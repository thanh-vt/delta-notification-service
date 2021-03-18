package com.delta.config;

import com.delta.model.OutputMessage;
import com.github.javafaker.Faker;
import com.github.javafaker.LeagueOfLegends;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class FakerConfig {

    @Bean
    public LeagueOfLegends leagueOfLegends() {
        return (new Faker()).leagueOfLegends();
    }

}
