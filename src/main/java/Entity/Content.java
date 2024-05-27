package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Content implements Serializable {
    public Double getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Integer getCompression() {
        return compression;
    }

    public String getText() {
        return text;
    }

    @SerializedName("size")
    Double size;
    @SerializedName("mimeType")
    String mimeType;
    @SerializedName("compression")
    Integer compression;
    @SerializedName("text")
    String text;
}
