package com.falvojr.audio2text.domain.transcribedaudio.adapter.gateway;

import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionService;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.io.InputStream;

/**
 * Feign client adapter for interfacing with an external transcription service, specifically OpenAI's API.
 * It implements {@link TranscriptionService} to provide a concrete mechanism for generating transcripts
 * from audio content through a RESTful service. <br>
 * <br>
 * Responsibilities: <br>
 * - Handle HTTP requests to the transcription service API. <br>
 * - Manage temporary storage and conversion of audio files for API consumption. <br>
 * - Translate API responses into domain-understandable format. <br>
 * <br>
 * Adherence to Clean Architecture: <br>
 * - Follows the Interface Adapter principle, serving as a bridge between external services and the domain. <br>
 * - Supports the interchangeability of transcription services by conforming to a domain-defined interface. <br>
 * - Ensures external API changes have minimal impact on the domain logic.
 *
 * @author falvojr
 */
@FeignClient(name = "openai", url = "${openai.base-url}", configuration = TranscriptionServiceImpl.Config.class)
public interface TranscriptionServiceImpl extends TranscriptionService {

    @Override
    default String generateTranscript(String fileName, InputStream fileContent) {
        // Use this default method to adapt contract parameters to OpenAI API endpoint.
        return null;
    }

    // TODO 2. Respecting the TranscriptionService contract, consumes "audio/transcription" on OpenAI API!
    // OpenAI API Reference: https://platform.openai.com/docs/api-reference/audio/createTranscription

    class Config {

        @Value("${openai.api-key}")
        private String apiKey;

        @Bean
        public RequestInterceptor bearerTokenRequestInterceptor() {
            // RequestInterceptor to put the "Authorization" header on all mapped endpoints.
            return template -> template.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }

        @Bean
        public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
            // Specialized encoder to support "form-data" (required to send files).
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
