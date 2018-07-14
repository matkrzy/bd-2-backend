package com.photos.api.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.photos.api.models.Tag;
import com.photos.api.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

public class TagDeserializer extends JsonDeserializer<Tag> {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        String tagName = node.asText();
        Optional<Tag> tag = this.tagRepository.findByName(tagName);

        if (!tag.isPresent()) {
            return new Tag(tagName);
        }

        return tag.get();
    }
}
