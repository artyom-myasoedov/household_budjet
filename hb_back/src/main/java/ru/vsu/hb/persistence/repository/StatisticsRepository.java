package ru.vsu.hb.persistence.repository;

import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface StatisticsRepository {


    UserStatisticsRecommendations getUserOutTransactionStatistics(String email);

    GlobalStatistics getGlobalStatistics();
}
