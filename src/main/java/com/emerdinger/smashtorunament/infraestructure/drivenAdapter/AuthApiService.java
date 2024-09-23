package com.emerdinger.smashtorunament.infraestructure.drivenAdapter;

import com.emerdinger.smashtorunament.helpers.errors.AuthApiError;
import com.emerdinger.smashtorunament.helpers.errors.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthApiService {

    private final WebClient webClient;

    public Mono<String> verifyUserAuth(String token) {
        return webClient.get()
                .uri("/api/v1/auth/user/verifyAuth")
                .header("Authorization", token)
                .header("appIdVerify", "66e7af23a0e038950c880346")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> clientResponse
                        .bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new AuthApiError(errorBody, clientResponse.statusCode().value()))))
                .bodyToMono(Map.class)
                .map(response -> response.get("Authorized").toString())
                .onErrorResume(WebClientResponseException.class, e -> Mono.error(new BadRequestError("Error during API call: " + e.getMessage())));
    }

}
