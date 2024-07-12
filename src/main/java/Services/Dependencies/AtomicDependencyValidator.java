package Services.Dependencies;

import Entity.*;
import Services.ResponseAnalyzer.AtomicObject;
import Services.ResponseAnalyzer.StructuredObject;
import opennlp.tools.stemmer.PorterStemmer;

import java.net.URI;

public class AtomicDependencyValidator {

    private static final float lcs_deadline =0.45f;

    public String porter_stamming(String input)
    {
        PorterStemmer porterStemmer = new PorterStemmer();
        return porterStemmer.stem(input);
    }

    public String lower_case (String input){
        return input.toLowerCase();
    }

    public String id_complention(String child, String father){
        if(child.length()<=3){
            return  porter_stamming(father)+""+child;
        }
        return child;
    }
    public String [] id_complention(String child, Request request){
        String [] res;
        if(child.length()<=3)
        {
            res = new String[2];
            URI uri = URI.create(request.getUrl());
            String path = uri.getPath();
            String [] subpath = path.split("/");
            res[0] = porter_stamming(subpath[subpath.length-1])+""+child;
            res[1] = porter_stamming(subpath[subpath.length-2])+""+child;
        }else {
            res = new String[1];
            res[0] = child;
        }
        return res;
    }

    public boolean evaluate_atomic_dependencies (AtomicObject atomicObject, Object to_object, StructuredObject father,Request from){

        if(to_object.getClass() == Header.class){
         Header header = (Header) to_object;
         return  evaluate_header_atomic_dep(atomicObject, header,father,from);
        } else if( to_object.getClass() == QueryParam.class){
            QueryParam queryParam = (QueryParam) to_object;
            return general_atomic_comparioson(atomicObject,queryParam.getValue(),queryParam.getName(),father,from);
        } else if(to_object.getClass() == Param.class){
            Param param = (Param)  to_object;
            return general_atomic_comparioson(atomicObject, param.getValue(),param.getName(),father,from);
        }else if (to_object.getClass() == Cookie.class){
            Cookie cookie =(Cookie) to_object;
            return evaluate_cookie_atomic_dep(atomicObject,cookie,father,from);
         }
        return false;
    }


    private boolean evaluate_header_atomic_dep(AtomicObject atomicObject, Header header, StructuredObject father,Request request){
        //special case Authorization
        if(header.getName().equals("Authorization")) {
            String header_value = header.getValue().substring(7);
            if(atomicObject.getValue().equals(header_value)){
                //System.out.println(atomicObject);
                return true;
            }
            return false;
        }
        return general_atomic_comparioson(atomicObject,header.getValue(),header.getName(),father,request);
    }

    private  boolean evaluate_cookie_atomic_dep(AtomicObject atomicObject, Cookie cookie, StructuredObject father,Request request){
        //special case Authorization
        if(cookie.getName().contains("Authorization")){
            if(atomicObject.getValue().equals(cookie.getValue())){
                return true;
            }
            return false;
        }
        return general_atomic_comparioson(atomicObject,cookie.getValue(),cookie.getName(),father,request);
    }


    public boolean evaluate_url_atomic_dep(AtomicObject atomicObject, Request request, String sub_path, StructuredObject father,String possible_name){
        String to_lower_name = lower_case(possible_name);
        String atomic_name =  lower_case(atomicObject.getName());
        if(father!=null && !father.getName().equals("")){
            atomic_name = id_complention(atomic_name,porter_stamming(father.getName().toLowerCase()));
            String to_name_stamm = porter_stamming(to_lower_name);
            //String atomic_name_stamm = porter_stamming(atomic_name);
            //int dist =  LevenshteinDistance.dist(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray());
            float lcs = LCS.LCSubStr(to_name_stamm.toCharArray(),atomic_name.toCharArray(),to_name_stamm.length(),atomic_name.length());
           // if(dist <=5){
           if(lcs>=lcs_deadline){
                //System.out.println(atomicObject);
                return true;
            }
        }else{
            String possible_fatherchild_name [] = id_complention(atomic_name,request);
            for(int i=0;i<possible_fatherchild_name.length;i++){
                String to_name_stamm = porter_stamming(to_lower_name);
                String atomic_name_stamm = possible_fatherchild_name[i];
                //int dist =  LevenshteinDistance.dist(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray());
                //if(dist <=5){
                 float lcs = LCS.LCSubStr(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray(),to_name_stamm.length(),atomic_name_stamm.length());
                 if(lcs >=lcs_deadline){
                    //System.out.println(atomicObject);
                    return true;
                }
            }
        }
        return  false;
    }

    public boolean general_atomic_comparioson(AtomicObject atomicObject, String to_value, String to_name, StructuredObject father,Request request){
        //common case
        if(atomicObject.getValue().equals(to_value)){
            String to_lower_name = lower_case(to_name);
            String atomic_name = lower_case(atomicObject.getName());
            if(father != null && !father.getName().equals("")){
                atomic_name = id_complention(atomic_name,porter_stamming(father.getName().toLowerCase()));
                String to_name_stamm = porter_stamming(to_lower_name);
                //String atomic_name_stamm = porter_stamming(atomic_name);
                //int dist =  LevenshteinDistance.dist(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray());
                //if(dist <=5){
                float lcs = LCS.LCSubStr(to_name_stamm.toCharArray(),atomic_name.toCharArray(),to_name_stamm.length(),atomic_name.length());
                if(lcs >= lcs_deadline){
                    //System.out.println(atomicObject);
                    return true;
                }
            }else{
                String possible_fatherchild_name [] = id_complention(atomic_name,request);
                for(int i=0;i<possible_fatherchild_name.length;i++){
                    String to_name_stamm = porter_stamming(to_lower_name);
                    String atomic_name_stamm = possible_fatherchild_name[i];
                    //int dist =  LevenshteinDistance.dist(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray());
                    //if(dist <=5){
                    float lcs = LCS.LCSubStr(to_name_stamm.toCharArray(),atomic_name_stamm.toCharArray(),to_name_stamm.length(),atomic_name_stamm.length());
                    if(lcs >=lcs_deadline){
                        //System.out.println(atomicObject);
                        return true;
                    }
                }
            }
           return  false;
        }
        return false;
    }




}
