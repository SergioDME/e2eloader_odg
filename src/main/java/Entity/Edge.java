package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Edge implements Serializable {

    @SerializedName("from")
    public MyNode from;
    @SerializedName("to")
    public MyNode to;
    @SerializedName("dependency")
    public AtomicObject dependency;
    @SerializedName("type")
    public String type;


    public Edge (MyNode from, MyNode to, String type, AtomicObject dependency){
        this.from=from;
        this.to=to;
        this.type=type;
        this.dependency= dependency;
    }

    @Override
    public String toString(){
        return "\n DEPENDENCY FROM:"+from+" TO:"+to+"\n";
    }
}
