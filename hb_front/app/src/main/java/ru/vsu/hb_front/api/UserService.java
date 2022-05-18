package ru.vsu.hb_front.api;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.vsu.hb_front.dto.UserDto;
import ru.vsu.hb_front.dto.UserEditRequest;
import ru.vsu.hb_front.dto.UserLoginRequest;
import ru.vsu.hb_front.dto.response.HBResponseData;

public interface UserService {

    @POST("/user")
    Single<Response<HBResponseData<UserDto>>> edit(@Body UserEditRequest user);

    @GET("/user")
    Single<Response<HBResponseData<UserDto>>> getByEmail(@Path("userEmail") String email);

    @POST("/user/login")
    Single<Response<HBResponseData<UserDto>>> login(@Body UserLoginRequest user);

    @POST("/user/register")
    Single<Response<HBResponseData<UserDto>>> register(@Body UserEditRequest user);

}
