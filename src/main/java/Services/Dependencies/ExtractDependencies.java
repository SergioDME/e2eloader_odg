package Services.Dependencies;

import Entity.DependencyGraph;
import Entity.Har;
import Entity.Node;
import Services.ResponseAnalyzer.ResponseAnalyzer;
import Services.ResponseAnalyzer.ResponseUnstructured;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ExtractDependencies {
    File file_har;
    String har_content;

    public Har getHar() {
        return har;
    }

    Har har;

    public File getFile_har() {
        return file_har;
    }

    public List<ResponseUnstructured> getResponseUnstructuredList() {
        return responseUnstructuredList;
    }

    List<ResponseUnstructured> responseUnstructuredList;
    public ExtractDependencies(File har_file) throws IOException {
        this.file_har = har_file;
        this.har_content= FileUtils.readFileToString(har_file, UTF_8);
        this.responseUnstructuredList = new ArrayList<>();
        Gson gson  = new Gson();
        this.har = gson.fromJson(har_content,Har.class);
    }

    public void analyze_responses(){
        ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer();
        for(int i=0; i<har.getLog().getEntries().length;i++){
            if("application/json".equals(har.getLog().getEntries()[i].getResponse().getContent().getMimeType())){
                this.responseUnstructuredList.add(
                        responseAnalyzer.getUnstructuredResponse(
                                har.getLog().getEntries()[i].getResponse().getContent().getText()
                        )
                );
            }else{
                this.responseUnstructuredList.add(
                        new ResponseUnstructured()
                );
            }
            responseAnalyzer.analyzeResponseHeader(har.getLog().getEntries()[i].getResponse().getHeaders(),this.responseUnstructuredList.get(i));
        }
    }

    public DependencyGraph build_dependencies_graph() throws IOException {

        DependencyGraph dependencyGraph = new DependencyGraph();
        dependencyGraph.addRequestoToGraph(this.har.getLog().getEntries()[0].getRequest(),0);
        for(int i=1; i<this.har.getLog().getEntries().length;i++){
            Node to = dependencyGraph.addRequestoToGraph(this.har.getLog().getEntries()[i].getRequest(),i);
            if(to != null){ // if to is not equals null, it's the first time we meet this request so we've to analyze it
                UrlDependency.check_url_dependencies(this.responseUnstructuredList,i,dependencyGraph,to);
                HeaderDependency.check_header_dependency(this.responseUnstructuredList,i,dependencyGraph,to);
                QueryParameterDependency.check_queryParams_dependency(this.responseUnstructuredList,i,dependencyGraph,to);
                if(to.getRequest().getPostData() != null &&(to.getRequest().getMethod().equals("POST")|| to.getRequest().getMethod().equals("PUT"))){
                    BodyDependency.check_body_dependency(this.responseUnstructuredList,i,dependencyGraph,to);
                }
                CookieDependency.check_cookie_dependency(this.responseUnstructuredList,i,dependencyGraph,to);
            }
        }
        return  dependencyGraph;
    }





}
