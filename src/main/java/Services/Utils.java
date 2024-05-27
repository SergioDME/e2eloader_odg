package Services;

import java.io.File;
import java.util.ArrayList;

public class Utils {
    public static ArrayList<String> getFilesByNameAndPath(String filename, String path) {

        ArrayList<String> result = new ArrayList<>();
        //int pos = filename.indexOf('-');
        //int pos = filename.indexOf('.');
        //String nameE2ETestCase = filename.substring(0,pos);
        File[] files = new File(path).listFiles();
        for(File file: files) {
//            if(file.getName().contains(nameE2ETestCase)) {
            if(file.getName().contains(filename)) {
                result.add(file.getName());
            }
        }
        return  result;
    }

}
