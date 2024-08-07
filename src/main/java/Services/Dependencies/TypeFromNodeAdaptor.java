package Services.Dependencies;

import Entity.CSVNode;
import Entity.MyNode;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TypeFromNodeAdaptor implements JsonDeserializer<MyNode> {

    @Override
    public MyNode deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.has("ignorefirstLine")){
            return  jsonDeserializationContext.deserialize(jsonElement, CSVNode.class);
        }else{
            return  deserializeNode(jsonObject);
        }
    }

    private MyNode deserializeNode(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, MyNode.class);
    }
}
