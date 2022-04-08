package ru.vsu.hb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.vsu.hb")
public class HouseHoldBudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseHoldBudgetApplication.class, args);
    }
}
