package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

public class EdgeUrl  extends Edge{
    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public AtomicObject getDependency() {
        return dependency;
    }

    public void setDependency(AtomicObject dependency) {
        this.dependency = dependency;
    }

    @SerializedName("subPath")
    public String subPath;

    public EdgeUrl(MyNode from, MyNode to, String subPath, AtomicObject atomicObject) {
        super(from,to,"url",atomicObject);
        this.subPath=subPath;
        //this.dependency=atomicObject;
    }

    public EdgeUrl(MyNode from, MyNode to, String subPath, AtomicObject atomicObject,int from_index, int to_index) {
        super(from,to,"url",atomicObject);
        this.subPath=subPath;
        this.from_index=from_index;
        this.to_index=to_index;
        //this.dependency=atomicObject;
    }
    @Override
    public String toString(){
        return "URL DEPENDENCY ["+subPath+"]  TO:"+to+" <- FROM:"+from+"atomicObject:"+dependency;
    }
}
