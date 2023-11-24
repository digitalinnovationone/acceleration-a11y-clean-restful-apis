package com.falvojr.audio2text.domain.transcribedaudio.infra.gateway;

import com.falvojr.audio2text.domain.transcribedaudio.entity.gateway.TranscribedAudioGateway;
import com.falvojr.audio2text.domain.transcribedaudio.entity.model.TranscribedAudio;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Service
public record TranscribedAudioGatewayImpl(
        GridFsTemplate gridFsTemplate,
        GridFsOperations gridFsOps,
        MongoTemplate mongoTemplate) implements TranscribedAudioGateway {

    private static final String METADATA_TRANSCRIPT_FIELD = "transcript";

    @Override
    public Optional<TranscribedAudio> findById(String id) {
        final GridFSFile gridFSFile = this.gridFsTemplate.findOne(this.createQueryById(id));
        return ofNullable(this.convertGridFSFileToModel(gridFSFile));
    }

    @Override
    public TranscribedAudio create(TranscribedAudio model) {
        DBObject metadata = new BasicDBObject();
        metadata.put(METADATA_TRANSCRIPT_FIELD, model.getTranscript());

        ObjectId objectId = this.gridFsTemplate.store(model.getContent(), model.getName(), metadata);
        model.setId(objectId.toString());

        return model;
    }

    @Override
    public TranscribedAudio update(TranscribedAudio model) {
        // Assuming that the content of the file doesn't change, only transcript metadata.
        Query query = this.createQueryById(model.getId());
        Update update = new Update();
        update.set("metadata.%s".formatted(METADATA_TRANSCRIPT_FIELD), model.getTranscript());

        // Perform the update operation using MongoTemplate.
        UpdateResult result = this.mongoTemplate.updateFirst(query, update, GridFSFile.class);

        // Check if the update was successful, if you need to handle it.
        boolean success = result.wasAcknowledged() && result.getMatchedCount() > 0;
        return success ? model : null;
    }

    @Override
    public void delete(String id) {
        gridFsTemplate.delete(this.createQueryById(id));
    }

    private Query createQueryById(String id) {
        return new Query(Criteria.where("_id").is(id));
    }

    private TranscribedAudio convertGridFSFileToModel(GridFSFile gridFsFile) {
        TranscribedAudio model = null;
        if (nonNull(gridFsFile)) {
            model = new TranscribedAudio();
            model.setId(gridFsFile.getId().asObjectId().getValue().toString());
            model.setName(gridFsFile.getFilename());
            if (nonNull(gridFsFile.getMetadata())) {
                model.setTranscript(gridFsFile.getMetadata().getString(METADATA_TRANSCRIPT_FIELD));
            }
            GridFsResource gridFsResource = this.gridFsOps.getResource(gridFsFile);
            model.setContent(gridFsResource.getContent());
        }
        return model;
    }
}