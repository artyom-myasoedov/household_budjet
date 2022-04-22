package ru.vsu.hb.persistence.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    Transaction save(Transaction transaction);

    Integer deleteByTransactionId(UUID transactionId);

    Integer deleteAllByCategoryIdAndUserId(UUID categoryId, UUID userId);

    @Query(nativeQuery = true, value = "SELECT (SELECT SUM(sum) FROM hb.transactions where user_id = ?1 and category_name is null) - (SELECT SUM(sum) FROM hb.transactions where user_id = ?1 and category_name is not null)")
    BigDecimal getBalance(UUID userId);

    Page<Transaction> findByUser_UserIdAndCategoryId(UUID userId, UUID categoryId, Pageable pageable);

    Page<Transaction> findByUser_UserIdAndCategoryIdIsNull(UUID userId, Pageable pageable);

    Page<Transaction> findByUser_UserIdAndCategoryIdIsNotNull(UUID userId, Pageable pageable);

    Page<Transaction> findByUser_UserId(UUID userId, Pageable pageable);
}
