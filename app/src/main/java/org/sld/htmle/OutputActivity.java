package org.sld.htmle;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;

public class OutputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);

        // String htmlCode = "<!DOCTYPE html><html><head><title>My Web
        // Page</title></head><body><h1>Hello, World!</h1></body></html>";
        // webView.loadData(htmlCode, "text/html", "UTF-8");

        String htmlCode = IDESettings.getString(getApplicationContext(), "code");

        webView.loadData(htmlCode, "text/html", "UTF-8");
    }
}
