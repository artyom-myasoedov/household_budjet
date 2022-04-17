package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.hb.controller.model.TransactionType;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.Transaction;
import ru.vsu.hb.persistence.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Transactional
    public Result<TransactionDto, HBError> addTransaction(TransactionDto transaction) {
        return categoryService.getByUserCategoryId(transaction.getUserId(), transaction.getCategoryName())
                .mapSuccess(it -> repository.save(transaction.toEntity()))
                .mapSuccess(TransactionDto::fromEntity)
                .flatMapFailure(error -> {
                    if (transaction.getCategoryName() == null) {
                        return userService.getById(transaction.getUserId())
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
        return userService.getById(userId)
                .mapSuccess(user -> repository.getBalance(userId));
    }

//    public Result<Page> getList(TransactionType transactionType, UUID userId) {
//    }
}
