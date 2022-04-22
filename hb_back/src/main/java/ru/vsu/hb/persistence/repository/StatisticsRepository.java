package ru.vsu.hb.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.GlobalStatistics;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface StatisticsRepository {


    Map<String, BigDecimal> getUserOutTransactionsSumForMonth(String email, List<CategoryDto> categories, LocalDateTime date);

    GlobalStatistics getGlobalStatistics();
}
