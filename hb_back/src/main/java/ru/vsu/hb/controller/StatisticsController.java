package ru.vsu.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.hb.dto.GlobalStatisticsDto;
import ru.vsu.hb.dto.UserRecommendations;
import ru.vsu.hb.dto.UserStatisticsDto;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.StatisticsService;

import static ru.vsu.hb.utils.ControllerUtils.toHBResult;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService service;

    @GetMapping("/{userId}")
    public ResponseEntity<? super HBResponseData<? super UserStatisticsDto>> getByUserId(@PathVariable String userId) {
        return toHBResult(service.getUserStatistics(userId));
    }

    @GetMapping
    public ResponseEntity<? super HBResponseData<? super GlobalStatisticsDto>> getGlobal() {
        return toHBResult(service.getGlobalStatistics());
    }

    @GetMapping("/{userId}/recommendations")
    public ResponseEntity<? super HBResponseData<? super UserRecommendations>> getRecommendations(@PathVariable String userId) {
        return toHBResult(service.getUserRecommendations(userId));
    }
}
