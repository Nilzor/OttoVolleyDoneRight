package nilzor.ottovolley.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import nilzor.ottovolley.R;
import nilzor.ottovolley.ServiceLocator;
import nilzor.ottovolley.core.OttoGsonRequest;
import nilzor.ottovolley.entities.HttpBinGetResponse;
import nilzor.ottovolley.messages.VolleyRequestSuccess;
import nilzor.ottovolley.viewmodels.VolleyRequestActivityViewModel;

public class VolleyRequestActivity extends Activity {
    //private final String Url = "http://httpbin.org/get";
    private final String Url = "http://httpbin.org/delay/1";
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
        if(_model.listenForResponse) {
            ServiceLocator.ResponseBuffer.startSaving();
            ServiceLocator.EventBus.unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OVDR", "onResume()");
        if (_model.listenForResponse) {
            ServiceLocator.EventBus.register(this);
            ServiceLocator.ResponseBuffer.stopAndProcess();
        }
        bindUi();
        // Add change listener to the switch. Must be done after binding, since this might trigger unregister
        ((Switch)findViewById(R.id.eventListenSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onListenForResponseChanged(isChecked);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("Model", _model);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("OVDR", "onRestoreInstanceState()");
        _model = (VolleyRequestActivityViewModel) savedInstanceState.getSerializable("Model");
    }

    private void bindUi() {
        ((TextView) findViewById(R.id.statusText)).setText(_model.status);
        ((TextView) findViewById(R.id.prevResultText)).setText(_model.prevResult);
        ((Switch) findViewById(R.id.eventListenSwitch)).setChecked(_model.listenForResponse);
    }

    public void onPerformHttpClicked(final View view) {
        OttoGsonRequest<HttpBinGetResponse> request = new OttoGsonRequest<HttpBinGetResponse>(ServiceLocator.EventBus, Url, HttpBinGetResponse.class);
        Log.d("OVDR", "Request begin: " + request.requestId);
        ServiceLocator.VolleyRequestQueue.add(request);
        updateUiForRequestSent(request);
    }

    public void onListenForResponseChanged(boolean isChecked) {
        if (isChecked != _model.listenForResponse){
            _model.listenForResponse = isChecked;
            registerServiceBus(_model.listenForResponse);
            Log.d("OVDR", "Listen for response: " + _model.listenForResponse);
        }
    }

    private void registerServiceBus(boolean register) {
        if (register) {
            ServiceLocator.EventBus.register(this);
            ServiceLocator.ResponseBuffer.stopAndProcess();
        }
        else {
            ServiceLocator.ResponseBuffer.startSaving();
            ServiceLocator.EventBus.unregister(this);
        }
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
