package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.repository.StatisticsRepository;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository repository;

    public Result<UserStatisticsRecommendations, HBError> getUserStatistics(String email) {
        return Results.success(repository.getUserOutTransactionStatistics(email))
                .map(this::getUserRecommendations, it -> (HBError) it);
    }

    public Result<GlobalStatistics, HBError> getGlobalStatistics() {
        return Results.success(repository.getGlobalStatistics());
    }

    private UserStatisticsRecommendations getUserRecommendations(
            UserStatisticsRecommendations userStatisticsRecommendations) {

        return null;
    }
}
