package nilzor.ottovolley.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import nilzor.ottovolley.OttoGsonRequest;
import nilzor.ottovolley.R;
import nilzor.ottovolley.ServiceLocator;
import nilzor.ottovolley.entities.HttpBinGetResponse;
import nilzor.ottovolley.messages.VolleyRequestSuccess;
import nilzor.ottovolley.viewmodels.VolleyRequestActivityViewModel;

public class VolleyRequestActivity extends Activity {
//    private final String Url = "http://httpbin.org/get";
    private final String Url = "http://httpbin.org/delay/4";
    private VolleyRequestActivityViewModel _model;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OVDR", "onCreate()");
        setContentView(R.layout.main);
        ServiceLocator.ensureInitialized(this);
        _model = new VolleyRequestActivityViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OVDR", "onPause()");
        ServiceLocator.EventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OVDR", "onResume()");
        ServiceLocator.EventBus.register(this);
        bindUi();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("Model", _model);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _model = (VolleyRequestActivityViewModel) savedInstanceState.getSerializable("Model");
    }

    private void bindUi() {
        ((TextView) findViewById(R.id.statusText)).setText(_model.status);
        ((TextView) findViewById(R.id.prevResultText)).setText(_model.prevResult);

    }

    public void performHttpGetQuery(final View view) {
        OttoGsonRequest<HttpBinGetResponse> request = new OttoGsonRequest<HttpBinGetResponse>(ServiceLocator.EventBus, Url, HttpBinGetResponse.class);
        Log.d("OVDR", "Request begin: " + request.requestId);
        ServiceLocator.VolleyRequestQueue.add(request);
        updateUiForRequestSent(request);
    }

    @Subscribe
    public void onHttpResponseReceived(VolleyRequestSuccess<HttpBinGetResponse> message) {
        Log.d("OVDR", "Request end: " + message.requestId);
        updateUiForResponseReceived(message);
    }

    private void updateUiForRequestSent(OttoGsonRequest<HttpBinGetResponse> request) {
        _model.status = "Sent #" + request.requestId;
        bindUi();
    }

    private void updateUiForResponseReceived(VolleyRequestSuccess<HttpBinGetResponse> message) {
        _model.status = "Received #" + message.requestId;
        _model.prevResult = "#" + message.requestId + " -- " + message.response.headers.X_Request_Id;
        bindUi();
    }
}
