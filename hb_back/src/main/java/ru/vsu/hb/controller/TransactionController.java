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
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiOperation(value = "Добавление транзакции к пользователю")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> addTransaction(
            @RequestBody TransactionDto transaction, @ApiIgnore Principal principal) {
        transaction.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.addTransaction(transaction))
                .build();
    }

    @PutMapping
    @ApiOperation(value = "Редактирование транзакции")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> updateTransaction(
            @RequestBody TransactionDto transaction) {
        return HBResponseBuilder.fromHBResult(service.updateTransaction(transaction))
                .build();
    }

    @DeleteMapping

    @ApiOperation(value = "Удаление транзакции по id")
    public ResponseEntity<? extends HBResponseData<? extends Integer>> deleteTransaction(
            @RequestParam @ApiParam(value = "Идентификато транзакции") UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.deleteByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/{transactionId}")
    @ApiOperation(value = "Получение транзакции по id")
    public ResponseEntity<? extends HBResponseData<? extends TransactionDto>> getTransactionById(
            @PathVariable @ApiParam(value = "Идентификато транзакции") UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.getByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/balance")
    @ApiOperation(value = "Получение баланса пользователя")
    public ResponseEntity<? extends HBResponseData<? extends BigDecimal>> getBalance(@ApiIgnore Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getBalance(principal.getName()))
                .build();
    }

    @PostMapping("/list")

    @ApiOperation(value = "Получение списка транзакций пользователя")
    public ResponseEntity<? extends HBResponseData<? extends PageDto<TransactionDto>>> getList(
            @RequestBody TransactionListRequest request, @ApiIgnore Principal principal) {
        request.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.getList(request))
                .build();
    }

    @PostMapping("/byCategory")
    @ApiOperation(value = "Получение списка транзакций пользователя в определённой категории")
    public ResponseEntity<? extends HBResponseData<? extends PageDto<TransactionDto>>> getByCategory(
            @RequestBody TransactionByCategoryRequest request, @ApiIgnore Principal principal) {
        request.setUserEmail(principal.getName());
        return HBResponseBuilder.fromHBResult(service.getByCategoryName(request))
                .build();
    }

    @GetMapping("/currMonthOutSum")
    @ApiOperation(value = "Получение суммы расходов на все категории за текущий месяц")
    public ResponseEntity<? extends HBResponseData<? extends BigDecimal>> getCurrMonthOutSum(@ApiIgnore Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getCurrMonthOutSum(principal.getName())).build();
    }

}
