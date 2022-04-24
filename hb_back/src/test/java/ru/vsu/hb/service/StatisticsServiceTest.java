package ru.vsu.hb.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.hb.dto.GlobalStatistics;
import ru.vsu.hb.dto.UserStatisticsRecommendations;
import ru.vsu.hb.persistence.repository.StatisticsRepository;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class StatisticsServiceTest {

    private final StatisticsRepository repository = Mockito.mock(StatisticsRepository.class);

    private final StatisticsService service = new StatisticsService(repository);

    @Test
    public void getSumRecommendationTest() {
        var stats = new UserStatisticsRecommendations();
        stats.setPrevMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.ONE, "3", BigDecimal.valueOf(2)));
        stats.setCurrMonthExpenses(Map.of("4", BigDecimal.ONE, "2", BigDecimal.valueOf(2), "3", BigDecimal.valueOf(2)));
        Mockito.when(repository.getUserOutTransactionStatistics(any())).thenReturn(stats);
        var st = service.getUserStatistics("").optional().orElse(null);
        assertEquals(5, st.getRecommendations().size());
        assertEquals("Вы привысили расходы в текущем месяце в сравнении с предыдущим на 1.00 руб. (25.00%).", st.getRecommendations().get(0));
        assertEquals("В текущем месяце у вас появились расходы по категории \"1\", в отличие от предыдущего месяца. Возможно стоит подумать, действительно ли они необходимы.", st.getRecommendations().get(1));
        assertEquals("В текущем месяце у вас отсутствуют расходы по категории \"4\", в отличие от предыдущего месяца. Продолжайте в том же духе!", st.getRecommendations().get(2));
        assertEquals("Расходы по категории \"3\" в текущем месяце равны расходам по категории \"3\" в предыдущем месяце. Так держать!", st.getRecommendations().get(3));
        assertEquals("Расходы по категории \"2\" в текущем месяце превышают расходы по категории \"2\" в предыдущем месяце на 1.00руб.(100.00%). Стоит задуматься о сокращении расходов на данную категорию.", st.getRecommendations().get(4));
    }

    @Test
    public void getRecommendationTest() {
        var stats = new UserStatisticsRecommendations();
        stats.setPrevMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.valueOf(4), "3", BigDecimal.valueOf(2)));
        stats.setCurrMonthExpenses(Map.of("4", BigDecimal.ONE, "2", BigDecimal.valueOf(2), "3", BigDecimal.valueOf(2)));
        Mockito.when(repository.getUserOutTransactionStatistics(any())).thenReturn(stats);
        var st = service.getUserStatistics("").optional().orElse(null);
        assertEquals(5, st.getRecommendations().size());
        assertEquals("Ваши расходы в текущем месяце не превышают расходы в предыдущем. Так Держать!", st.getRecommendations().get(0));
        assertEquals("В текущем месяце у вас появились расходы по категории \"1\", в отличие от предыдущего месяца. Возможно стоит подумать, действительно ли они необходимы.", st.getRecommendations().get(1));
        assertEquals("В текущем месяце у вас отсутствуют расходы по категории \"4\", в отличие от предыдущего месяца. Продолжайте в том же духе!", st.getRecommendations().get(2));
        assertEquals("Расходы по категории \"3\" в текущем месяце равны расходам по категории \"3\" в предыдущем месяце. Так держать!", st.getRecommendations().get(3));
        assertEquals("Расходы по категории \"2\" в текущем месяце меньше расходов по категории \"2\" в предыдущем месяце на 2.00руб.(50.00%). Соблюдайте такую динамику!.", st.getRecommendations().get(4));
    }

}