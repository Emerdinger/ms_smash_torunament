package com.emerdinger.smashtorunament.infraestructure.configuration;

import com.emerdinger.smashtorunament.application.handler.TournamentManejador;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.domain.useCase.TournamentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public TournamentManejador tournamentManejador(TournamentUseCase tournamentUseCase) {
        return new TournamentManejador(tournamentUseCase);
    }

    @Bean
    public TournamentUseCase tournamentUseCase(TournamentRepository tournamentRepository) {
        return new TournamentUseCase(tournamentRepository);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:8080")
                .build();
    }
}
