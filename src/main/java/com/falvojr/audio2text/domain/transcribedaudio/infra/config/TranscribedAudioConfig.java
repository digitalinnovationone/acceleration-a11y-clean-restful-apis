package com.falvojr.audio2text.domain.transcribedaudio.infra.config;

import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioGateway;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionGateway;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.CreateTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.DeleteTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.ReadTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.UpdateTranscribedAudioUC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranscribedAudioConfig {

    @Bean
    CreateTranscribedAudioUC createTranscribedAudioUC(TranscribedAudioGateway modelGateway, TranscriptionGateway sttGateway) {
        return new CreateTranscribedAudioUC(modelGateway, sttGateway);
    }

    @Bean
    DeleteTranscribedAudioUC deleteTranscribedAudioUC(TranscribedAudioGateway modelGateway) {
        return new DeleteTranscribedAudioUC(modelGateway);
    }

    @Bean
    ReadTranscribedAudioUC readTranscribedAudioUC(TranscribedAudioGateway modelGateway) {
        return new ReadTranscribedAudioUC(modelGateway);
    }

    @Bean
    UpdateTranscribedAudioUC reviewTranscribedAudioUC(TranscribedAudioGateway modelGateway) {
        return new UpdateTranscribedAudioUC(modelGateway);
    }
}
