package Services.HomeView.WorkloadGraph;

public class MyPoint {
    private float x;
    private float y;

    private int row_n=-1;
    private char state='I';

    public MyPoint (float x , float y ){ this.x = x; this.y=y;}
    public MyPoint (float x , float y,int row_n, char state ){ this.x = x; this.y=y; this.row_n=row_n; this.state=state;}
    public float getX(){return this.x;}
    public float getY(){return this.y;}

    public int getRow_n(){return  this.row_n;}
    public char getState(){return  this.state;}


    @Override
    public String toString(){
        return "["+this.getRow_n()+"] phase:"+this.getState()+" ("+this.getX()+"-"+this.getY()+")";
    }

}
