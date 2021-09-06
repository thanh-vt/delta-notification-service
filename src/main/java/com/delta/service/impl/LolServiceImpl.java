package com.delta.service.impl;

import com.delta.model.webflux.LolNews;
import com.delta.service.LolService;
import com.github.javafaker.LeagueOfLegends;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author thanhvt
 * @created 15/08/2021 - 5:50 CH
 * @project vengeance
 * @since 1.0
 **/
@Service
public class LolServiceImpl implements LolService {

    private final LeagueOfLegends leagueOfLegends;

    private final AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    public LolServiceImpl(LeagueOfLegends leagueOfLegends,
        AsyncTaskExecutor asyncTaskExecutor) {
        this.leagueOfLegends = leagueOfLegends;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public Flux<LolNews> publishNews() {
        return Flux.interval(Duration.of(30, ChronoUnit.SECONDS),
            Schedulers.boundedElastic()).map(number -> LolNews.builder()
            .champion(this.leagueOfLegends.champion())
            .location(this.leagueOfLegends.location())
            .quote(this.leagueOfLegends.quote())
            .summonerSpell(this.leagueOfLegends.summonerSpell())
            .masteries(this.leagueOfLegends.masteries())
            .rank(this.leagueOfLegends.rank())
            .build());
//        return Flux.just(new Employee("29750","AtomPtit") , new Employee("18273", "HungCD"));
    }
}
