package aadharapp.cloud.csc.aadharapp.Connection;

import android.app.Application;
import android.content.Context;

/**
 * Created by PAVAS NAVANEY on 29-05-2017.
 */
public class MyApplication extends Application{

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    public void setConnectivityListener(ConnectivityListener.ConnectivityReceiverListener listener) {
        ConnectivityListener.connectivityReceiverListener = listener;
    }

}
