package com.delta.scheduler;

import com.delta.model.OutputMessage;
import com.github.javafaker.LeagueOfLegends;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LolNewsPublisher {

    private final LeagueOfLegends leagueOfLegends;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    public LolNewsPublisher(
        LeagueOfLegends leagueOfLegends,
        SimpMessageSendingOperations simpMessageSendingOperations) {
        this.leagueOfLegends = leagueOfLegends;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Scheduled(fixedDelay = 10000)
    public void publishQuotes() {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        this.simpMessageSendingOperations.convertAndSend("/topic/notification",
            new OutputMessage("Lol (@Scheduled)", this.leagueOfLegends.quote(), time));
    }

    // 10:15 AM on the 15th day of every month
    @Scheduled(cron = "0 15 10 * * ?", zone = "GMT+7")
    public void publishChampions() {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        this.simpMessageSendingOperations.convertAndSend("/topic/notification",
            new OutputMessage("Lol (@Scheduled)", this.leagueOfLegends.champion(), time));
    }

}
