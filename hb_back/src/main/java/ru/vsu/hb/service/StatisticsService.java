package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.GlobalStatisticsDto;
import ru.vsu.hb.dto.UserRecommendations;
import ru.vsu.hb.dto.UserStatisticsDto;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.repository.TransactionRepository;

@Service
public class StatisticsService {

    @Autowired
    private TransactionRepository repository;

    public Result<UserStatisticsDto, HBError> getUserStatistics(String userId) {
        return null;
    }

    public Result<GlobalStatisticsDto, HBError> getGlobalStatistics() {
        return null;
    }

    public Result<UserRecommendations, HBError> getUserRecommendations(String userId) {
        return null;
    }
}
