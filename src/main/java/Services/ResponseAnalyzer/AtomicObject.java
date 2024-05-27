package Services.ResponseAnalyzer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AtomicObject implements Serializable {


    public AtomicObject (String value, String name, String xpath){
        this.value=value;
        this.name=name;
        this.xpath=xpath;
    }

    public AtomicObject(String value, String name, String xpath, boolean from_set_cookie){
        this.value=value;
        this.name=name;
        this.xpath=xpath;
        this.from_set_cookie=from_set_cookie;
    }
    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getXpath() {
        return xpath;
    }

    public boolean getFromSetCookie(){return this.from_set_cookie;}

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public void setFrom_set_cookie(boolean from_set_cookie) {
        this.from_set_cookie = from_set_cookie;
    }

    @SerializedName("value")
    public  String value;
    @SerializedName("name")
    public  String name;
    @SerializedName("xpath")
    public String xpath;
    @SerializedName("from_set_cookie")
    public boolean from_set_cookie =false;

    @Override
    public String toString(){
        if(!from_set_cookie)
            return "[ATOMIC] name: "+name+" value:"+value+" xpath:"+xpath;
        else
            return "[SET COOKIE] name:"+name+" value:"+value;
    }
}
