package Entity;

public class CheckableItem {


    public Edge getEdge() {
        return edge;
    }

    Edge edge;

    public int getNumReqWS() {
        return numReqWS;
    }

    public void setNumReqWS(int numReqWS) {
        this.numReqWS = numReqWS;
    }

    private int numReqWS;

    public String getDataRequestWS() {
        return dataRequestWS;
    }

    private String dataRequestWS;

    private boolean selected;
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public CheckableItem(Edge edge, boolean selected) {
        this.edge =edge;
        this.selected = selected;
    }

    public CheckableItem(Edge edge,int numReq, String data, boolean selected) {
        this.edge =edge;
        this.selected = selected;
        this.numReqWS= numReq;
        this.dataRequestWS=data;
    }


}