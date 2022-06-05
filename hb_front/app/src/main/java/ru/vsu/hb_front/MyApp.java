package ru.vsu.hb_front;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("e21872c4-3278-4bca-a10f-0e3357ebcfd2")
                .withLogs()
                .build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
    }
}
