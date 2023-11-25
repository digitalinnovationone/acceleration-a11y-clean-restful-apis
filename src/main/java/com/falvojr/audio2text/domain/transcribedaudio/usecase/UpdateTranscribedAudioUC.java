package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioRepository;

public record UpdateTranscribedAudioUC(TranscribedAudioRepository repository) {

    public Output execute(Input input) {
        try {
            TranscribedAudio transcribedAudio = this.repository.findById(input.id())
                    .orElseThrow(() -> new ApplicationBusinessException("Audio transcript not found."));
            transcribedAudio.setTranscript(input.reviewedTranscript());

            transcribedAudio.validate();

            return new Output(this.repository.update(transcribedAudio));

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String id, String reviewedTranscript) { }

    public record Output(TranscribedAudio transcribedAudio) { }
}
