package com.ginnyci.ginnyapi.githubauth;

import com.ginnyci.ginnyapi.EnvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ginny/auth")
@CrossOrigin(origins = "*")
public class GithubAuthController {

    private final RestTemplate restTemplate;
    private final EnvService env;

    @GetMapping("/companion")
    public String companionAuth(@RequestParam String code) {
        return this.auth(code, env.COMPANION_CLIENT_ID, env.COMPANION_CLIENT_SECRET);
    }

    @GetMapping("/app")
    public String appAuth(@RequestParam String code) {
        return this.auth(code, env.APP_CLIENT_ID, env.APP_CLIENT_SECRET);
    }

    private String auth(final String code, final String clientId, final String clientSecret) {
        final var headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        final var request = new HttpEntity<>(headers);

        final var url = UriComponentsBuilder
                .fromHttpUrl("https://github.com/login/oauth/access_token")
                .queryParam("code", code)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .encode()
                .toUriString();
        final var response = this.restTemplate.exchange(url, HttpMethod.POST, request, GithubResponse.class);
        return response.getBody() != null ? response.getBody().getAccess_token() : null;
    }
}
