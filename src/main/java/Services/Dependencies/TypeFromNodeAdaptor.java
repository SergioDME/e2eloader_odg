package Services.Dependencies;

import Entity.CSVNode;
import Entity.Node;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeFromNodeAdaptor implements JsonDeserializer<Node> {

    @Override
    public Node deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.has("ignorefirstLine")){
            return  jsonDeserializationContext.deserialize(jsonElement, CSVNode.class);
        }else{
            return  deserializeNode(jsonObject);
        }
    }

    private Node deserializeNode(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, Node.class);
    }
}
