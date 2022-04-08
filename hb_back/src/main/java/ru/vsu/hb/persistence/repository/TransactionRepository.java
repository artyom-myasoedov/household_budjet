package ru.vsu.hb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>, TransactionCustomRepository {

    Optional<Transaction> getByTransactionId(UUID transactionId);

    List<Transaction> getByUser_UserId(UUID userId);

    @Override
    Transaction save(Transaction transaction);

    void deleteByTransactionId(UUID transactionId);

    void deleteByUser_UserId(UUID userId);

}
