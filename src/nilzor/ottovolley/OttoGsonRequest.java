package nilzor.ottovolley;

import com.squareup.otto.Bus;
import nilzor.ottovolley.volleyextensions.GsonRequest;

public class OttoGsonRequest<T> extends GsonRequest<T> {
    private static int _idCounter = 1;
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
