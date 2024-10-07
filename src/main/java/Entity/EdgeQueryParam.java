package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import com.google.gson.annotations.SerializedName;

public class EdgeQueryParam extends  Edge{
    public String getQuery_param_name() {
        return query_param_name;
    }

    public void setQuery_param_name(String query_param_name) {
        this.query_param_name = query_param_name;
    }

    public AtomicObject getDependency() {
        return dependency;
    }

    public void setDependency(AtomicObject dependency) {
        this.dependency = dependency;
    }

    @SerializedName("query_param_name")
    public String query_param_name;
    /*@SerializedName("dependency")
    public AtomicObject dependency;
    */
    public EdgeQueryParam(MyNode from, MyNode to, String query_param_name, AtomicObject atomicObject){
        super(from,to,"queryparam",atomicObject);
        this.query_param_name=query_param_name;
        //this.dependency=atomicObject;
    }

    public EdgeQueryParam(MyNode from, MyNode to, String query_param_name, AtomicObject atomicObject,int index_from ,int index_to){
        super(from,to,"queryparam",atomicObject);
        this.query_param_name=query_param_name;
        this.from_index=index_from;
        this.to_index=index_to;
        //this.dependency=atomicObject;
    }


    @Override
    public String toString(){
        return "QUERYPARAM DEPENDENCY ["+query_param_name+"]  TO:"+to+" <- FROM:"+from+"atomicObject:"+dependency;
    }
}