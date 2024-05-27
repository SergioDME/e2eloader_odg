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
    @SerializedName("dependency")
    public AtomicObject dependency;

    public  EdgeHeader(Node from,Node to, String header_name, AtomicObject atomicObject){
        super(from,to,"header");
        this.header_name=header_name;
        this.dependency=atomicObject;
    }

    @Override
    public String toString(){
        return "HEADER DEPENDENCY ["+header_name+"] TO:"+to+" <- FROM"+from+"atomicObject:"+dependency;
    }
}
