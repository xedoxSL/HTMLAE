package org.sld.htmle;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.IOException;
import java.io.InputStream;

public class OutputActivity extends AppCompatActivity {
    
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        String htmlCode = getIntent().getStringExtra("code");
        String name = getIntent().getStringExtra("projectName");
        webView.loadData(htmlCode, "text/html", "UTF-8");
        
        toolbar.setSubtitle(name);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EditorActivity.class));
        finish();
    }
}
