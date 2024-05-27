package Entity;


public class UltimateThreadGroup {
    public String getThreadsCount() {
        return ThreadsCount;
    }

    public void setThreadsCount(String threadsCount) {
        ThreadsCount = threadsCount;
    }

    public String getInitialDelay() {
        return InitialDelay;
    }

    public void setInitialDelay(String initialDelay) {
        InitialDelay = initialDelay;
    }

    public String getStartupTime() {
        return StartupTime;
    }

    public void setStartupTime(String startupTime) {
        StartupTime = startupTime;
    }

    public String getHoldLoadFor() {
        return HoldLoadFor;
    }

    public void setHoldLoadFor(String holdLoadFor) {
        HoldLoadFor = holdLoadFor;
    }

    public String getShutDownTime() {
        return ShutDownTime;
    }

    public void setShutDownTime(String shutDownTime) {
        ShutDownTime = shutDownTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getAdd() {
        return add;
    }
    public String getDelete() {
        return  delete;
    }

    private String ThreadsCount;
    private String InitialDelay;
    private String StartupTime;
    private String HoldLoadFor;
    private String ShutDownTime;
    private String filename;
    private final String add = "+";
    private final String delete ="-";

    public UltimateThreadGroup(String tc, String id, String st, String hf, String sdt, String f) {
        this.ThreadsCount=tc;
        this.InitialDelay=id;
        this.StartupTime =st;
        this.HoldLoadFor=hf;
        this.ShutDownTime =sdt;
        this.filename=f;
    }
    @Override
    public String toString() {
        return this.filename+" "+" "+this.ThreadsCount+" "+this.InitialDelay+" "+this.StartupTime+" "+this.HoldLoadFor+" "+this.ShutDownTime;

    }

}
