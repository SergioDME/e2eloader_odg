package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

public class EdgeBodyUE extends Edge{
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

    public EdgeBodyUE(MyNode from , MyNode to, String url_enc, AtomicObject atomicObject){
        super(from,to,"bodyue",atomicObject);
        this.name=url_enc;
        //this.dependency=atomicObject;
    }

    @Override
    public String toString(){
        return "BODYUE DEPENDENCY ["+name+"] TO:"+to+" <- FROM:"+from+"atomicObject:"+dependency;
    }

}
