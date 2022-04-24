package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb.dto.request.TransactionListRequest;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.Transaction;
import ru.vsu.hb.persistence.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TransactionServiceTest {

    private TransactionRepository repository = Mockito.mock(TransactionRepository.class);
    private UserService userService = Mockito.mock(UserService.class);
    private CategoryService categoryService = Mockito.mock(CategoryService.class);
    private TransactionService service = new TransactionService(repository, categoryService, userService);

    private Transaction OUT_TRANSACTION_ENTITY = new Transaction(
            UUID.fromString("ecd8c0d6-b513-4435-af7a-59ccbfcddf28"),
            "email1",
            BigDecimal.TEN, LocalDateTime.now(), "desc", UUID.fromString("ecd8c0d6-b513-4435-af7a-59c5bfcddf28"));
    private Transaction IN_TRANSACTION_ENTITY = new Transaction(
            UUID.fromString("ecd8c0d6-b513-4435-af7a-59ccbfcddf27"),
            "email2",
            BigDecimal.TEN, LocalDateTime.now(), "desc", null);

    private TransactionDto OUT_TRANSACTION_DTO = TransactionDto.fromEntity(OUT_TRANSACTION_ENTITY);
    private TransactionDto IN_TRANSACTION_DTO = TransactionDto.fromEntity(IN_TRANSACTION_ENTITY);



    @Test
    public void addOutTransactionSuccess() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any())).thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.save(any())).thenReturn(OUT_TRANSACTION_ENTITY);
        Result<TransactionDto, HBError> res = service.addTransaction(OUT_TRANSACTION_DTO);
        assertTrue(res.isSuccess());
    }

    @Test
    public void addInTransactionSuccess() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.failure(new EntityNotFoundError("")));
        Mockito.when(repository.save(any())).thenReturn(IN_TRANSACTION_ENTITY);
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Result<TransactionDto, HBError> res = service.addTransaction(IN_TRANSACTION_DTO);
        assertTrue(res.isSuccess());
    }

    @Test
    public void addInTransactionUserNotFound() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.failure(new EntityNotFoundError("")));
        Mockito.when(repository.save(any())).thenReturn(IN_TRANSACTION_ENTITY);
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.failure(new EntityNotFoundError("msg")));
        Result<TransactionDto, HBError> res = service.addTransaction(IN_TRANSACTION_DTO);
        assertEquals("msg", res.optionalFailure().orElse(new HBError("", "")).getMessage());
    }

    @Test
    public void addOutTransactionUserOrCategoryNotFound() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.failure(new EntityNotFoundError("msg")));
        Mockito.when(repository.save(any())).thenReturn(OUT_TRANSACTION_ENTITY);
        Result<TransactionDto, HBError> res = service.addTransaction(OUT_TRANSACTION_DTO);
        assertEquals("msg", res.optionalFailure().orElse(new HBError("", "")).getMessage());
    }

    @Test
    public void updateOutTransactionSuccess() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.save(any())).thenReturn(OUT_TRANSACTION_ENTITY);
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.of(OUT_TRANSACTION_ENTITY));
        Result<TransactionDto, HBError> res = service.updateTransaction(OUT_TRANSACTION_DTO);
        assertTrue(res.isSuccess());
    }

    @Test
    public void updateOutTransactionTransactionNotFound() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.save(any())).thenReturn(OUT_TRANSACTION_ENTITY);
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.empty());
        Result<TransactionDto, HBError> res = service.updateTransaction(OUT_TRANSACTION_DTO);
        assertEquals("Transaction with id = " + OUT_TRANSACTION_ENTITY.getTransactionId() + " not found", res.optionalFailure().orElse(new HBError("", "")).getMessage());
    }

    @Test
    public void updateOutTransactionUserNotFound() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.save(any())).thenReturn(OUT_TRANSACTION_ENTITY);
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.of(IN_TRANSACTION_ENTITY));
        Result<TransactionDto, HBError> res = service.updateTransaction(OUT_TRANSACTION_DTO);
        assertEquals("User with userEmail = " + OUT_TRANSACTION_ENTITY.getUserEmail() + " doesn't have transaction with id = " + OUT_TRANSACTION_ENTITY.getTransactionId(), res.optionalFailure().orElse(new HBError("", "")).getMessage());
    }

    @Test
    public void updateInTransactionSuccess() {
        Mockito.when(categoryService.getByUserCategoryId(any(), any()))
                .thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.save(any())).thenReturn(IN_TRANSACTION_ENTITY);
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.of(IN_TRANSACTION_ENTITY));
        Result<TransactionDto, HBError> res = service.updateTransaction(IN_TRANSACTION_DTO);
        assertTrue(res.isSuccess());
    }

    @Test
    public void deleteTransaction() {
        Mockito.when(repository.deleteByTransactionId(any()))
                .thenReturn(1);
        var res = service.deleteByTransactionId(IN_TRANSACTION_DTO.getTransactionId());
        assertTrue(res.isSuccess());
    }

    @Test
    public void getTransactionSuccess() {
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.of(IN_TRANSACTION_ENTITY));
        var res = service.getByTransactionId(IN_TRANSACTION_DTO.getTransactionId());
        assertTrue(res.isSuccess());
    }

    @Test
    public void getTransactionFailure() {
        Mockito.when(repository.getByTransactionId(any())).thenReturn(Optional.empty());
        var res = service.getByTransactionId(IN_TRANSACTION_DTO.getTransactionId());
        assertTrue(res.isFailure());
    }

    @Test
    public void getBalanceSuccess() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(repository.getInBalance(any())).thenReturn(Optional.of(BigDecimal.ONE));
        var res = service.getBalance(IN_TRANSACTION_DTO.getUserEmail());
        assertEquals(BigDecimal.ONE, res.orElse(BigDecimal.ZERO));
    }

    @Test
    public void getBalanceFailure() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.failure(new HBError("", "")));
        Mockito.when(repository.getInBalance(any())).thenReturn(Optional.of(BigDecimal.ONE));
        var res = service.getBalance(IN_TRANSACTION_DTO.getUserEmail());
        assertTrue(res.isFailure());
    }

    @Test
    public void getListSuccess() {
        var req = new TransactionListRequest();
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(repository.findByUser_UserEmail(any(), any())).thenReturn(Page.empty());
        var res = service.getList(req);
        assertTrue(res.isSuccess());
    }

    @Test
    public void getListFailure() {
        var req = new TransactionListRequest();
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.failure(new HBError("", "")));
        Mockito.when(repository.findByUser_UserEmail(any(), any())).thenReturn(Page.empty());
        var res = service.getList(req);
        assertTrue(res.isFailure());
    }

    @Test
    public void getByCategorySuccess() {
        var req = new TransactionByCategoryRequest();
        Mockito.when(categoryService.getByUserCategoryId(any(), any())).thenReturn(Results.success(new CategoryDto()));
        Mockito.when(repository.findByUser_UserEmailAndCategoryId(any(), any(), any())).thenReturn(Page.empty());
        var res = service.getByCategoryName(req);
        assertTrue(res.isSuccess());
    }

    @Test
    public void getByCategoryFailure() {
        var req = new TransactionByCategoryRequest();
        Mockito.when(categoryService.getByUserCategoryId(any(), any())).thenReturn(Results.failure(new HBError("", "")));
        Mockito.when(repository.findByUser_UserEmailAndCategoryId(any(), any(), any())).thenReturn(Page.empty());
        var res = service.getByCategoryName(req);
        assertTrue(res.isFailure());
    }

}