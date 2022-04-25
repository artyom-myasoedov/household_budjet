package ru.vsu.hb.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.dto.PageDto;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb.dto.request.TransactionListRequest;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.TransactionService;
import ru.vsu.hb.utils.HBResponseBuilder;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

import static ru.vsu.hb.security.SecurityConstants.HEADER_STRING;
import static ru.vsu.hb.utils.ControllerUtils.toHBResult;

@PreAuthorize("hasAnyAuthority('USER')")
@RestController
@RequestMapping(value = "/transaction", produces = {"application/json"})
@Api(description = "Транзакция(пополнение или расход)")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Добавление транзакции к пользователю")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> addTransaction(
            @RequestBody TransactionDto transaction, Principal principal) {
        transaction.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.addTransaction(transaction))
                .build();
    }

    @PutMapping
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Редактирование транзакции")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> updateTransaction(
            @RequestBody TransactionDto transaction) {
        return HBResponseBuilder.fromHBResult(service.updateTransaction(transaction))
                .build();
    }

    @DeleteMapping
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Удаление транзакции по id")
    public ResponseEntity<? extends HBResponseData<? extends Integer>> deleteTransaction(
            @RequestParam @ApiParam(value = "Идентификато транзакции") UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.deleteByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/{transactionId}")
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Получение транзакции по id")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> getTransactionById(
            @PathVariable @ApiParam(value = "Идентификато транзакции") UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.getByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/balance")
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Получение баланса пользователя")
    public ResponseEntity<? extends HBResponseData<? extends BigDecimal>> getBalance(Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getBalance(principal.getName()))
                .build();
    }

    @PostMapping("/list")
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Получение списка транзакций пользователя")
    public ResponseEntity<? extends HBResponseData<? extends PageDto<TransactionDto>>> getList(
            @RequestBody TransactionListRequest request, Principal principal) {
        request.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.getList(request))
                .build();
    }

    @PostMapping("/byCategory")
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Получение списка транзакций пользователя в определённой категории")
    public ResponseEntity<? extends HBResponseData<? extends PageDto<TransactionDto>>> getByCategory(
            @RequestBody TransactionByCategoryRequest request, Principal principal) {
        request.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.getByCategoryName(request))
                .build();
    }

    @GetMapping("/currMonthOutSum")
    @ApiImplicitParams(
            @ApiImplicitParam(name="Authorization", paramType = "header", value = "Bearer token")
    )
    @ApiOperation(value = "Получение суммы расходов на все категории за текущий месяц")
    public ResponseEntity<? extends HBResponseData<? extends BigDecimal>> getCurrMonthOutSum(Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getCurrMonthOutSum(principal.getName())).build();
    }

}
