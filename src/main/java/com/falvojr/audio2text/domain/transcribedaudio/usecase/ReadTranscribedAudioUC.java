package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioRepository;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;

public record ReadTranscribedAudioUC(TranscribedAudioRepository repository) {

    public Output execute(Input input) {
        try {
            TranscribedAudio transcribedAudio = this.repository.findById(input.id())
                    .orElseThrow(() -> new ApplicationBusinessException("Audio transcript not found."));

            return new Output(transcribedAudio);

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String id) { }

    public record Output(TranscribedAudio transcribedAudio) { }
}
