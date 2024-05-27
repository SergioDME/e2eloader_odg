package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Log implements Serializable {

    public String getVersion() {
        return version;
    }

    public String[] getPages() {
        return pages;
    }

    public Creator getCreator() {
        return creator;
    }

    public Entry[] getEntries() {
        return entries;
    }

    @SerializedName("version")
    String version;
    @SerializedName("pages")
    String [] pages;
    @SerializedName("creator")
    Creator creator;

    @SerializedName("entries")
    Entry [] entries;

}
