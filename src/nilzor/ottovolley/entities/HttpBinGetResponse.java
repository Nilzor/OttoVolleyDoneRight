package nilzor.ottovolley.entities;

import com.google.gson.annotations.SerializedName;

public class HttpBinGetResponse {
    public String origin;
    public String url;
    public HeadersSubClass headers;

    public class HeadersSubClass {
        public String XRequestId;
        @SerializedName("X-Request-Id")
        public String X_Request_Id;
        public String Host;
    }
}
