package com.falvojr.audio2text.config.usecase;

import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioRepository;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionService;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.CreateTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.DeleteTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.ReadTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.UpdateTranscribedAudioUC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Transcribed Audio use cases.
 * Declares and manages Spring beans for use cases related to Transcribed Audio,
 * thereby centralizing the configuration for domain use cases and facilitating dependency injection. <br>
 * <br>
 * Responsibilities: <br>
 * - Configure and instantiate use case classes with their required dependencies. <br>
 * <br>
 * Adherence to Clean Architecture: <br>
 * - Supports the independence of use cases from the instantiation and configuration details. <br>
 * - Follows Inversion of Control by defining dependencies externally rather than within use cases.
 *
 * @author falvojr
 */
@Configuration
public class TranscribedAudioUCsConfig {

    @Bean
    CreateTranscribedAudioUC createTranscribedAudioUC(TranscribedAudioRepository modelRepository,
                                                      TranscriptionService sttService) {
        return new CreateTranscribedAudioUC(modelRepository, sttService);
    }

    @Bean
    DeleteTranscribedAudioUC deleteTranscribedAudioUC(TranscribedAudioRepository modelRepository) {
        return new DeleteTranscribedAudioUC(modelRepository);
    }

    @Bean
    ReadTranscribedAudioUC readTranscribedAudioUC(TranscribedAudioRepository modelRepository) {
        return new ReadTranscribedAudioUC(modelRepository);
    }

    @Bean
    UpdateTranscribedAudioUC reviewTranscribedAudioUC(TranscribedAudioRepository modelRepository) {
        return new UpdateTranscribedAudioUC(modelRepository);
    }
}
