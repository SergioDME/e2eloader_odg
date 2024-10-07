package Entity;

import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.StructuredObject;
import com.google.gson.annotations.SerializedName;

public class EdgeBodyJSON  extends Edge{

    @SerializedName("primitive")
    boolean primitive;

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public AtomicObject getAtomicObject() {
        return atomicObject;
    }*/

    /*public void setAtomicObject(AtomicObject atomicObject) {
        this.atomicObject = atomicObject;
    }*/

    public StructuredObject getStructuredObject() {
        return structuredObject;
    }

    public void setStructuredObject(StructuredObject structuredObject) {
        this.structuredObject = structuredObject;
    }

    @SerializedName("name")
    public String name;
    //@SerializedName("atomicObject")
    //public AtomicObject atomicObject=null;
    @SerializedName("structuredObject")
    public StructuredObject structuredObject=null;

    public  EdgeBodyJSON(boolean primitive, String name, MyNode from, MyNode to, AtomicObject atomicObject, StructuredObject s){
        super(from,to,"bodyjson",atomicObject);
        this.primitive= primitive;
        this.name=name;
        if(!primitive){
            //super(from.to,"bodyjson",null),
            this.structuredObject=s;
        }
    }


    public  EdgeBodyJSON(boolean primitive, String name, MyNode from, MyNode to, AtomicObject atomicObject, StructuredObject s,int index_from ,int index_to){
        super(from,to,"bodyjson",atomicObject);
        this.primitive= primitive;
        this.name=name;
        if(!primitive){
            //super(from.to,"bodyjson",null),
            this.structuredObject=s;
        }
        this.from_index=index_from;
        this.to_index=index_to;
    }

    @Override
    public String toString(){
        if(primitive)
            return "BODYJS AT DEPENDENCY ["+name+"] TO:"+to+" <- FROM:"+from+"atomicObject:"+dependency;
        else
            return  "BODYJS ST DEPENDENCY ["+name+"] TO:"+to+" <- FROM:"+from+" structuredObject:"+structuredObject;
    }

}
