package com.falvojr.audio2text.domain.transcribedaudio.entity.model;

import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.util.FileUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.falvojr.audio2text.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

/**
 * Domain model for TranscribedAudio.
 * Represents an audio file and its associated transcript in the domain layer.
 * Encapsulates the core business logic related to handling audio and transcript data. <br>
 * <br>
 * Responsibilities: <br>
 * - Hold the properties of an audio file, including its ID, name, content (audio stream), and transcript. <br>
 * - Validate the integrity and conformity of the audio file data, ensuring it meets business rules. <br>
 * <br>
 * Adherence to Clean Architecture: <br>
 * - As a domain entity, it is central to the enterprise business logic and rules of the application. <br>
 * - Independent of any external frameworks or data persistence mechanisms.
 *
 * @author falvojr
 */
public class TranscribedAudio {

    private String id;
    private String name;
    private InputStream content;
    private String transcript;

    private static final List<String> VALID_EXTENSIONS = Arrays.asList("flac", "m4a", "mp3", "mp4", "mpeg", "mpga", "oga", "ogg", "wav", "webm");

    public void validate() {
        if (isNullOrEmpty(name) || isNullOrEmpty(transcript) || isNull(content)) {
            throw new EnterpriseBusinessException("Name, transcript and audio content are required.");
        }
        String extension = FileUtils.getExtension(name).toLowerCase();
        if (!VALID_EXTENSIONS.contains(extension)) {
            throw new EnterpriseBusinessException("Invalid audio file extension. Try one of these: %s".formatted(VALID_EXTENSIONS));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}
