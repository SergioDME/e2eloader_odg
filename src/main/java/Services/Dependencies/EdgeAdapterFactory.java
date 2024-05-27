package Services.Dependencies;

import Entity.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EdgeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Edge.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        TypeAdapter<Edge> defaultAdapter = (TypeAdapter<Edge>) gson.getDelegateAdapter(this, TypeToken.get(Edge.class));
        return (TypeAdapter<T>) new EdgeAdapter(gson,defaultAdapter);
    }

    private static class EdgeAdapter extends  TypeAdapter<Edge>{
        private final TypeAdapter<Edge> defaultAdapter;
        private final Gson gson;
        EdgeAdapter(Gson gson, TypeAdapter<Edge> defaultAdapter){
            this.gson=gson;
            this.defaultAdapter = defaultAdapter;
        }
        @Override
        public void write(JsonWriter out, Edge value) throws IOException {
            defaultAdapter.write(out, value);
        }
        @Override
        public Edge read(JsonReader in) throws IOException{
            JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
            String type_edge = jsonObject.get("type").getAsString();
            switch (type_edge){
                case "bodyjson":
                    return gson.fromJson(jsonObject, EdgeBodyJSON.class);
                case "bodyue":
                    return gson.fromJson(jsonObject, EdgeBodyUE.class);
                case "cookie":
                    return gson.fromJson(jsonObject, EdgeCookie.class);
                case "header":
                    return gson.fromJson(jsonObject,  EdgeHeader.class);
                case "queryparam":
                    return gson.fromJson(jsonObject, EdgeQueryParam.class);
                case "url":
                    return gson.fromJson(jsonObject, EdgeUrl.class);
                default:
                    return defaultAdapter.fromJsonTree(jsonObject);
            }
        }
    }

}
