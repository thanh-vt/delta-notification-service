package com.delta.config.general;

import com.github.javafaker.Faker;
import com.github.javafaker.LeagueOfLegends;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class FakerConfig {

    @Bean
    public LeagueOfLegends leagueOfLegends() {
        return (new Faker()).leagueOfLegends();
    }

}
