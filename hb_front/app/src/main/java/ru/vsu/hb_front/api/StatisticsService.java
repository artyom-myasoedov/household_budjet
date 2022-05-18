package ru.vsu.hb_front.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.dto.GlobalStatistics;
import ru.vsu.hb_front.dto.UserStatisticsRecommendations;
import ru.vsu.hb_front.dto.response.HBResponseData;

public interface StatisticsService {

    @GET("/statistics/global")
    Single<Response<HBResponseData<GlobalStatistics>>> getGlobalStat();

    @GET("/statistics/personal")
    Single<Response<HBResponseData<UserStatisticsRecommendations>>> getPersonalStat();

}
