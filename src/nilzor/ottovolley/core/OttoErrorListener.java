package nilzor.ottovolley.core;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.otto.Bus;
import nilzor.ottovolley.messages.VolleyRequestFailed;


public class OttoErrorListener implements Response.ErrorListener {
    public int RequestId;
    Bus _eventBus;

    public OttoErrorListener(Bus eventBus, int requestId) {
        RequestId = requestId;
        _eventBus = eventBus;
    }

    public void onErrorResponse(VolleyError error) {
        VolleyRequestFailed message = new VolleyRequestFailed(RequestId, error);
        _eventBus.post(message);
    }
}