package org.sld.htmle;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ProjectsAdapter extends BaseAdapter {

    private List<Project> projects;

    public ProjectsAdapter(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int arg0) {
        return projects.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        TextView name = (TextView) view.findViewById(R.id.project_name);
        TextView desk = (TextView) view.findViewById(R.id.project_description);
        name.setText(projects.get(pos).getName());
        desk.setText(projects.get(pos).getDesk());
        return view;
    }
}
