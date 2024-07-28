package org.sld.htmle;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import android.os.Environment;

public class ProjectsActivity extends AppCompatActivity {

    private ListView list;
    private ProjectsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_projects);

        list = findViewById(R.id.projects_list);
        adapter = new ProjectsAdapter(getProjects());
    }

    private List<Project> getProjects() {
        List<Project> list = new ArrayList<>();
        File path = Environment.getExternalStorageDirectory();
        String folderPath = path.getAbsolutePath() + "/HTMLAEProjects/";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {

        } else {
            folder.mkdir();
        }
        return list;
    }
}
