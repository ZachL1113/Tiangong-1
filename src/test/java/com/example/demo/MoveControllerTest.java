package com.example.demo;

import com.example.demo.game.controller.MoveController;
import com.example.demo.game.service.GameService;
import com.example.demo.game.service.dto.MoveRequest;
import com.example.demo.game.service.dto.MoveResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MoveControllerTest {

    private MockMvc mockMvc;
    private GameService gameService;

    @BeforeEach
    void setup() {
        gameService = mock(GameService.class);
        Map<String, GameService> map = new HashMap<>();
        map.put("test", gameService);
        MoveController controller = new MoveController(map);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testNewGame() throws Exception {
        when(gameService.newGame(null)).thenReturn("{}\n");
        mockMvc.perform(get("/games/test/new"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}\n"));
        verify(gameService).newGame(null);
    }

    @Test
    void testNewGameUnknown() throws Exception {
        mockMvc.perform(get("/games/unknown/new"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testMove() throws Exception {
        MoveResponse resp = new MoveResponse("{}", true, false);
        when(gameService.move(any(MoveRequest.class))).thenReturn(resp);
        String body = "{\"boardJson\":\"{}\",\"direction\":\"W\",\"pieceId\":1}";
        mockMvc.perform(post("/games/test/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardJson", is("{}")))
                .andExpect(jsonPath("$.valid", is(true)))
                .andExpect(jsonPath("$.success", is(false)));
        verify(gameService).move(any(MoveRequest.class));
    }

    @Test
    void testMoveUnknown() throws Exception {
        mockMvc.perform(post("/games/unknown/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}
