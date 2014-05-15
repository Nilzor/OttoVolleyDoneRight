package nilzor.ottovolley;


import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.otto.Bus;

public class ServiceLocator {
    public static Bus EventBus;
    public static RequestQueue VolleyRequestQueue;
    private static boolean _isInitialized;

    public static void ensureInitialized(Context context) {
        if (!_isInitialized) {
            _isInitialized = true;
            VolleyRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            EventBus = new Bus();
        }
    }
}
