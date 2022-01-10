package com.classpath.oauth2client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class UserController {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping
    public Map<String, String> printUserDetails (OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Map<String,String> response = new HashMap<>();
        final OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
        response.put("Name ", principal.getName());
        response.put("Is Authenticated ", String.valueOf(oAuth2AuthenticationToken.isAuthenticated()));

        final OAuth2AuthorizedClient oAuth2AuthorizedClient = this.oAuth2AuthorizedClientService
                .loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), oAuth2AuthenticationToken.getName());
        response.put("Access token : ", oAuth2AuthorizedClient.getAccessToken().getTokenValue());
        response.put("Access Token type : ", oAuth2AuthorizedClient.getAccessToken().getTokenType().getValue());
        response.put("Scopes : ", oAuth2AuthorizedClient.getAccessToken().getScopes().toString());
        response.put("Access Token Issuer : ", oAuth2AuthorizedClient.getAccessToken().getIssuedAt().toString());
        response.put("Access Token Expiry : ", oAuth2AuthorizedClient.getAccessToken().getExpiresAt().toString());
        return response;
    }

}