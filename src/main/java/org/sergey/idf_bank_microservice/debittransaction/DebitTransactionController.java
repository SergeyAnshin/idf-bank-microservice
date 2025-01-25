package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/debit-transaction")
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
}
