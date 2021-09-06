package com.delta.model.webflux;

import lombok.Builder;
import lombok.Data;

/**
 * @author thanhvt
 * @created 15/08/2021 - 9:06 CH
 * @project vengeance
 * @since 1.0
 **/
@Data
@Builder
public class LolNews {

    private String champion;

    private String location;

    private String quote;

    private String summonerSpell;

    private String masteries;

    private String rank;

}
