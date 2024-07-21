package org.sld.htmle;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CodeEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        

        editor = findViewById(R.id.editor);
        editor.setContext(this);
        editor.setText(IDESettings.getString(getApplicationContext(), "code"));

        Token[] tokens = {
            new Token(".*", getColor(R.color.textColor)),
            new Token("true|false", getColor(R.color.keyword)),
            new Token("\\d+", getColor(R.color.number)),
            new Token("<[a-zA-Z]+|</[a-zA-Z]+>", getColor(R.color.func)),
            new Token("<![a-zA-Z]+|</![a-zA-Z]+>", getColor(R.color.func)),
            new Token(
                    "(\\()|(\\))|(\\+)|(\\-)|(\\*)|(\\/)|(\\=)|(\\<)|(\\>)",
                    getColor(R.color.keyword)),
            new Token("<!--.*-->", getColor(R.color.textColor))
        };
        editor.setKeywords(tokens);
    }

    @Override
    public void onBackPressed() {

        save();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.run) {
            save();
            startActivity(new Intent(this, OutputActivity.class));
            return true;
        } else if (item.getItemId() == R.id.settings) {
            save();
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void save() {
        IDESettings.saveString(getApplicationContext(), "code", editor.getText().toString());
    }
}
