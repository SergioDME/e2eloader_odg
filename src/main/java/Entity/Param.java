package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Param implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @SerializedName("name")
    String name;
    @SerializedName("value")
    String value;
}
