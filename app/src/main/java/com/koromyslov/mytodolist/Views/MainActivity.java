package com.koromyslov.mytodolist.Views;

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
import com.koromyslov.mytodolist.Model.Consts;
import com.koromyslov.mytodolist.Model.NotifyData;
import com.koromyslov.mytodolist.Model.TasksData;
import com.koromyslov.mytodolist.Notification.AlertReceiver;
import com.koromyslov.mytodolist.R;
import com.koromyslov.mytodolist.Model.TaskUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements RVAdapter.OnItemClickListener
        , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private NotifyData notifyData = new NotifyData();
    private TasksData tasksData = new TasksData();

    private RecyclerView rv;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton fab;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        initData();
        setClickListeners();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Consts.ADD_TASK_CODE) {

            if (resultCode == RESULT_OK) {

                String titleTask = data.getStringExtra(Consts.TASK_TITLE_VALUE);
                String descriptionTask = data.getStringExtra(Consts.TASK_DESCRIPTION_VALUE);
                int indicatorTask = data.getIntExtra(Consts.TASK_INDICATOR_VALUE, 1);
                boolean isTaskCreated = data.getBooleanExtra(Consts.TASK_IS_CREATER_VALUE, false);
                Log.e("IsTaskCreated: ", Boolean.toString(isTaskCreated));
                int position = data.getIntExtra(Consts.TASK_POSITION_VALUE, 0);

                if (isTaskCreated) tasksData.getTaskList().remove(position);
                tasksData.addTask(position, new TaskUnit(titleTask, descriptionTask, indicatorTask, false));

                rv.getAdapter().notifyDataSetChanged();

            }
        }
    }

    private void setClickListeners() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TaskAddActivity.class);
                intent.putExtra(Consts.IS_TASK_CREATED_TAG, false);
                startActivityForResult(intent, Consts.ADD_TASK_CODE);

            }
        });

    }

    private void initViews() {

        rv = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasksData.getTaskList());
        editor.putString("taskList", json);
        editor.apply();
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<TaskUnit>>() {
        }.getType();
        tasksData.setTaskList(gson.fromJson(json, type));

        if (tasksData.getTaskList() == null) tasksData.setTaskList(new ArrayList<>());

        itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback((RVAdapter) rv.getAdapter(), rv, tasksData.getTaskList()));

        rv.setAdapter(new RVAdapter(getApplicationContext(), tasksData.getTaskList(), this));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        itemTouchHelper.attachToRecyclerView(rv);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }


    @Override
    public void onCheckBoxClick(TaskUnit taskUnit) {
        if (!taskUnit.isDone())
            Toast.makeText(this, "Congratulation! You have done: " + taskUnit.getTitleTask(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditBtnClick(TaskUnit taskUnit, int position) {

        Intent intent = new Intent(getApplicationContext(), TaskAddActivity.class);
        intent.putExtra(Consts.IS_TASK_CREATED_TAG, true);
        intent.putExtra(Consts.TASK_TITLE_VALUE, tasksData.getTaskList().get(position).getTitleTask());
        intent.putExtra(Consts.TASK_DESCRIPTION_VALUE, tasksData.getTaskList().get(position).getTextTask());
        intent.putExtra(Consts.TASK_INDICATOR_VALUE, tasksData.getTaskList().get(position).getPriorityType());
        intent.putExtra(Consts.TASK_POSITION_VALUE, position);
        startActivityForResult(intent, Consts.ADD_TASK_CODE);

    }

    @Override
    public void onDateBtnClick(TaskUnit taskUnit, int position) {

        notifyData.setTaskTitle(taskUnit.getTitleTask());
        notifyData.setTaskId(position);
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        notifyData.setYearFinal(i);
        notifyData.setMonthFinal(i1);
        notifyData.setDayFinal(i2);

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                MainActivity.this, calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        notifyData.setHourFinal(i - 1);
        notifyData.setMinuteFinal(i1);

        createNotify(notifyData.getTaskId(), notifyData.getTaskTitle(), notifyData.getYearFinal(),
                notifyData.getMonthFinal(), notifyData.getDayFinal(), notifyData.getHourFinal(),
                notifyData.getMinuteFinal());

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


}
