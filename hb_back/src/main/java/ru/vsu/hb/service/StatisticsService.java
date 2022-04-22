package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserRecommendations;
import ru.vsu.hb.dto.UserStatistics;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.repository.TransactionRepository;

@Service
public class StatisticsService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryService categoryService;

    public Result<UserStatistics, HBError> getUserStatistics(String email) {
        var list = categoryService.getAll(email);


        return null;
    }

    public Result<GlobalStatistics, HBError> getGlobalStatistics() {
        return null;
    }

    public Result<UserRecommendations, HBError> getUserRecommendations(String email) {
        return null;
    }
}
