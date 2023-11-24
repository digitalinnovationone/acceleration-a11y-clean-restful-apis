package com.falvojr.audio2text.domain.transcribedaudio.entity.gateway;

import java.io.InputStream;

public interface TranscriptionGateway {

    String generateTranscript(String fileName, InputStream fileContent);
}
