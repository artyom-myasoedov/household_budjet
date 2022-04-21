package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.hb.dto.SortOrder;
import ru.vsu.hb.dto.TransactionType;
import ru.vsu.hb.dto.PageDto;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.request.PageRequest;
import ru.vsu.hb.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb.dto.request.TransactionListRequest;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.Transaction;
import ru.vsu.hb.persistence.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    public TransactionService() {
    }

    public TransactionService(TransactionRepository repository, CategoryService categoryService, UserService userService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Transactional
    public Result<TransactionDto, HBError> addTransaction(TransactionDto transaction) {
        return categoryService.getByUserCategoryId(transaction.getUserId(), transaction.getCategoryName())
                .mapSuccess(it -> repository.save(transaction.toEntity()))
                .mapSuccess(TransactionDto::fromEntity)
                .flatMapFailure(error -> {
                    if (transaction.getCategoryName() == null) {
                        return userService.getDtoById(transaction.getUserId())
                                .mapSuccess(user -> repository.save(transaction.toEntity()))
                                .mapSuccess(TransactionDto::fromEntity);
                    }
                    return Results.failure(error);
                });
    }

    @Transactional
    public Result<TransactionDto, HBError> updateTransaction(TransactionDto transaction) {
        var res = transaction.getCategoryName() == null ? Results.success(new Category()).mapFailure(it -> (HBError) it)
                : categoryService.getByUserCategoryId(transaction.getUserId(), transaction.getCategoryName());
        return res.flatMapSuccess((category) -> Results.ofCallable(() -> repository.getByTransactionId(transaction.getTransactionId())
                                .orElseThrow(() -> new IllegalStateException("not_found")))
                        .mapFailure(exception -> {
                            if (exception instanceof IllegalStateException && "not_found".equals(exception.getMessage())) {
                                return new EntityNotFoundError("Transaction with id = " + transaction.getTransactionId() + " not found");
                            }
                            throw new RuntimeException(exception);
                        }))
                .flatMapSuccess(it -> Results.ofCallable(() -> {
                            if (it.getUserId().equals(transaction.getUserId())) {
                                return TransactionDto.fromEntity(repository.save(transaction.toEntity()));
                            }
                            throw new RuntimeException("bad_request");
                        },
                        e -> {
                            if ("bad_request".equals(e.getMessage())) {
                                return new BadRequestError("User with id = " + transaction.getUserId() + " doesn't have transaction with id = " + transaction.getTransactionId());
                            }
                            throw new RuntimeException(e);
                        }
                ));
    }

    @Transactional
    public Result<Integer, HBError> deleteByTransactionId(UUID transactionId) {
        return Results.success(repository.deleteByTransactionId(transactionId));
    }

    public Result<TransactionDto, HBError> getByTransactionId(UUID transactionId) {
        return Results.ofCallable(() -> repository.getByTransactionId(transactionId)
                        .orElseThrow(() -> new IllegalStateException("not_found")))
                .mapSuccess(TransactionDto::fromEntity)
                .mapFailure(e -> {
                    if ("not_found".equals(e.getMessage())) {
                        return new EntityNotFoundError("Transaction with id = " + transactionId + " not found");
                    }
                    throw new RuntimeException(e);
                });
    }

    public Result<BigDecimal, HBError> getBalance(UUID userId) {
        return userService.getDtoById(userId)
                .mapSuccess(user -> repository.getBalance(userId));
    }

    public Result<PageDto<TransactionDto>, HBError> getList(TransactionListRequest request) {
        return userService.getDtoById(request.getUserId())
                .mapSuccess(user -> {
                    if (request.getTransactionType().equals(TransactionType.ALL)) {
                        return repository.findByUser_UserId(
                                request.getUserId(),
                                getPageable(request.getPage()));
                    } else if (request.getTransactionType().equals(TransactionType.IN)) {
                        return repository.findByUser_UserIdAndCategoryNameIsNull(
                                request.getUserId(),
                                getPageable(request.getPage()));
                    } else {
                        return repository.findByUser_UserIdAndCategoryNameIsNotNull(
                                request.getUserId(),
                                getPageable(request.getPage()));
                    }
                })
                .mapSuccess(getPageToPageDtoFunction(request.getPage()));
    }

    public Result<PageDto<TransactionDto>, HBError> getByCategoryName(TransactionByCategoryRequest request) {
        return categoryService.getByUserCategoryId(request.getUserId(), request.getCategoryName())
                .mapSuccess(category -> repository.findByUser_UserIdAndCategoryName(
                        request.getUserId(),
                        request.getCategoryName(),
                        getPageable(request.getPage())
                ))
                .mapSuccess(getPageToPageDtoFunction(request.getPage()));
    }

    private Sort getSort(PageRequest request) {
        return Sort.by(request.getSortOrder().equals(SortOrder.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC,
                request.getSortField() == null ? "createTime" : request.getSortField());
    }

    private Pageable getPageable(PageRequest request) {
        return org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getLimit(), getSort(request));
    }

    private Function<Page<Transaction>, PageDto<TransactionDto>> getPageToPageDtoFunction(PageRequest request) {
        return page -> new PageDto<>(
                page.toList().stream().map(TransactionDto::fromEntity).collect(Collectors.toList()),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalPages() > page.getSize(),
                request.getSortField() == null ? "createTime" : request.getSortField(),
                request.getSortOrder());
    }

}
