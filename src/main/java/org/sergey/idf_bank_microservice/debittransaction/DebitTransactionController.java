package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/debit-transactions")
public class DebitTransactionController {
    public static final String BASE_URI = "/api/debit-transaction/";
    private final DebitTransactionService debitTransactionService;

    @PostMapping
    public ResponseEntity<String> register(
            @RequestBody @Validated(NewDebitTransactionChecks.class) DebitTransactionDto debitTransactionDto) {
        SuccessfulDebitTransactionDto successfulTransaction = debitTransactionService.register(debitTransactionDto);
        URI location = URI.create(BASE_URI + successfulTransaction.id());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/exceeded-limit")
    public ResponseEntity<List<DebitTransactionDto>> getTransactionsWithExceededLimit(@RequestBody @Validated(
            TransactionsWithExceededLimitGroup.class) DebitTransactionDto debitTransactionDto) {
        List<DebitTransactionDto> transactions = debitTransactionService.findByExceededLimit(debitTransactionDto);
        return ResponseEntity.ok(transactions);
    }
}
