package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Entry  implements Serializable {

    public Date getStartedDateTime() {
        return startedDateTime;
    }

    public Double getTime() {
        return time;
    }

    public Timing getTimings() {
        return timings;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }

    public Cache getCache() {
        return cache;
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public String get_priority() {
        return _priority;
    }

    public String get_resourceType() {
        return _resourceType;
    }

    public WebSocketMessage[] getWebSocketMessages() {
        return webSocketMessages;
    }

    public Integer getConnection() {
        return connection;
    }

    @SerializedName("startedDateTime")
    Date startedDateTime;
    @SerializedName("time")
    Double time;

    @SerializedName("timings")
    Timing timings;

    @SerializedName("request")
    Request request;

    @SerializedName("response")
    Response response;

    @SerializedName("cache")
    Cache cache;

    @SerializedName("serverIPAddress")
    String serverIPAddress;
    @SerializedName("_priority")
    String _priority;
    @SerializedName("_resourceType")
    String _resourceType;

    @SerializedName("_webSocketMessages")
    WebSocketMessage [] webSocketMessages;

    @SerializedName("connection")
    Integer connection;
}

