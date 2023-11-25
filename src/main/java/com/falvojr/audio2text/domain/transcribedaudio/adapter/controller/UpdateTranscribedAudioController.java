package com.falvojr.audio2text.domain.transcribedaudio.adapter.controller;

import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.UpdateTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.UpdateTranscribedAudioUC.Input;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.UpdateTranscribedAudioUC.Output;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record UpdateTranscribedAudioController(UpdateTranscribedAudioUC updateTranscribedAudioUC) {

    @PatchMapping("/transcribed-audios/{id}")
    public ResponseEntity<Response> updateAudioTranscript(@PathVariable String id, @RequestBody Request request) {
        Input input = new Input(id, request.transcript());
        Output output = updateTranscribedAudioUC.execute(input);

        TranscribedAudio transcribedAudio = output.transcribedAudio();
        return ResponseEntity.ok(new Response(transcribedAudio.getId(), transcribedAudio.getTranscript()));
    }

    public record Request(String transcript) { }

    public record Response(String id, String transcript) { }
}
