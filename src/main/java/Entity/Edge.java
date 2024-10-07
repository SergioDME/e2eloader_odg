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
    @SerializedName("from_index")
    public int from_index=-1;
    @SerializedName("to_index")
    public int to_index=-1;

    public int getTo_index() {
        return to_index;
    }

    public void setTo_index(int to_index) {
        this.to_index = to_index;
    }



    public int getFrom_index() {
        return from_index;
    }

    public void setFrom_index(int from_index) {
        this.from_index = from_index;
    }



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
