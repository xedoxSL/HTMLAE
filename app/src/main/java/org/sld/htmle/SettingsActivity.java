package org.sld.htmle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private EditText textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textSize = findViewById(R.id.textSize);
        textSize.setText(String.valueOf(IDESettings.getInt(getApplicationContext(), "textSize")));
    }

    @Override
    public void onBackPressed() {
        save();
        startActivity(new Intent(this, EditorActivity.class));
        finish();
    }

    private void save() {
        IDESettings.saveInt(
                getApplicationContext(),
                "textSize",
                Integer.parseInt(textSize.getText().toString()));
    }
}
