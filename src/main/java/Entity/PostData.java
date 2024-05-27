package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostData implements Serializable {

    @SerializedName("mimeType")
    String mimeType;
    @SerializedName("text")
    String text;

    public String getMimeType() {
        return mimeType;
    }

    public String getText() {
        return text;
    }

    public Param[] getParams() {
        return params;
    }

    @SerializedName("params")
    Param [] params;
}
