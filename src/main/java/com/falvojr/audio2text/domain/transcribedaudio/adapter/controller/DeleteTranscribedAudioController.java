package com.falvojr.audio2text.domain.transcribedaudio.adapter.controller;

import com.falvojr.audio2text.domain.transcribedaudio.usecase.DeleteTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.DeleteTranscribedAudioUC.Input;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record DeleteTranscribedAudioController(DeleteTranscribedAudioUC deleteTranscribedAudioUC) {

    @DeleteMapping("/transcribed-audios/{id}")
    public ResponseEntity<Void> deleteAudio(@PathVariable String id) {
        deleteTranscribedAudioUC.execute(new Input(id));
        return ResponseEntity.noContent().build();
    }
}
