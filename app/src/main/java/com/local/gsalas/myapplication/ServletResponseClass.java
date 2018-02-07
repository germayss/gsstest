package com.local.gsalas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class ServletResponseClass extends AppCompatActivity {

    private TextView txt;
    private TextView txt2;
    private GoogleApiClient googleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servlet_response);

        txt = findViewById(R.id.txtGG);
        txt2 = findViewById(R.id.txtGG2);

        //Configura Google client API
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    //Login silencioso y datos almacenados para solicitar informacion de usuario
    @Override
    protected void onStart() {
        super.onStart();
        //Login silencioso
        OptionalPendingResult<GoogleSignInResult> optionalPendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        //Verificar si hay una cuenta verificado previamente
        if(optionalPendingResult.isDone()){
            //obtiene datos del ususrio
            GoogleSignInResult googleSignInResult = optionalPendingResult.get();
            handleSignInResult(googleSignInResult);
        } else {
            //Elimina seccion no verificada
            optionalPendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult){
        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            //Envia datos de cuenta Google a Android
            txt.setText(googleSignInAccount.getDisplayName());
            txt2.setText(googleSignInAccount.getId());
        } else {
            //No obtiene resusltados envia error
            Intent intent = new Intent(ServletResponseClass.this, ErrorPaymentClass.class);
            startActivity(intent);
        }
    }

    //Salir de ventana de datos y cierra secion
    public void onClickM(View v){
        //Fin de seccion
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                //Retorna a la ventana inicial
                if(status.isSuccess()){
                    Intent intent = new Intent(ServletResponseClass.this,MainActivity.class);
                    startActivity(intent);

                    //Error de cierre de seccion
                } else {
                    Intent intent = new Intent(ServletResponseClass.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
