package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Creator implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("version")
    String version;
    @SerializedName("comment")
    String comment;
}
