package com.falvojr.audio2text.domain.transcribedaudio.entity.gateway;

import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;

import java.util.Optional;

/**
 * Interface for the {@link TranscribedAudio} model database gateway.
 * This interface defines the methods for handling {@link TranscribedAudio} data.
 */
public interface TranscribedAudioGateway {

    Optional<TranscribedAudio> findById(String id);

    TranscribedAudio create(TranscribedAudio model);

    TranscribedAudio update(TranscribedAudio model);

    void delete(String id);
}
