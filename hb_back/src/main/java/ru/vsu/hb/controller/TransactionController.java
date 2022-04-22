package ru.vsu.hb.controller;

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
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> addTransaction(
            @RequestBody TransactionDto transaction) {
        return HBResponseBuilder.fromHBResult(service.addTransaction(transaction))
                .build();
    }

    @PutMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> updateTransaction(
            @RequestBody TransactionDto transaction) {
        return HBResponseBuilder.fromHBResult(service.updateTransaction(transaction))
                .build();
    }

    @DeleteMapping
    public ResponseEntity<? super HBResponseData<? super Integer>> deleteTransaction(
            @RequestParam UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.deleteByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> getTransactionById(
            @PathVariable UUID transactionId) {
        return HBResponseBuilder.fromHBResult(service.getByTransactionId(transactionId))
                .build();
    }

    @GetMapping("/balance")
    public ResponseEntity<? super HBResponseData<? super BigDecimal>> getBalance(
            @RequestParam UUID userId) {
        return HBResponseBuilder.fromHBResult(service.getBalance(userId))
                .build();
    }

    @PostMapping("/list")
    public ResponseEntity<? super HBResponseData<? super PageDto<TransactionDto>>> getList(
            @RequestBody TransactionListRequest request) {
        return HBResponseBuilder.fromHBResult(service.getList(request))
                .build();
    }

    @PostMapping("/byCategory")
    public ResponseEntity<? super HBResponseData<? super PageDto<TransactionDto>>> getByCategory(
            @RequestBody TransactionByCategoryRequest request) {
        return HBResponseBuilder.fromHBResult(service.getByCategoryName(request))
                .build();
    }

}
