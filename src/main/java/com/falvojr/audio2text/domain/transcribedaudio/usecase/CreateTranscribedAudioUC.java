package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioGateway;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionGateway;

import java.io.InputStream;

public record CreateTranscribedAudioUC(TranscribedAudioGateway modelGateway, TranscriptionGateway sttGateway) {

    public Output execute(Input input) {
        try {
            var audio = new TranscribedAudio();
            audio.setName(input.fileName());
            audio.setContent(input.fileContent());

            audio.validate();

            try {
                String transcript = this.sttGateway.generateTranscript(audio.getName(), audio.getContent());
                audio.setTranscript(transcript);
            } catch (Exception httpClientException) {
                httpClientException.printStackTrace();
                throw new ApplicationBusinessException("Speech-To-Text gateway integration error.");
            }

            return new Output(this.modelGateway.create(audio));

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String fileName, InputStream fileContent) { }

    public record Output(TranscribedAudio transcribedAudio) { }
}
