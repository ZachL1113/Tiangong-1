package com.example.demo.Config;

import com.example.demo.game.service.GameService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.game.service.Game2048Service;
import com.example.demo.game.service.HuarongdaoService;

@Configuration
public class GameServiceConfig {

    @Bean
    public Map<String, GameService> gameServices(
            @Qualifier("2048") Game2048Service game2048,
            @Qualifier("huarongdao") HuarongdaoService huarongdao) {

        Map<String, GameService> map = new HashMap<>();
        map.put("2048", game2048);
        map.put("huarongdao", huarongdao);
        return map;
    }
}

