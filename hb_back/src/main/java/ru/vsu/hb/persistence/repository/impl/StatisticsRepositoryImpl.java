package ru.vsu.hb.persistence.repository.impl;

import org.springframework.stereotype.Repository;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.persistence.repository.StatisticsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Map<String, BigDecimal> getUserOutTransactionsSumForMonth(String email, List<CategoryDto> categories, LocalDateTime date) {


        return null;
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return null;
    }
}
