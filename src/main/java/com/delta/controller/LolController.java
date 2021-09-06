package com.delta.controller;

import com.delta.model.webflux.Employee;
import com.delta.model.webflux.LolNews;
import com.delta.service.LolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author thanhvt
 * @created 15/08/2021 - 5:49 CH
 * @project vengeance
 * @since 1.0
 **/
@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping(value = "/lol")
public class LolController {

    private final LolService lolService;

    @Autowired
    public LolController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping("/news")
    private Flux<LolNews> getNews() {
        return lolService.publishNews();
    }
}
