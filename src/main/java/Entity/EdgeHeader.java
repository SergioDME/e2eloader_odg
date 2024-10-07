package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

public class EdgeHeader extends Edge {
    public String getHeader_name() {
        return header_name;
    }

    public void setHeader_name(String header_name) {
        this.header_name = header_name;
    }

    public AtomicObject getDependency() {
        return dependency;
    }

    public void setDependency(AtomicObject dependency) {
        this.dependency = dependency;
    }

    @SerializedName("header_name")
    public String header_name;
    /*@SerializedName("dependency")
    public AtomicObject dependency;
    */

    public  EdgeHeader(MyNode from, MyNode to, String header_name, AtomicObject atomicObject){
        super(from,to,"header",atomicObject);
        this.header_name=header_name;
       // this.dependency=atomicObject;
    }

    public  EdgeHeader(MyNode from, MyNode to, String header_name, AtomicObject atomicObject,int index_from, int index_to){
        super(from,to,"header",atomicObject);
        this.header_name=header_name;
        this.from_index=index_from;
        this.to_index=index_to;
        // this.dependency=atomicObject;
    }
    @Override
    public String toString(){
        return "HEADER DEPENDENCY ["+header_name+"] TO:"+to+" <- FROM"+from+"atomicObject:"+dependency;
    }
}
