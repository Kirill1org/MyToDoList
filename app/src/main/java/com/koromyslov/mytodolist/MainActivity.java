package com.koromyslov.mytodolist;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koromyslov.mytodolist.Notification.AlertReceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAddFragment.OnFragmentInteractionListener, RVAdapter.OnItemClickListener
        , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private List<TaskUnit> taskList;
    private int day, month, year, hour, minute;
    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    private String taskTitle;
    private int taskId;

    private RecyclerView rv;
    private FrameLayout fragmentContainer;
    private TaskAddFragment taskAddFragment;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton fab;
    private AlarmManager alarmManager;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadData();

        fragmentContainer = findViewById(R.id.fragment_container);
        rv = findViewById(R.id.recycler_view);
        itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback((RVAdapter) rv.getAdapter()));

        rv.setAdapter(new RVAdapter(getApplicationContext(), taskList, this));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(rv);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                rv.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                fragmentContainer.setVisibility(View.VISIBLE);

                taskAddFragment = TaskAddFragment.newInstance();

                FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.add(R.id.fragment_container, taskAddFragment);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onFragmentInteraction(String titleTask, String descriptionTask,
                                      int indicatorPriorityTask, int position, boolean isCreated) {
        if (isCreated) taskList.remove(position);
        taskList.add(position, new TaskUnit(titleTask, descriptionTask, indicatorPriorityTask, false));

        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.remove(taskAddFragment);
        fragmentManager.commit();

        rv.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        rv.setAdapter(new RVAdapter(getApplicationContext(), taskList, this));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.getAdapter().notifyDataSetChanged();

    }


    @Override
    public void onCheckBoxClick(TaskUnit taskUnit) {
        if (!taskUnit.isDone())
            Toast.makeText(this, "Congratulation! You have done: " + taskUnit.getTitleTast(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onEditBtnClick(TaskUnit taskUnit, int position) {
        rv.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);
        fragmentContainer.setVisibility(View.VISIBLE);

        taskAddFragment = TaskAddFragment.newInstance(taskUnit.getTitleTast(), taskUnit.getTextTask(), taskUnit.getPriorityType(), position);
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.fragment_container, taskAddFragment);

        fragmentManager.addToBackStack(null);
        fragmentManager.commit();

    }

    @Override
    public void onDateBtnClick(TaskUnit taskUnit, int position) {

        taskTitle = taskUnit.getTitleTast();
        taskId = position;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, year, month, day);
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, true);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i - 1;
        minuteFinal = i1;

        createNotify(taskId, taskTitle, yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);

    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("taskList", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<TaskUnit>>() {
        }.getType();
        taskList = gson.fromJson(json, type);

        if (taskList == null) taskList = new ArrayList<>();

    }

    private void createNotify(int ID_NOTIFY, String taskTitle, int year, int month,
                              int date, int hour, int minute) {
        Log.e("CREATE NOTIFU", "YES");

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("taskTitle", taskTitle);
        intent.putExtra("taskID", ID_NOTIFY);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ID_NOTIFY, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }


    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private RVAdapter rvAdapter;


        public SwipeToDeleteCallback(RVAdapter rvAdapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            rvAdapter = rvAdapter;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            taskList.remove(position);
            rv.getAdapter().notifyDataSetChanged();


        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
    }


}
