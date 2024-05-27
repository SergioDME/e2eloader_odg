package Services.Dependencies;

import Entity.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeEdgeAdaptor implements JsonDeserializer<Edge> {
    @Override
    public Edge deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String type_edge = jsonObject.get("type").getAsString();
        switch (type_edge){
            case "bodyjson":
                return jsonDeserializationContext.deserialize(jsonElement, EdgeBodyJSON.class);
            case "bodyue":
                return jsonDeserializationContext.deserialize(jsonElement, EdgeBodyUE.class);
            case "cookie":
                return jsonDeserializationContext.deserialize(jsonElement, EdgeCookie.class);
            case "header":
                return jsonDeserializationContext.deserialize(jsonElement, EdgeHeader.class);
            case "queryparam":
                return jsonDeserializationContext.deserialize(jsonElement, EdgeQueryParam.class);
            case "url":
                return jsonDeserializationContext.deserialize(jsonElement,EdgeUrl.class);
            default:
                throw new JsonParseException("Unknown type: " + type);
        }
    }
}
