package org.sergey.idf_bank_microservice.debittransaction;

import lombok.RequiredArgsConstructor;
import org.sergey.idf_bank_microservice.entitypersister.EntityPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Service
public class DebitTransactionService {
    private final DebitTransactionRepository debitTransactionRepository;
    private final DebitTransactionMapper debitTransactionMapper;
    private final EntityPersistenceService<DebitTransaction> debitTransactionPersistenceService;

    @Transactional
    public SuccessfulDebitTransactionDto register(DebitTransactionDto debitTransactionDto) {
        DebitTransaction debitTransaction = debitTransactionMapper.toDebitTransaction(debitTransactionDto);
        DebitTransaction persistedDebitTransaction = debitTransactionPersistenceService.persist(debitTransaction);
        DebitTransaction savedDebitTransaction = debitTransactionRepository.save(persistedDebitTransaction);
        return debitTransactionMapper.toSuccessfulDebitTransactionDto(savedDebitTransaction);
    }

}
