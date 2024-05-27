package Services.ResponseAnalyzer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseUnstructured implements Serializable {

    public ResponseUnstructured (){
        this.objects = new ArrayList<>();
    }
    public List<Object> getObjects() {
        return objects;
    }

    List<Object> objects;

}
