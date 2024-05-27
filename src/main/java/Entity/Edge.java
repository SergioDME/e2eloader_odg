package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Edge implements Serializable {

    @SerializedName("from")
    public Node from;
    @SerializedName("to")
    public Node to;
    @SerializedName("type")
    public String type;


    public Edge (Node from,Node to,String type){
        this.from=from;
        this.to=to;
        this.type=type;
    }

    @Override
    public String toString(){
        return "\n DEPENDENCY FROM:"+from+" TO:"+to+"\n";
    }
}
