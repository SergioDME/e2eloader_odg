package View.AddManuallyCorrelationPage;

import java.util.ArrayList;

public class UsePreviouseResponseModel {

    public ArrayList<SingleItem> getUsePreviousResponseArray() {
        return usePreviousResponseArray;
    }

    private ArrayList<SingleItem> usePreviousResponseArray;

    public UsePreviouseResponseModel(){
        this.usePreviousResponseArray = new ArrayList<>();
    }

    public static class SingleItem{
        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public int getNumberOfRequestUsed() {
            return numberOfRequestUsed;
        }

        public void setNumberOfRequestUsed(int numberOfRequestUsed) {
            this.numberOfRequestUsed = numberOfRequestUsed;
        }

        private boolean used;
        private int numberOfRequestUsed;
        private String urlRequestUsed;
        public SingleItem(boolean u, int n,String urlRequestUsed){
            this.used=u;
            this.numberOfRequestUsed=n;
            this.urlRequestUsed=urlRequestUsed;

        }
    }
}
