package com.local.gsalas.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        //URL de la pagina de prueba
        String url = ("https://aplicacionrally.000webhostapp.com/");

        WebView webViewPrueba = findViewById(R.id.wvp);
        webViewPrueba.getSettings().setJavaScriptEnabled(true);

        webViewPrueba.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    //Compara si el URL es igual al de la ventana de pago exitoso
                    if (url.equals("https://developer.android.com/reference/android/webkit/WebView.html")) {
                        Intent NextLayout = new Intent(WebViewClass.this, SuccessfulPaymentClass.class);
                        startActivity(NextLayout);

                        //Compara si el URL es igual al de la ventana de Error en pago
                    } else if (url.equals("https://developer.android.com/reference/android/webkit/WebSettings.html")) {
                        Intent NextLayout = new Intent(WebViewClass.this, ErrorPaymentClass.class);
                        startActivity(NextLayout);
                    }
                } catch (Exception e) {
                    String ee = e.toString();
                }
                //Cargar la pagina de nuevo
                super.onPageStarted(view, url, favicon);
            }
        });
        //Carga el URL inicial
        webViewPrueba.loadUrl(url);
    }
}
