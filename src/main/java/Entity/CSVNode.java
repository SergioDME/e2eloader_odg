package Entity;

import com.google.gson.annotations.SerializedName;

public class CSVNode extends MyNode {

    public boolean isIgnorefirstLine() {
        return ignorefirstLine;
    }

    public void setIgnorefirstLine(boolean ignorefirstLine) {
        this.ignorefirstLine = ignorefirstLine;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getVariablesName() {
        return variablesName;
    }

    public void setVariablesName(String variablesName) {
        this.variablesName = variablesName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @SerializedName("ignorefirstLine")
    private Boolean ignorefirstLine ;
    @SerializedName("encoding")
    private String encoding ;
    @SerializedName("variablesName")
    private String variablesName ;
    @SerializedName("filename")
    private String filename;

    public CSVNode(boolean ignorefl, String enc, String varName, String filen) {
        super(null);
        ignorefirstLine=ignorefl;
        encoding=enc;
        variablesName=varName;
        filename=filen;
    }

    @Override
    public String toString() {
        return this.filename+" "+this.encoding+" "+variablesName+" "+ignorefirstLine;
    }


    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || o.getClass()!=CSVNode.class){
            return false;
        }

        CSVNode csvNode= (CSVNode) o;
        return this.variablesName.equals(((CSVNode) o).variablesName) && this.filename.equals(((CSVNode) o).filename);

    }
}
