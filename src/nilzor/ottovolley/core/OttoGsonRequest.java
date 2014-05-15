package nilzor.ottovolley.core;

import com.squareup.otto.Bus;
import nilzor.ottovolley.volleyextensions.GsonRequest;

/** GsonRequest which passes the result on to an Otto Message Bus */
public class OttoGsonRequest<T> extends GsonRequest<T> {
    private static int _idCounter = 1;

    /** A session-unique ID for this request. Reset when app process is killed. */
    public int requestId;

    public OttoGsonRequest(Bus eventBus, String url, Class<T> classType) {
        super(url,
                classType,
                new OttoSuccessListener<T>(eventBus, _idCounter),
                new OttoErrorListener(eventBus, _idCounter));
        requestId = _idCounter;
        _idCounter++;
    }
}
