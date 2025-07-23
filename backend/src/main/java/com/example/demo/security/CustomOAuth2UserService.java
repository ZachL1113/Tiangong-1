package com.example.demo.security;

import com.example.demo.player.Player;
import com.example.demo.player.PlayerRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final PlayerRepository playerRepository;

    public CustomOAuth2UserService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);

        String provider = request.getClientRegistration().getRegistrationId();
        String providerId = user.getName();
        String name = (String) user.getAttributes().get("name");
        String email = (String) user.getAttributes().get("email");

        Player player = playerRepository.findByProviderAndProviderId(provider, providerId);
        if (player == null) {
            player = new Player();
            player.setProvider(provider);
            player.setProviderId(providerId);
        }
        if (name != null) {
            player.setName(name);
        }
        if (email != null) {
            player.setEmail(email);
        }
        playerRepository.save(player);

        return user;
    }
}
