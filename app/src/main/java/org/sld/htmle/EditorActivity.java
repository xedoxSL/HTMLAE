package org.sld.htmle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import static android.view.ViewGroup.LayoutParams;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        EditText regex = view.findViewById(R.id.word_for_find);
        EditText wordFind = view.findViewById(R.id.word_for_replace);

        Button replace = view.findViewById(R.id.replace);
        Button find = view.findViewById(R.id.find);

        View.OnClickListener listener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view == replace) {
                            String text = editor.getText().toString();
                            Matcher m = Pattern.compile(regex.getText().toString()).matcher(text);
                            StringBuilder out = new StringBuilder();
                            int lastEnd = 0;
                            while (m.find()) {
                                out.append(text.substring(lastEnd, m.start()));
                                out.append(wordFind.getText());
                                lastEnd = m.end();
                            }
                            out.append(text.substring(lastEnd));
                            editor.setText(out.toString());
                        } else if (view == find) {
                            editor.coincidencesHighlight( regex.getText().toString());
                        }
                        popupWindow.dismiss();
                    }
                };
        find.setOnClickListener(listener);
        replace.setOnClickListener(listener);
    }
}
