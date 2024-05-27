package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QueryParam implements Serializable {

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @SerializedName("name")
    String name;
    @SerializedName("value")
    String value;
}
