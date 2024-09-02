package org.sld.htmle;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EditorActivity extends AppCompatActivity {

    private CodeEditor editor;
    private String projectPath;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editor = findViewById(R.id.editor);
        editor.setContext(this);

        name = getIntent().getStringExtra("name");
        projectPath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/HTMLAEProjects/"
                        + name;
        File codeFile = new File(projectPath, "index.html");

        try (BufferedReader br = new BufferedReader(new FileReader(codeFile))) {
            StringBuilder code = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                code.append(line).append("\n");
            }
            if (!code.toString().isBlank()) {
                editor.setText(code.toString());
            }
        } catch (IOException e) {
            Log.e("EditorActivity", "Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.run_preview) {
            Intent i = new Intent(EditorActivity.this, OutputActivity.class);
            i.putExtra("code", editor.getText().toString());
            i.putExtra("projectName", name);
            startActivity(i);
            saveCode();
            finish();
        } else if (item.getItemId() == R.id.save) {
            saveCode();
        } else if (item.getItemId() == R.id.regex) {
            showRegexMenu();
        } else if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, ProjectsActivity.class);
            startActivity(i);
        }
        return true;
    }

    private final void saveCode() {
        File codeFile = new File(projectPath, "index.html");
        try (FileWriter writer = new FileWriter(codeFile)) {
            writer.write(editor.getText().toString());
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private final void showRegexMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.regex));

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Действия при нажатии на кнопку "OK"
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(
                "Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Действия при нажатии на кнопку "Отмена"
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
