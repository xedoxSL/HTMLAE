package org.sld.htmle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ProjectsActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE = 1;
    private Toolbar toolbar;
    private ProjectsAdapter adapter;
    private ListView list;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.projects);
        setSupportActionBar(toolbar);
        requestForManage();
        existsAppFolder();
        updateProjectsList();

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        Project item = (Project) parent.getItemAtPosition(position);
                        Toast.makeText(
                                        ProjectsActivity.this,
                                        "Выбрано: " + item.getName(),
                                        Toast.LENGTH_SHORT)
                                .show();

                        Intent i = new Intent(ProjectsActivity.this, EditorActivity.class);
                        i.putExtra("name", item.getName());

                        startActivity(i);
                    }
                });
    }

    private final void updateProjectsList() {

        adapter = new ProjectsAdapter(readProjects());
        list.setAdapter(adapter);
    }

    private final void requestForManage() {
        if (!Environment.isExternalStorageManager()) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:%s" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_EXTERNAL_STORAGE);
            }
        }
    }

    public final void existsAppFolder() {
        String directoryPath =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/HTMLAEProjects";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e("DirectoryManager", "Не удалось создать директорию: " + directoryPath);
            } else {
                Log.i("DirectoryManager", "Директория успешно создана: " + directoryPath);
            }
        } else {
            Log.i("DirectoryManager", "Директория уже существует: " + directoryPath);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_project) {
            showCreateProjectWindow();
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Project> readProjects() {
        List<Project> projects = new ArrayList<>();
        String directoryPath =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/HTMLAEProjects";
        File projectsDir = new File(directoryPath);

        for (File projectDir : projectsDir.listFiles()) {
            if (projectDir.isDirectory()) {
                Project project = new Project();
                for (File file : projectDir.listFiles()) {
                    if (file.getName().equals("index.html")) {
                        project.setCodeFile(file);
                    }
                    if (file.getName().equals("config.json")) {
                        project.setConfig(file);
                    }
                    project.setName(projectDir.getName());
                }

                projects.add(project);
            }
        }

        return projects;
    }

    private final void showCreateProjectWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectsActivity.this);
        builder.setTitle(getString(R.string.create_project));

        EditText projectName = new EditText(this);
        projectName.setTextColor(getColor(R.color.textColor));
        projectName.setHint(getString(R.string.enter_project_name));
        builder.setView(projectName);
        builder.setPositiveButton(
                getString(R.string.create),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (projectName.getText().toString().isBlank()) {
                            Toast.makeText(
                                            ProjectsActivity.this,
                                            getString(R.string.project_name_cannot_be_empty),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            try {
                                createProject(projectName.getText().toString());
                                dialog.cancel();
                            } catch (Exception e) {

                            }
                        }
                    }
                });

        builder.setNegativeButton(
                getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private final void createProject(String projectName) throws IOException {
        String directoryPath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/HTMLAEProjects/"
                        + projectName;
        File projectDir = new File(directoryPath);
        if (!projectDir.exists()) {
            if (!projectDir.mkdirs()) {
                throw new IOException(
                        "Не удалось создать директорию проекта: " + projectDir.getAbsolutePath());
            }
        }

        File index = new File(projectDir, "index.html");
        File config = new File(projectDir, "config.json");
        if (!index.createNewFile()) {
            throw new IOException("Не удалось создать файл index.html: " + index.getAbsolutePath());
        }
        if (!config.createNewFile()) {
            throw new IOException(
                    "Не удалось создать файл config.json: " + config.getAbsolutePath());
        }
        updateProjectsList();
    }
}
