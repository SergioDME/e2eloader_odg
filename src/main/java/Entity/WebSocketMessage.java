package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WebSocketMessage implements Serializable {


    public String getData() {
        return data;
    }

    public Double getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public Integer getOpcode() {
        return opcode;
    }

    public Boolean getMask() {
        return mask;
    }

    @SerializedName("data")
    String data;
    @SerializedName("time")
    Double time;
    @SerializedName("type")
    String type;
    @SerializedName("opcode")
    Integer opcode;
    @SerializedName("mask")
    Boolean mask;
}
