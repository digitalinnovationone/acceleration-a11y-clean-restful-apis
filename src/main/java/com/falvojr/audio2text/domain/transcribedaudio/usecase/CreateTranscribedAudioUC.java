package com.falvojr.audio2text.domain.transcribedaudio.usecase;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioRepository;
import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscriptionService;

import java.io.InputStream;

/**
 * Use case for creating a TranscribedAudio entity.
 * Orchestrates the process of transcribing audio content and persisting it.
 * This class sits in the application layer of Clean Architecture and coordinates the flow of data between
 * the domain and the external interfaces. <br>
 * <br>
 * Responsibilities: <br>
 * - Validates input data for creating a TranscribedAudio entity. <br>
 * - Invokes the transcription service to generate the transcript from audio content. <br>
 * - Persists the transcribed audio using the repository. <br>
 * - Handles and propagates exceptions appropriately. <br>
 * <br>
 * Adherence to Clean Architecture: <br>
 * - Maintains independence from external frameworks and data sources. <br>
 * - Relies on abstractions (TranscribedAudioRepository and TranscriptionService) to interact with other layers. <br>
 * - Encapsulates the business logic and rules specific to creating a TranscribedAudio entity.
 *
 * @author falvojr
 */
public record CreateTranscribedAudioUC(TranscribedAudioRepository repository, TranscriptionService sttService) {

    public Output execute(Input input) {
        try {
            var audio = new TranscribedAudio();
            audio.setName(input.fileName());
            audio.setContent(input.fileContent());

            // DONE! 3. Orchestrate the integration with transcription service.
            // Remember that every TranscribedAudio must have a transcript before it can be saved!
            
            try {
                String transcript = sttService.generateTranscript(audio.getName(), audio.getContent());
                audio.setTranscript(transcript);
            } catch (Exception httpException) {
                throw new ApplicationBusinessException("Speech-To-Text service integration error.");
            }

            audio.validate();

            return new Output(this.repository.create(audio));

        } catch (EnterpriseBusinessException | ApplicationBusinessException handledException) {
            throw handledException;
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected exception, check the logs.", unexpected);
        }
    }

    public record Input(String fileName, InputStream fileContent) { }

    public record Output(TranscribedAudio transcribedAudio) { }
}
