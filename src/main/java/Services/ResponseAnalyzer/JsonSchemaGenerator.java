package Services.ResponseAnalyzer;

import com.squareup.okhttp.*;

import java.io.IOException;

public class JsonSchemaGenerator {
    public static String generateJSONSchema(String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://localhost:4000/schema")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
