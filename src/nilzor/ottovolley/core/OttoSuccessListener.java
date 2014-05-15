package nilzor.ottovolley.core;

import com.android.volley.Response;
import com.squareup.otto.Bus;
import nilzor.ottovolley.messages.VolleyRequestSuccess;

public class OttoSuccessListener<T> implements Response.Listener<T>  {
    public int requestId;
    private Bus _eventBus;
    public OttoSuccessListener(Bus eventBus, int requestId) {
        this.requestId = requestId;
        _eventBus = eventBus;
    }

    public void onResponse(T response) {
        VolleyRequestSuccess<T> message = new VolleyRequestSuccess<T>(requestId, response);
        _eventBus.post(message);
    }
}