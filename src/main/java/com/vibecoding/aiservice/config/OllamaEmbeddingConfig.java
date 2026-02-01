package com.vibecoding.aiservice.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OllamaEmbeddingConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String ollamaBaseUrl;

    @Value("${spring.ai.ollama.embedding.model}")
    private String embeddingModelName;


    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    @Bean
    public  ModelManagementOptions modelManagementOptions(){
        return ModelManagementOptions.builder().build();
    }

    @Bean
    public EmbeddingModel embeddingModel() {

        OllamaApi ollamaApi = new OllamaApi();

        OllamaOptions defaultOptions = OllamaOptions.builder()
                .model(embeddingModelName)
                .build();

        return new OllamaEmbeddingModel(
                ollamaApi,
                defaultOptions,
                observationRegistry(),
                modelManagementOptions()
        );
    }
}