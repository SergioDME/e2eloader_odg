package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Request implements Serializable {

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public QueryParam[] getQueryParams() {
        return queryParams;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public Double getHeaderSize() {
        return headerSize;
    }

    public Double getBodySize() {
        return bodySize;
    }

    @SerializedName("method")
    String method;

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    String url;
    @SerializedName("httpVersion")
    String httpVersion;

    @SerializedName("headers")
    Header [] headers;

    @SerializedName("queryString")
    QueryParam [] queryParams;

    @SerializedName("cookies")
    Cookie [] cookies;

    @SerializedName("headersSize")
    Double headerSize;
    @SerializedName("bodySize")
    Double bodySize;

    public PostData getPostData() {
        return postData;
    }

    @SerializedName("postData")
    PostData postData;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // If the same object, return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // If null or different classes, return false
        }
        Request otherRequest = (Request) obj; // Cast to Request
        // Compare  url and method
        if (!(this.url.equals(otherRequest.url) && this.method.equals(otherRequest.method)))
            return  false;
        if(this.method.equals("POST") || this.method.equals("PUT")){
            if(!this.getPostData().text.equals(otherRequest.getPostData().text))
                return false;
        }
        return true;
    }





}
