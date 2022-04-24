package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.repository.StatisticsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository repository;

    public StatisticsService(StatisticsRepository repository) {
        this.repository = repository;
    }

    public Result<UserStatisticsRecommendations, HBError> getUserStatistics(String email) {
        return Results.success(repository.getUserOutTransactionStatistics(email))
                .map(this::getUserRecommendations, it -> (HBError) it);
    }

    public Result<GlobalStatistics, HBError> getGlobalStatistics() {
        return Results.success(repository.getGlobalStatistics());
    }

    private UserStatisticsRecommendations getUserRecommendations(
            UserStatisticsRecommendations statistics) {
        List<String> recommendations = new ArrayList<>();

        String sumRecommendation = getSumRecommendation(statistics.getCurrMonthExpensesSum(), statistics.getPrevMonthExpensesSum());
        recommendations.add(sumRecommendation);

        recommendations.addAll(Objects.requireNonNull(getAbsencesCategoriesCurrMonth(statistics)));
        recommendations.addAll(Objects.requireNonNull(getAbsencesCategoriesPrevMonth(statistics)));
        recommendations.addAll(Objects.requireNonNull(getRecommendationsForBothMonths(statistics)));

        statistics.setRecommendations(recommendations);
        return statistics;
    }

    private List<String> getRecommendationsForBothMonths(UserStatisticsRecommendations statistics) {
        return statistics.getCurrMonthExpenses().keySet().stream()
                .filter(it -> statistics.getPrevMonthExpenses().containsKey(it))
                .map(it -> getRecommendationForBothCategories(it, statistics))
                .collect(Collectors.toList());

    }

    private String getRecommendationForBothCategories(String categoryName, UserStatisticsRecommendations statistics) {
        var currSum = statistics.getCurrMonthExpenses().get(categoryName);
        var prevSum = statistics.getPrevMonthExpenses().get(categoryName);
        int compare = currSum.compareTo(prevSum);
        String recommendation;
        if (compare > 0) {
            recommendation = "Расходы по категории \"" + categoryName +
                    "\" в текущем месяце превышают расходы по категории \"" + categoryName +
                    "\" в предыдущем месяце на " +
                    currSum.subtract(prevSum).setScale(2, RoundingMode.HALF_UP) + "руб.";
            if (prevSum.compareTo(BigDecimal.ZERO) > 0) {
                recommendation += "(" + currSum.divide(prevSum, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).subtract(BigDecimal.valueOf(100)) +
                        "%). Стоит задуматься о сокращении расходов на данную категорию.";
            }
        } else if (compare == 0) {
            recommendation = "Расходы по категории \"" + categoryName +
                    "\" в текущем месяце равны расходам по категории \"" + categoryName +
                    "\" в предыдущем месяце. Так держать!";

        } else {
            recommendation = "Расходы по категории \"" + categoryName +
                    "\" в текущем месяце меньше расходов по категории \"" + categoryName +
                    "\" в предыдущем месяце на " +
                    prevSum.subtract(currSum).setScale(2, RoundingMode.HALF_UP) + "руб.";
            if (currSum.compareTo(BigDecimal.ZERO) > 0) {
                recommendation += "(" + BigDecimal.valueOf(100).subtract(currSum.divide(prevSum, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))) +
                        "%). Соблюдайте такую динамику!.";
            }
        }
        return recommendation;
    }

    private List<String> getAbsencesCategoriesPrevMonth(UserStatisticsRecommendations statistics) {
        return statistics.getCurrMonthExpenses().keySet().stream()//категории, которые есть в текущем месяце, но отсутствуют в пред месяце => расходы в текущем месяце появились
                .filter(it -> !statistics.getPrevMonthExpenses().containsKey(it))
                .map(it -> "В текущем месяце у вас отсутствуют расходы по категории \"" + it + "\", в отличие от предыдущего месяца. Продолжайте в том же духе!")
                .collect(Collectors.toList());
    }

    private List<String> getAbsencesCategoriesCurrMonth(UserStatisticsRecommendations statistics) {
        return statistics.getPrevMonthExpenses().keySet().stream()//категории, которые есть в пред месяце, но отсутствуют в текущем месяце => расходов в текущем месяце нет
                .filter(it -> !statistics.getCurrMonthExpenses().containsKey(it))
                .map(it -> "В текущем месяце у вас появились расходы по категории \"" + it + "\", в отличие от предыдущего месяца. Возможно стоит подумать, действительно ли они необходимы.")
                .collect(Collectors.toList());
    }

    private String getSumRecommendation(BigDecimal curr, BigDecimal prev) {
        String sumRecommendation;
        if (curr.compareTo(prev) > 0) {
            String overrun = curr
                    .subtract(prev)
                    .setScale(2, RoundingMode.HALF_UP) + " руб.";
            if (prev.compareTo(BigDecimal.ZERO) > 0) {
                overrun += " (" + curr
                        .divide(prev, 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).subtract(BigDecimal.valueOf(100)) + "%)";
            }
            sumRecommendation = "Вы привысили расходы в текущем месяце в сравнении с предыдущим на ";
            sumRecommendation += overrun + ".";
        } else {
            sumRecommendation = "Ваши расходы в текущем месяце не превышают расходы в предыдущем. Так Держать!";
        }
        return sumRecommendation;
    }
}
