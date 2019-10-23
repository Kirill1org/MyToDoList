package com.koromyslov.mytodolist.Views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.koromyslov.mytodolist.Model.TaskUnit;

import java.util.List;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private RVAdapter rvAdapter;
    private List<TaskUnit> tasksData;
    private RecyclerView rv;


    public SwipeToDeleteCallback(RVAdapter rvAdapter, RecyclerView rv, List<TaskUnit> tasksData) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.rvAdapter = rvAdapter;
        this.tasksData = tasksData;
        this.rv = rv;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        tasksData.remove(position);
        rv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
}

