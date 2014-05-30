package nilzor.ottovolley.okhttpextensions;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by fronilse on 30.05.2014.
 */
public class OkHttpGsonRequest<T>  {
    private String _url;
    private Class _clazz;

    public OkHttpGsonRequest(String url, Class clazz) {
        _url = url;
        _clazz = clazz;
    }

    public void call(OkHttpGsonCallback<T> callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(_url)
                .build();

        client.newCall(request).enqueue(new HttpCallback(callback, request));
    }

    class HttpCallback implements Callback {
        private OkHttpGsonCallback<T> _callback;
        private Request _request;

        public HttpCallback(OkHttpGsonCallback<T> callback, Request request) {
            _callback = callback;
            _request = request;
        }
        @Override
        public void onFailure(Request request, Throwable throwable) {
            OkHttpGsonResponse res = new OkHttpGsonResponse();
            res.Request = _request;
            res.Error = throwable;
            _callback.onResponse(res);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            String s = response.body().string();
            T payload = (T) new Gson().fromJson(s, _clazz);
            OkHttpGsonResponse res = new OkHttpGsonResponse();
            res.Request = _request;
            res.Payload = payload;
            res.Response = response;
            _callback.onResponse(res);
        }
    }
}
