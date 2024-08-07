package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyNode implements Serializable {


    public Request getRequest() {
        return request;
    }

    public List<Integer> getIndexs() {
        return indexs;
    }
    @SerializedName("request")
    public Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setIndexs(List<Integer> indexs) {
        this.indexs = indexs;
    }

    @SerializedName("indexs")
    public List<Integer> indexs;

    public MyNode(Request request){
        this.request=request;
        this.indexs  = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "["+request.method+"]"+request.url+" "+indexs;
    }

    @Override
    public boolean equals(Object obj ){
        if (this == obj) {
            return true; // If the same object, return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // If null or different classes, return false
        }
        MyNode otherNode = (MyNode) obj; // Cast to Request
        // Compare the request
       return this.request.equals(otherNode.request) && this.indexs.equals(otherNode.indexs);
    }

}
