package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

public class EdgeCookie extends  Edge{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicObject getDependency() {
        return dependency;
    }

    public void setDependency(AtomicObject dependency) {
        this.dependency = dependency;
    }

    @SerializedName("name")
    public String name;
    @SerializedName("dependency")
    public AtomicObject dependency;

    public EdgeCookie(Node from, Node to, String cookie_name, AtomicObject atomicObject){
        super(from,to,"cookie");
        this.name=cookie_name;
        this.dependency=atomicObject;
    }

    @Override
    public String toString(){
        return "COOKIE DEPENDENCY ["+name+"] TO:"+to+" <- FROM"+from+"atomicObject:"+dependency;
    }
}