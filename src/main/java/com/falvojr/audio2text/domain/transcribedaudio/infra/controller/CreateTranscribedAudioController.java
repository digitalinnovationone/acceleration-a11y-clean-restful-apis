package com.falvojr.audio2text.domain.transcribedaudio.infra.controller;

import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.CreateTranscribedAudioUC;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.CreateTranscribedAudioUC.Input;
import com.falvojr.audio2text.domain.transcribedaudio.usecase.CreateTranscribedAudioUC.Output;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
public record CreateTranscribedAudioController(CreateTranscribedAudioUC createTranscribedAudioUC) {

    @PostMapping(value = "/transcribed-audios", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> createAudio(@RequestParam("file") MultipartFile file) throws IOException {
        Input input = new Input(file.getOriginalFilename(), file.getInputStream());
        Output output = this.createTranscribedAudioUC.execute(input);

        TranscribedAudio transcribedAudio = output.transcribedAudio();
        Response resp = new Response(transcribedAudio.getId(), transcribedAudio.getTranscript());

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/transcribed-audios/{id}")
                .buildAndExpand(resp.id()).toUri();

        return ResponseEntity.created(location).body(resp);
    }

    public record Response(String id, String transcript) { }
}
