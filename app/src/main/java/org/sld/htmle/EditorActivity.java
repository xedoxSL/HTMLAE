package org.sld.htmle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import static android.view.ViewGroup.LayoutParams;

public class EditorActivity extends AppCompatActivity {

    private CodeEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = findViewById(R.id.editor);
        editor.setContext(this);
        editor.setText(IDESettings.getString(getApplicationContext(), "code"));
        try {
            int getTextSize = IDESettings.getInt(getApplicationContext(), "textSize");
            editor.setTextSize(getTextSize);
        } catch (Exception e) {
        }
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
            finish();
            return true;
        } else if (item.getItemId() == R.id.settings) {
            save();
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
            return true;
        } else if (item.getItemId() == R.id.regex) {
            showRegexMenu();
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

    private void showRegexMenu() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_regex, null);

        PopupWindow popupWindow =
                new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
