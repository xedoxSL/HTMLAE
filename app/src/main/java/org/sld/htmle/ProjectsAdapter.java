package org.sld.htmle;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public final class ProjectsAdapter extends BaseAdapter {

    private List<Project> list;

    public ProjectsAdapter(List<Project> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            view =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_project, parent, false);
        }
        TextView name = view.findViewById(R.id.project_name);
        name.setText(list.get(pos).getName());
        return view;
    }
}
