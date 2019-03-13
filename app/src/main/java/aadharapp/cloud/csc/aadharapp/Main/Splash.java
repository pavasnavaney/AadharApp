package aadharapp.cloud.csc.aadharapp.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import aadharapp.cloud.csc.aadharapp.Connection.ConnectivityListener;
import aadharapp.cloud.csc.aadharapp.Connection.MyApplication;
import aadharapp.cloud.csc.aadharapp.IntroManager.PrefManager;
import aadharapp.cloud.csc.aadharapp.R;

public class Splash extends AppCompatActivity implements ConnectivityListener.ConnectivityReceiverListener {

    private static int SPLASH_TIME_OUT = 6500;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_splash);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                    .coordinatorLayout);
            checkConnection();
            LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
            animationView.setAnimation("fingerprint.json");
            animationView.loop(false);
            animationView.setSpeed(0.2f);
            animationView.playAnimation();
        }

    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkConnection();
                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            snackbar.show();
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityListener.isConnected();
        showSnack(isConnected);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }
}
