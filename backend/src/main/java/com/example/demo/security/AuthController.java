package com.example.demo.security;

import com.example.demo.player.Player;
import com.example.demo.player.PlayerRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final PlayerRepository playerRepository;

    public AuthController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/me")
    public Player me(OAuth2AuthenticationToken authentication) {
        if (authentication == null) {
            return null;
        }
        String provider = authentication.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();
        return playerRepository.findByProviderAndProviderId(provider, providerId);
    }

    @GetMapping("/oauth2/callback")
    public String callback() {
        return "OAuth2 login successful";
    }
}
