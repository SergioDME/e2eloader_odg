package Services.CorrelationsView.ScriptGeneration;
import Properties.Paths;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Converter {

    public static void runMain (String filename, String filehar,String path) throws IOException, ParseException, InterruptedException, ParserConfigurationException, TransformerException, SAXException {
        Response uploadResponse = UploadRequest(filehar,path+"/hars");
        String bodyresponse = uploadResponse.body().string();
        Response converResponse = convertRequest(bodyresponse);
        String convertResponse = converResponse.body().string();
        Response statusResponse = statusRequest(convertResponse);
        String statusR = statusResponse.body().string();
        while(!getStatus(statusR).equals("FINISHED")){
            TimeUnit.SECONDS.sleep(5);
            statusResponse = statusRequest(convertResponse);
            statusR = getStatus(statusResponse.body().string());
        }
        String downResponse = downloadRequest(getoUrl(statusR));
        saveJMXFile(downResponse,filename);
    }

    public static void saveJMXFile(String jmx,String filename) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        /*// Parse the given input
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(jmx)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result =  new StreamResult(new File("icst-e2e-loader\\"+"src\\SINGLE_SCRIPTS_BEHAVIOR\\"+getNamefileHar(filename)+".jmx"));
        transformer.transform(source, result);
    */
        System.out.println(Paths.scripts_saved_path+"/"+getNamefileHar(filename)+".jmx");
        try(FileWriter fw = new FileWriter(Paths.scripts_saved_path+"/"+getNamefileHar(filename)+".jmx")) {
            fw.write(jmx);
        }catch (IOException e){
            //System.out.println("eccezzione catturata: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static Response UploadRequest(String filename, String path) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",filename,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(path+"/"+filename)))
                .build();
        Request request = new Request.Builder()
                .url("https://converter.blazemeter.com/api/converter/v1/upload")
                .method("POST", body)
                .addHeader("authority", "converter.blazemeter.com")
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("origin", "https://converter.blazemeter.com")
                .addHeader("referer", "https://converter.blazemeter.com/")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response convertRequest (String res) throws ParseException, IOException {
        String token = getToken(res);
        OkHttpClient client = new OkHttpClient().newBuilder()

                .build();

        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder()

                .url("https://converter.blazemeter.com/api/converter/v1/"+token+"/convert")

                .method("POST", body)

                .addHeader("authority", "converter.blazemeter.com")

                .addHeader("accept", "application/json, text/plain, */*")

                .addHeader("accept-language", "it,it-IT;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")

                .addHeader("content-length", "0")

                .addHeader("origin", "https://converter.blazemeter.com")

                .addHeader("referer", "https://converter.blazemeter.com/")

                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Microsoft Edge\";v=\"102\"")

                .addHeader("sec-ch-ua-mobile", "?0")

                .addHeader("sec-ch-ua-platform", "\"Windows\"")

                .addHeader("sec-fetch-dest", "empty")

                .addHeader("sec-fetch-mode", "cors")

                .addHeader("sec-fetch-site", "same-origin")

                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36 Edg/102.0.1245.39")

                .build();

                Response response =  client.newCall(request).execute();
                return response;
    }

    public static String downloadRequest(String url) throws ParseException, IOException {
        String bodyString="";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET",  null)
                .build();
        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            ResponseBody responsebody = response.body();
            if(responsebody != null){
                bodyString = responsebody.string();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return bodyString;
    }

    public static Response statusRequest(String res) throws ParseException, IOException {
        String token = getToken(res);
        OkHttpClient client = new OkHttpClient().newBuilder()

                .build();

        MediaType mediaType = MediaType.parse("text/plain");

        //RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder()

                .url("https://converter.blazemeter.com/api/converter/v1/"+token+"/status")

                .method("GET", null)

                .addHeader("authority", "converter.blazemeter.com")

                .addHeader("accept", "application/json, text/plain, */*")

                .addHeader("accept-language", "it,it-IT;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")

                .addHeader("referer", "https://converter.blazemeter.com/")

                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Microsoft Edge\";v=\"102\"")

                .addHeader("sec-ch-ua-mobile", "?0")

                .addHeader("sec-ch-ua-platform", "\"Windows\"")

                .addHeader("sec-fetch-dest", "empty")

                .addHeader("sec-fetch-mode", "cors")

                .addHeader("sec-fetch-site", "same-origin")

                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36 Edg/102.0.1245.39")

                .build();

        Response response = client.newCall(request).execute();
        return response;
    }
    public static String getToken(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        return json.get("publicToken").toString();
    }

    public static String getNamefileHar(String filename) {
        return filename.split("\\.")[0];
    }

    public static String getStatus(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject data = (JSONObject) json.get("data");
        String status = (String)data.get("status");
        return status;
    }

    public static String getoUrl(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject data = (JSONObject) json.get("data");
        String ourl = (String)data.get("oUrl");
        return ourl;
    }

}
