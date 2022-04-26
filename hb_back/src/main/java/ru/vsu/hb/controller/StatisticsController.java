package ru.vsu.hb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.StatisticsService;
import ru.vsu.hb.utils.HBResponseBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;


@RestController
@RequestMapping(value = "/statistics", produces = {"application/json"})
@Api(description = "Статистика и рекомендации")
public class StatisticsController {

    @Autowired
    private StatisticsService service;

    @GetMapping("/personal")
    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "Получение статистики и рекомендаций для пользователя")
    public ResponseEntity<? extends HBResponseData<? extends UserStatisticsRecommendations>> getByUserId(@ApiIgnore Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getUserStatistics(principal.getName())).build();
    }

    @GetMapping("/global")
    @ApiOperation(value = "Получение статистики по всем пользователям")
    public ResponseEntity<? extends HBResponseData<? extends GlobalStatistics>> getGlobal() {
        return HBResponseBuilder.fromHBResult(service.getGlobalStatistics()).build();
    }
}
