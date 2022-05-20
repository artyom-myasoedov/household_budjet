package ru.vsu.hb_front.api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.Observable;
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
import ru.vsu.hb_front.BuildConfig;
import ru.vsu.hb_front.dto.CategoryDto;
import ru.vsu.hb_front.dto.GlobalStatistics;
import ru.vsu.hb_front.dto.PageDto;
import ru.vsu.hb_front.dto.TransactionDto;
import ru.vsu.hb_front.dto.UserDto;
import ru.vsu.hb_front.dto.UserEditRequest;
import ru.vsu.hb_front.dto.UserLoginRequest;
import ru.vsu.hb_front.dto.UserStatisticsRecommendations;
import ru.vsu.hb_front.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb_front.dto.request.TransactionListRequest;
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

    private static class LocalDateAdapter implements JsonSerializer<LocalDateTime> {

        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    private static class LocalDateDeserializeAdapter implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString().substring(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
    }

    public static void init(String baseUrl) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializeAdapter())
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY );
        }

        api.client = UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder().addInterceptor(api).addInterceptor(loggingInterceptor).build();
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
        return userService.login(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<GlobalStatistics>>> getGlobalStat() {
        return statisticsService.getGlobalStat().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<UserStatisticsRecommendations>>> getUserStat() {
        return statisticsService.getPersonalStat().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<HBResponseData<List<CategoryDto>>>> getUserCategories() {
        return categoryService.getUserCategories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<PageDto<TransactionDto>>>> getCategoryTransactions(TransactionByCategoryRequest transactionByCategoryRequest) {
        return transactionService.getTransactionsByCategory(transactionByCategoryRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<PageDto<TransactionDto>>>> getTransactionsList(TransactionListRequest request) {
        return transactionService.getTransactionsList(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<HBResponseData<BigDecimal>>> getCurMonthOutSum() {
        return transactionService.getCurMonthOutSum().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<HBResponseData<BigDecimal>>> getBalance() {
        return transactionService.getBalance().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<CategoryDto>>> createCategory(CategoryDto category) {
        return categoryService.createCategory(category).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<TransactionDto>>> createTransaction(TransactionDto transaction) {
        return transactionService.addTransaction(transaction).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<CategoryDto>>> editCategory(CategoryDto category) {
        return categoryService.editCategory(category).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<TransactionDto>>> editTransaction(TransactionDto transaction) {
        return transactionService.editTransaction(transaction).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<Integer>>> deleteCategory(String id) {
        return categoryService.deleteCategory(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<Integer>>> deleteTransaction(String id) {
        return transactionService.deleteTransaction(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<HBResponseData<UserDto>>> register(UserEditRequest userEditRequest) {
        return userService.register(userEditRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
