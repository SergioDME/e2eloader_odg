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

    public AtomicObject getAtomicObject() {
        return atomicObject;
    }

    public void setAtomicObject(AtomicObject atomicObject) {
        this.atomicObject = atomicObject;
    }

    public StructuredObject getStructuredObject() {
        return structuredObject;
    }

    public void setStructuredObject(StructuredObject structuredObject) {
        this.structuredObject = structuredObject;
    }

    @SerializedName("name")
    public String name;
    @SerializedName("atomicObject")
    public AtomicObject atomicObject=null;
    @SerializedName("structuredObject")
    public StructuredObject structuredObject=null;

    public  EdgeBodyJSON(boolean primitive, String name, MyNode from, MyNode to, AtomicObject atomicObject, StructuredObject s){
        super(from,to,"bodyjson",null);
        this.primitive= primitive;
        this.name=name;
        if(primitive){
            this.atomicObject=atomicObject;
        }else {
            this.structuredObject=s;
        }
    }

    @Override
    public String toString(){
        if(primitive)
            return "BODYJS AT DEPENDENCY ["+name+"] TO:"+to+" <- FROM:"+from+"atomicObject:"+atomicObject;
        else
            return  "BODYJS ST DEPENDENCY ["+name+"] TO:"+to+" <- FROM:"+from+" structuredObject:"+structuredObject;
    }

}
