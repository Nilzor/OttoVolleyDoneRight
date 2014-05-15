package nilzor.ottovolley.messages;

import com.android.volley.VolleyError;

public class VolleyRequestFailed {
    public int requestId;
    public VolleyError error;

    public VolleyRequestFailed(int requestId, VolleyError error) {
        this.requestId = requestId;
        this.error = error;
    }
}
