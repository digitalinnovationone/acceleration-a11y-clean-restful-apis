package com.falvojr.audio2text.domain.transcribedaudio.entity.model;

import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.util.FileUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.falvojr.audio2text.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class TranscribedAudio {

    private String id;
    private String name;
    private InputStream content;
    private String transcript;

    private static final List<String> VALID_EXTENSIONS = Arrays.asList("flac", "m4a", "mp3", "mp4", "mpeg", "mpga", "oga", "ogg", "wav", "webm");

    public void validate() {
        if (isNullOrEmpty(name) || isNull(content)) {
            throw new EnterpriseBusinessException("Name and audio content are required.");
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
