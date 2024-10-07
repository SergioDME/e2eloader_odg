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
    /*@SerializedName("dependency")
    public AtomicObject dependency;
    */
    public EdgeCookie(MyNode from, MyNode to, String cookie_name, AtomicObject atomicObject){
        super(from,to,"cookie",atomicObject);
        this.name=cookie_name;
        //this.dependency=atomicObject;
    }

    public EdgeCookie(MyNode from, MyNode to, String cookie_name, AtomicObject atomicObject,int index_from,int index_to){
        super(from,to,"cookie",atomicObject);
        this.name=cookie_name;
        this.from_index=index_from;
        this.to_index=index_to;
        //this.dependency=atomicObject;
    }

    @Override
    public String toString(){
        return "COOKIE DEPENDENCY ["+name+"] TO:"+to+" <- FROM"+from+"atomicObject:"+dependency;
    }
}
