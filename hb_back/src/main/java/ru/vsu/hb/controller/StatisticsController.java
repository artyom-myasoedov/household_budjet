package ru.vsu.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserRecommendations;
import ru.vsu.hb.dto.UserStatistics;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.StatisticsService;
import ru.vsu.hb.utils.HBResponseBuilder;

import java.security.Principal;


@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService service;

    @GetMapping("/personal")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<? super HBResponseData<? super UserStatistics>> getByUserId(Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getUserStatistics(principal.getName())).build();
    }

    @GetMapping()
    public ResponseEntity<? super HBResponseData<? super GlobalStatistics>> getGlobal() {
        return HBResponseBuilder.fromHBResult(service.getGlobalStatistics()).build();
    }

    @GetMapping("/recommendations")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<? super HBResponseData<? super UserRecommendations>> getRecommendations(Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getUserRecommendations(principal.getName())).build();
    }
}
