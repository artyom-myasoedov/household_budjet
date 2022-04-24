package ru.vsu.hb.persistence.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;
import ru.vsu.hb.persistence.repository.StatisticsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static Logger LOG = LoggerFactory.getLogger(StatisticsRepositoryImpl.class);


    @Override
    public UserStatisticsRecommendations getUserOutTransactionStatistics(String email) {
        var userStatistics = new UserStatisticsRecommendations();
        List<?> currMonth = entityManager
                .createQuery("select (" +
                        "select c.categoryName from Category c " +
                        "where c.userCategoryId.userEmail = :email and c.userCategoryId.categoryId = t.categoryId), " +
                        "sum(t.sum) from transactions t " +
                        "where t.userEmail = :email and month(t.createTime) = month(current_timestamp()) " +
                        "group by t.categoryId")
                .setParameter("email", email)
                .getResultList();

        Map<String, BigDecimal> currMonthMap = currMonth.stream()
                .filter(it -> ((Object[]) it)[0] != null)
                .collect(Collectors.toMap(it -> (String) ((Object[]) it)[0],
                        it -> (BigDecimal) ((Object[]) it)[1]));

        List<?> prevMonth = entityManager
                .createQuery("select (" +
                        "select c.categoryName from Category c " +
                        "where c.userCategoryId.userEmail = :email and c.userCategoryId.categoryId = t.categoryId), " +
                        "sum(t.sum) from transactions t " +
                        "where t.userEmail = :email and month(t.createTime) = month(current_timestamp()) - 1 " +
                        "group by t.categoryId")
                .setParameter("email", email)
                .getResultList();

        Map<String, BigDecimal> prevMonthMap = prevMonth.stream()
                .filter(it -> ((Object[]) it)[0] != null)
                .collect(Collectors.toMap(it -> (String) ((Object[]) it)[0],
                        it -> (BigDecimal) ((Object[]) it)[1]));

        userStatistics.setCurrMonthExpenses(currMonthMap);
        userStatistics.setPrevMonthExpenses(prevMonthMap);
        try {
            userStatistics.setCurrMonthSumIn((BigDecimal) ((Object[]) currMonth.stream()
                    .filter(it -> ((Object[]) it)[0] == null).findFirst().get())[1]);
        } catch (Exception e) {
            userStatistics.setCurrMonthSumIn(BigDecimal.ZERO);
            LOG.error("Exception while extracting IN transaction sum for current month for user: " + email);
        }
        try {
            userStatistics.setPrevMonthSumIn((BigDecimal) ((Object[]) prevMonth.stream()
                    .filter(it -> ((Object[]) it)[0] == null).findFirst().get())[1]);
        } catch (Exception e) {
            userStatistics.setPrevMonthSumIn(BigDecimal.ZERO);
            LOG.error("Exception while extracting IN transaction sum for previous month for user: " + email);
        }

        return userStatistics;
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        List<?> currMonth = entityManager
                .createQuery("select " +
                        "(select distinct c.categoryName from Category c " +
                        "where c.userCategoryId.categoryId = t.categoryId), " +
                        "sum(t.sum) / count(u) from transactions t, User u " +
                        "where (select distinct c.isDefault from Category c " +
                        "where c.userCategoryId.categoryId = t.categoryId " +
                        "and c.userCategoryId.userEmail = t.userEmail) = true " +
                        "and month(t.createTime) = month(current_timestamp()) " +
                        "group by t.categoryId")
                .getResultList();

        Map<String, BigDecimal> currMonthMap = currMonth.stream()
                .filter(it -> ((Object[]) it)[0] != null)
                .collect(Collectors.toMap(it -> (String) ((Object[]) it)[0],
                        it -> (BigDecimal) ((Object[]) it)[1]));

        List<?> prevMonth = entityManager
                .createQuery("select " +
                        "(select distinct c.categoryName from Category c " +
                        "where c.userCategoryId.categoryId = t.categoryId), " +
                        "sum(t.sum) / count(u) from transactions t, User u " +
                        "where (select distinct c.isDefault from Category c " +
                        "where c.userCategoryId.categoryId = t.categoryId " +
                        "and c.userCategoryId.userEmail = t.userEmail) = true " +
                        "and month(t.createTime) = month(current_timestamp()) - 1 " +
                        "group by t.categoryId")
                .getResultList();

        Map<String, BigDecimal> prevMonthMap = prevMonth.stream()
                .filter(it -> ((Object[]) it)[0] != null)
                .collect(Collectors.toMap(it -> (String) ((Object[]) it)[0],
                        it -> (BigDecimal) ((Object[]) it)[1]));

        return new GlobalStatistics(currMonthMap, prevMonthMap);
    }
}
