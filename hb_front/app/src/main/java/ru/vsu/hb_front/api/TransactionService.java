package ru.vsu.hb_front.api;

import java.math.BigDecimal;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.vsu.hb_front.dto.PageDto;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb_front.dto.request.TransactionListRequest;
import ru.vsu.hb_front.dto.response.HBResponseData;

public interface TransactionService {

    @PUT("/transaction")
    Single<Response<HBResponseData<TransactionDto>>> editTransaction(@Body TransactionDto transaction);

    @POST("/transaction")
    Single<Response<HBResponseData<TransactionDto>>> addTransaction(@Body TransactionDto transaction);

    @DELETE("/transaction")
    Single<Response<HBResponseData<Integer>>> deleteTransaction(@Query("transactionId") String id);

    @GET("/transaction")
    Single<Response<HBResponseData<TransactionDto>>> getTransaction(@Path("transactionId") String id);

    @GET("/transaction/balance")
    Single<Response<HBResponseData<BigDecimal>>> getBalance();

    @GET("/transaction/byCategory")
    Single<Response<HBResponseData<PageDto<TransactionDto>>>> getTransactionsByCategory
            (@Body TransactionByCategoryRequest request);

    @GET("/transaction/currMonthOutSum")
    Single<Response<HBResponseData<BigDecimal>>> getCurMonthOutSum();

    @GET("/transaction/list")
    Single<Response<HBResponseData<PageDto<TransactionDto>>>> getTransactionsList
            (@Body TransactionListRequest request);

}
