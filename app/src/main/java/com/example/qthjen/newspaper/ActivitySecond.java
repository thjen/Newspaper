package com.example.qthjen.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActivitySecond extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        wv = (WebView) findViewById(R.id.wv);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        wv.loadUrl(link);
        wv.setWebViewClient(new WebViewClient());

    }
}
