package com.falvojr.audio2text.domain.transcribedaudio.entity.gateway;

import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;

import java.util.Optional;

/**
 * Repository interface for TranscribedAudio entity.
 * Defines the contract for persistence operations on TranscribedAudio entities.
 * This interface represents an abstraction that the domain layer interacts with,
 * allowing for different implementations of data persistence mechanisms. <br>
 *
 * Responsibilities: <br>
 * - Provide methods for standard CRUD operations (Create, Read, Update, Delete). <br>
 *
 * Adherence to Clean Architecture: <br>
 * - Sits at the boundary between the domain and data source layers. <br>
 * - Allows for decoupling the domain logic from specific data persistence technologies.
 *
 * @author falvojr
 */
public interface TranscribedAudioRepository {

    Optional<TranscribedAudio> findById(String id);

    TranscribedAudio create(TranscribedAudio model);

    TranscribedAudio update(TranscribedAudio model);

    void delete(String id);
}
