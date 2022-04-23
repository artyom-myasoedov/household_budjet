package ru.vsu.hb.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.Transaction;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> getByTransactionId(UUID transactionId);

    @Override
    Transaction save(Transaction transaction);

    Integer deleteByTransactionId(UUID transactionId);

    Integer deleteAllByCategoryIdAndUserEmail(UUID categoryId, String userEmail);

    @Query(nativeQuery = true, value = "SELECT SUM(sum) FROM hb.transactions where user_email = ?1 and category_id is null")
    Optional<BigDecimal> getInBalance(String userEmail);

    @Query(nativeQuery = true, value = "SELECT SUM(sum) FROM hb.transactions where user_email = ?1 and category_id is not null")
    Optional<BigDecimal> getOutBalance(String userEmail);

    Page<Transaction> findByUser_UserEmailAndCategoryId(String userEmail, UUID categoryId, Pageable pageable);

    Page<Transaction> findByUser_UserEmailAndCategoryIdIsNull(String userEmail, Pageable pageable);

    Page<Transaction> findByUser_UserEmailAndCategoryIdIsNotNull(String userEmail, Pageable pageable);

    Page<Transaction> findByUser_UserEmail(String userEmail, Pageable pageable);
}
