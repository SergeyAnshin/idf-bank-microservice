package org.sergey.idf_bank_microservice.expenselimit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/expense-limit")
public class ExpenseLimitController {
    public static final String BASE_URI = "/api/expense-limit";
    private final ExpenseLimitService expenseLimitService;

    @PostMapping
    public ResponseEntity<String> setExpenseLimit(@RequestBody @Valid ExpenseLimitDto expenseLimitDto) {
        ExpenseLimitDto newExpenseLimit = expenseLimitService.setExpenseLimit(expenseLimitDto);
        URI location = URI.create(BASE_URI + newExpenseLimit.id());
        return ResponseEntity.created(location).build();
    }

}
