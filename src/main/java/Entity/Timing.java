package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Timing implements Serializable {

    public Double getBlocked() {
        return blocked;
    }

    public Integer getDns() {
        return dns;
    }

    public Integer getSsl() {
        return ssl;
    }

    public Integer getConnect() {
        return connect;
    }

    public Double getSend() {
        return send;
    }

    public Double getWait() {
        return wait;
    }

    public Double getReceive() {
        return receive;
    }

    @SerializedName("blocked")
    Double blocked;
    @SerializedName("dns")
    Integer dns;
    @SerializedName("ssl")

    Integer ssl;
    @SerializedName("connect")

    Integer connect;
    @SerializedName("send")

    Double send;
    @SerializedName("wait")

    Double wait;
    @SerializedName("receive")

    Double receive;

}
