package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Response  implements Serializable {

    public Integer getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public Content getContent() {
        return content;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public Double getHeaderSize() {
        return headerSize;
    }

    public Double getBodySize() {
        return bodySize;
    }

    public Integer getTransferSize() {
        return transferSize;
    }

    @SerializedName("status")
    Integer status;
    @SerializedName("statusText")
    String statusText;
    @SerializedName("httpVersion")
    String httpVersion;
    @SerializedName("headers")
    Header [] headers;
    @SerializedName("cookies")
    Cookie [] cookies;
    @SerializedName("content")
    Content content;
    @SerializedName("redirectURL")
    String redirectURL;
    @SerializedName("headersSize")
    Double headerSize;
    @SerializedName("bodySize")
    Double bodySize;
    @SerializedName("_transferSize")
    Integer transferSize;

}
