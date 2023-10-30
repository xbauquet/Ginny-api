package com.ginnyci.ginnyapi.githubauth;

import lombok.Data;

@Data
class GithubResponse {
    private String access_token;
    private String token_type;
    private String scope;
}
