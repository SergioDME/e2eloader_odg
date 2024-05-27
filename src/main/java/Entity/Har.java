package Entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Har implements Serializable {

    public Log getLog() {
        return log;
    }

    @SerializedName("log")
    Log log;
}
