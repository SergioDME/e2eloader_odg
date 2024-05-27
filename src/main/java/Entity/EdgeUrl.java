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
    @SerializedName("dependency")
    public AtomicObject dependency;

    public EdgeUrl(Node from, Node to,String subPath, AtomicObject atomicObject) {
        super(from,to,"url");
        this.subPath=subPath;
        this.dependency=atomicObject;
    }

    @Override
    public String toString(){
        return "URL DEPENDENCY ["+subPath+"]  TO:"+to+" <- FROM:"+from+"atomicObject:"+dependency;
    }
}
