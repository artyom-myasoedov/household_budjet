package ru.vsu.hb_front.api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vsu.hb_front.dto.UserDto;
import ru.vsu.hb_front.dto.UserLoginRequest;
import ru.vsu.hb_front.dto.response.HBResponseData;
import ru.vsu.hb_front.store.PreferenceStore;

public class Api implements Interceptor {
    private Retrofit retrofit;
    private static final Api api = new Api();
    private OkHttpClient client;

    public UserService getUserService() {
        return userService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public StatisticsService getStatisticsService() {
        return statisticsService;
    }

    private UserService userService;
    private CategoryService categoryService;
    private TransactionService transactionService;
    private StatisticsService statisticsService;

    public static void init(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        api.client = new OkHttpClient.Builder().addInterceptor(api).addInterceptor(loggingInterceptor).build();
        api.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(api.client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api.userService = api.retrofit.create(UserService.class);
        api.categoryService = api.retrofit.create(CategoryService.class);
        api.transactionService = api.retrofit.create(TransactionService.class);
        api.statisticsService = api.retrofit.create(StatisticsService.class);
    }


    public static Api getInstance() {
        return api;
    }

    private Api() {
    }

    public Single<Response<HBResponseData<UserDto>>> login(UserLoginRequest user) {
        return userService.login(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        String token = PreferenceStore.getInstance().getToken();

        if (token == null || token.length() == 0) {
            Request req = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(req);
        } else {
            Request req = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            return chain.proceed(req);
        }

    }
}
