package com.delta.service;

import com.delta.model.webflux.Employee;
import com.delta.model.webflux.LolNews;
import reactor.core.publisher.Flux;

/**
 * @author thanhvt
 * @created 15/08/2021 - 5:49 CH
 * @project vengeance
 * @since 1.0
 **/
public interface LolService {

    Flux<LolNews> publishNews();
}
