package com.local.gsalas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura Google client API
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    //Boton para layuot con WebView
    public void onClickWebView(View v){
        try {
            Intent intent = new Intent(MainActivity.this, WebViewClass.class);
            startActivity(intent);
        } catch (Exception e){}
    }

    //Boton para layout con conexion a GoogleAPI
    public void onClickServlet(View v){
        //Metodo para cerrar secciones abriertas previamente o predefinidas por el dispositivo
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Intent intent = new Intent(MainActivity.this, ServletTestClass.class );
                    startActivity(intent);
                }
            }
        });
        Intent intent = new Intent(MainActivity.this, ServletTestClass.class );
        startActivity(intent);
    }
}
