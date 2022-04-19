package ru.vsu.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

import static ru.vsu.hb.security.SecurityConstants.HEADER_STRING;
import static ru.vsu.hb.utils.ControllerUtils.toHBResult;

@PreAuthorize("hasAnyAuthority('USER')")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> addTransaction(
            @RequestBody TransactionDto transaction,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.addTransaction(transaction))
                .withAuthToken(auth)
                .build();
    }

    @PutMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> updateTransaction(
            @RequestBody TransactionDto transaction,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.updateTransaction(transaction))
                .withAuthToken(auth)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<? super HBResponseData<? super Integer>> deleteTransaction(
            @RequestParam UUID transactionId,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.deleteByTransactionId(transactionId))
                .withAuthToken(auth)
                .build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> getTransactionById(
            @PathVariable UUID transactionId,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.getByTransactionId(transactionId))
                .withAuthToken(auth)
                .build();
    }

    @GetMapping("/balance")
    public ResponseEntity<? super HBResponseData<? super BigDecimal>> getBalance(
            @RequestParam UUID userId,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.getBalance(userId))
                .withAuthToken(auth)
                .build();
    }

    @PostMapping("/list")
    public ResponseEntity<? super HBResponseData<? super PageDto<TransactionDto>>> getList(
            @RequestBody TransactionListRequest request,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.getList(request))
                .withAuthToken(auth)
                .build();
    }

    @PostMapping("/byCategory")
    public ResponseEntity<? super HBResponseData<? super PageDto<TransactionDto>>> getByCategory(
            @RequestBody TransactionByCategoryRequest request,
            @RequestHeader(name = HEADER_STRING) String auth) {
        return HBResponseBuilder.fromHBResult(service.getByCategoryName(request))
                .withAuthToken(auth)
                .build();
    }

}
