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
import ru.vsu.hb_front.dto.UserDto;
import ru.vsu.hb_front.dto.response.HBResponseData;

public interface CategoryService {

    @GET("/category")
    Single<Response<HBResponseData<List<CategoryDto>>>> getUserCategories();

    @PUT("/category")
    Single<Response<HBResponseData<CategoryDto>>> editCategory(@Body CategoryDto category);

    @POST("/category")
    Single<Response<HBResponseData<CategoryDto>>> createCategory(@Body CategoryDto category);

    @DELETE("/category")
    Single<Response<HBResponseData<CategoryDto>>> deleteCategory(@Query("categoryId") String id);

    @GET("/category")
    Single<Response<HBResponseData<CategoryDto>>> getCategory(@Path("categoryId") String id);

}
