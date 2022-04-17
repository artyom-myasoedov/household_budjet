package ru.vsu.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.controller.model.TransactionType;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.TransactionService;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.vsu.hb.utils.ControllerUtils.toHBResult;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> addTransaction(@RequestBody TransactionDto transaction) {
        return toHBResult(service.addTransaction(transaction));
    }

    @PutMapping
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> updateTransaction(@RequestBody TransactionDto transaction) {
        return toHBResult(service.updateTransaction(transaction));
    }

    @DeleteMapping
    public ResponseEntity<? super HBResponseData<? super Integer>> deleteTransaction(@RequestParam UUID transactionId){
        return toHBResult(service.deleteByTransactionId(transactionId));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<? super HBResponseData<? super TransactionDto>> getTransactionById(@PathVariable UUID transactionId) {
        return toHBResult(service.getByTransactionId(transactionId));
    }

    @GetMapping("/balance")
    public ResponseEntity<? super HBResponseData<? super BigDecimal>> getBalance(@RequestParam UUID userId) {
        return toHBResult(service.getBalance(userId));
    }
//
//    @GetMapping
//    public ResponseEntity<?> getList(@RequestParam TransactionType transactionType, @RequestParam UUID userId) {
//        return toHBResult(service.getList(transactionType, userId));
//    }
//
//    @GetMapping("/byCategory")
//    public ResponseEntity<?> getByCategory(@RequestParam String categoryName, @RequestParam UUID userId) {
//
//    }

}
