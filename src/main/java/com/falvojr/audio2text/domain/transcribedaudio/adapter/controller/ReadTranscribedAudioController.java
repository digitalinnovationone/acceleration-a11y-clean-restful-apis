package com.falvojr.audio2text.domain.transcribedaudio.adapter.controller;

import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.ReadTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.ReadTranscribedAudioUC.Input;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.ReadTranscribedAudioUC.Output;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record ReadTranscribedAudioController(ReadTranscribedAudioUC readTranscribedAudioUC) {

    @GetMapping("/transcribed-audios/{id}")
    public ResponseEntity<Response> getTranscribedAudio(@PathVariable String id) {
        Input input = new Input(id);
        Output output = readTranscribedAudioUC.execute(input);

        TranscribedAudio transcribedAudio = output.transcribedAudio();
        return ResponseEntity.ok(new Response(transcribedAudio.getId(), transcribedAudio.getName(), transcribedAudio.getTranscript()));
    }

    public record Response(String id, String name, String transcript) { }
}

