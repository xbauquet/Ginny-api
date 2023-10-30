package com.ginnyci.ginnyapi.githubauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ginny/auth")
public class GithubAuthController {

    private final RestTemplateBuilder restTemplateBuilder;
    private final Environment env;

    @GetMapping
    @CrossOrigin
    public String authWithGithub(@RequestParam Optional<String> code) {
        final String clientId = env.getProperty("app.github.clientId", String.class);
        if (clientId == null) {
            throw new MissingEnvironmentException("app.github.clientId", String.class);
        }

        final String clientSecret = env.getProperty("app.github.clientSecret", String.class);
        if (clientSecret == null) {
            throw new MissingEnvironmentException("app.github.clientSecret", String.class);
        }

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

        final var response = this.restTemplateBuilder.build().exchange(url, HttpMethod.POST, request, GithubResponse.class);
        return response.getBody() != null ? response.getBody().getAccess_token() : null;
    }
}
