package ru.vsu.hb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>, TransactionCustomRepository {

    Optional<Transaction> getByTransactionId(UUID transactionId);

    List<Transaction> getByUser_UserId(UUID userId);

    @Override
    Transaction save(Transaction transaction);

    Integer deleteByTransactionId(UUID transactionId);

    void deleteByUser_UserId(UUID userId);

    @Query(nativeQuery = true, value = "SELECT (SELECT SUM(sum) FROM hb.transactions where user_id = ?1 and category_name is null) - (SELECT SUM(sum) FROM hb.transactions where user_id = ?1 and category_name is not null)")
    BigDecimal getBalance(UUID userId);



}
