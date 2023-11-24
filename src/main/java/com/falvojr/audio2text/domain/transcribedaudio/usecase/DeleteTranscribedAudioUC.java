package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioGateway;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;

public record DeleteTranscribedAudioUC(TranscribedAudioGateway modelGateway) {

    public void execute(Input input) {
        try {
            TranscribedAudio transcribedAudio = this.modelGateway.findById(input.id())
                    .orElseThrow(() -> new ApplicationBusinessException("Audio transcript not found."));

            this.modelGateway.delete(transcribedAudio.getId());

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String id) { }
}
