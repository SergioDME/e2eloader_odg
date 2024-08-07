package Services.ResponseAnalyzer;


import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static Services.ResponseAnalyzer.JsonSchemaGenerator.generateJSONSchema;

public class StructuredObject implements Serializable {

    public StructuredObject(String name, String value)  {
        this.name = name;
        this.value = value;
        this.objects = new ArrayList<>();
       // if (value.startsWith("{")) {
            try {
              this.schemaString=generateJSONSchema(value);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        //}
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public void setSchemaString(String schemaString) {
        this.schemaString = schemaString;
    }

    @SerializedName("name")
    public String name;
    @SerializedName("value")
    public String value;
    @SerializedName("objects")
    public List<Object> objects;

    public String getSchemaString() {
        return schemaString;
    }

    String schemaString;

    public String toString(){
        return "[STRUCTURED] name: "+name+" value:"+value+" size:"+objects.size()+"\n [JSON_SCHEMA]: "+getSchemaString();
    }



}
