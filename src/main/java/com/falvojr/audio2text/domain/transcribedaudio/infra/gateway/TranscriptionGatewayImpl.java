package com.falvojr.audio2text.domain.transcribedaudio.infra.gateway;

import com.falvojr.audio2text.util.FileUtils;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionGateway;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@FeignClient(name = "openai", url = "${openai.base-url}", configuration = TranscriptionGatewayImpl.Config.class)
public interface TranscriptionGatewayImpl extends TranscriptionGateway {

    @PostMapping(value = "/audio/transcriptions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String generateTranscript(Map<String, ?> formData);

    @Override
    default String generateTranscript(String fileName, InputStream fileContent) {
        try {
            String extension = FileUtils.getExtension(fileName);
            Path tempFilePath = Files.createTempFile("transcript_", ".%s".formatted(extension));
            Files.copy(fileContent, tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            Map<String, Object> formData = new HashMap<>();
            formData.put("file", tempFilePath.toFile());
            formData.put("model", "whisper-1");
            formData.put("response_format", "text");

            String transcript = generateTranscript(formData);

            Files.delete(tempFilePath);

            return transcript;
        } catch (IOException ioException) {
            throw new RuntimeException("File copy error on OpenAI API integration.", ioException);
        }
    }

    class Config {

        @Value("${openai.api-key}")
        private String apiKey;

        @Bean
        public RequestInterceptor bearerTokenRequestInterceptor() {
            return template -> template.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }

        @Bean
        public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
