package com.emerdinger.smashtorunament.infraestructure.configuration.Filters;

import com.emerdinger.smashtorunament.helpers.errors.AuthApiError;
import com.emerdinger.smashtorunament.infraestructure.drivenAdapter.AuthApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthWebFilter implements WebFilter {

    private final AuthApiService authApiService;

    private static final String BASE_URI = "/api/v1/tournament";

    private static final Set<String> PROTECTED_ROUTES = Set.of(
            BASE_URI + "/basic-tournament/update-status",
            BASE_URI + "/basic-tournament"
    );

    private static final Pattern DYNAMIC_ROUTE_PATTERN = Pattern.compile(BASE_URI + "/basic-tournament/(find/\\w+|delete/\\w+)");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isProtectedRoute(path)) {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");

            return isValidSession(token)
                    .flatMap(response -> {
                        exchange.getAttributes().put("userId", response);
                        return chain.filter(exchange);
                    })
                    .onErrorResume(AuthApiError.class, e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.valueOf(e.getCode()));
                        String errorMessage = e.getMessage();
                        byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    });
        }

        return chain.filter(exchange);
    }

    private boolean isProtectedRoute(String path) {
        return PROTECTED_ROUTES.contains(path) || DYNAMIC_ROUTE_PATTERN.matcher(path).matches();
    }

    private Mono<String> isValidSession(String token) {
        return authApiService.verifyUserAuth(token);
    }
}
