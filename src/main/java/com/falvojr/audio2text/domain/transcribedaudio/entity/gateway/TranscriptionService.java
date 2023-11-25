package com.falvojr.audio2text.domain.transcribedaudio.entity.gateway;

import java.io.InputStream;

/**
 * Interface for transcription services.
 * Defines the contract for converting audio content into text.
 * Acts as a gateway for interacting with external speech-to-text services. <br>
 * <br>
 * Responsibilities: <br>
 * - Provide method for generating transcript from audio content. <br>
 * <br>
 * Adherence to Clean Architecture: <br>
 * - Represents a boundary between the domain and external service providers. <br>
 * - Enables the core application to remain agnostic of the specific implementation of transcription services.
 *
 * @author falvojr
 */
public interface TranscriptionService {

    String generateTranscript(String fileName, InputStream fileContent);
}
