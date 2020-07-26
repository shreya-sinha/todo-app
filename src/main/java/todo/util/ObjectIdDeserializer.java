package todo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {

    @Override
    public ObjectId deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        if (p.hasCurrentToken()) {
            String value = p.getValueAsString();
            return new ObjectId(value);
        } else {
            return null;
        }
    }
}