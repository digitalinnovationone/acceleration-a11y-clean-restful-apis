package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioGateway;

public record UpdateTranscribedAudioUC(TranscribedAudioGateway modelGateway) {

    public Output execute(Input input) {
        try {
            TranscribedAudio transcribedAudio = this.modelGateway.findById(input.id())
                    .orElseThrow(() -> new ApplicationBusinessException("Audio transcript not found."));
            transcribedAudio.setTranscript(input.reviewedTranscript());

            return new Output(this.modelGateway.update(transcribedAudio));

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String id, String reviewedTranscript) { }

    public record Output(TranscribedAudio transcribedAudio) { }
}
