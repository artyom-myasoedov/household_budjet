package ru.vsu.hb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {
        "ru.vsu.hb.persistence.entity",
})
@EnableJpaRepositories(basePackages = "ru.vsu.hb.persistence.repository")
@EnableTransactionManagement
public class DataBaseConfig {
}
