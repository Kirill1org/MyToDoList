package com.koromyslov.mytodolist.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koromyslov.mytodolist.Model.AddTaskData;
import com.koromyslov.mytodolist.Model.Consts;
import com.koromyslov.mytodolist.R;

public class TaskAddActivity extends AppCompatActivity {

    private AddTaskData addTaskData;

    private Button createBtn;
    private EditText editTitleTask;
    private EditText editDescriptionTask;
    private View redEllipse;
    private View yellowEllipse;
    private View greenEllipse;
    private View redVectorCheck;
    private View yellowVectorCheck;
    private View greenVectorCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        initViews();
        setListeners();
        initData();
    }

    private void initData() {

        Intent intent = getIntent();

        if (!intent.getBooleanExtra(Consts.IS_TASK_CREATED_TAG, false)) {
            addTaskData = new AddTaskData();
        }
        if (intent.getBooleanExtra(Consts.IS_TASK_CREATED_TAG, false)) {
            addTaskData = new AddTaskData(intent.getStringExtra(Consts.TASK_TITLE_VALUE),
                    intent.getStringExtra(Consts.TASK_DESCRIPTION_VALUE),
                    intent.getIntExtra(Consts.TASK_INDICATOR_VALUE, 1),
                    intent.getIntExtra(Consts.TASK_POSITION_VALUE, 0),
                    intent.getBooleanExtra(Consts.IS_TASK_CREATED_TAG, false));
        }


        editTitleTask.setText(addTaskData.getTitleTask());
        editDescriptionTask.setText(addTaskData.getDescriptionTask());
        if (addTaskData.getIndicatorTask() == 1) {
            setRedIndicator();
        } else if (addTaskData.getIndicatorTask() == 2) {
            setYellowIndicator();
        } else {
            setGreenIndicator();
        }

    }

    private void setListeners() {
        redEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRedIndicator();


            }
        });

        yellowEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setYellowIndicator();


            }
        });

        greenEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGreenIndicator();

            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitleTask.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "You should add a title!", Toast.LENGTH_LONG).show();
                } else if (editDescriptionTask.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "You should add a description!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Consts.TASK_TITLE_VALUE, editTitleTask.getText().toString());
                    intent.putExtra(Consts.TASK_DESCRIPTION_VALUE, editDescriptionTask.getText().toString());
                    intent.putExtra(Consts.TASK_INDICATOR_VALUE, addTaskData.getIndicatorTask());
                    intent.putExtra(Consts.TASK_IS_CREATER_VALUE, addTaskData.isCreated());
                    intent.putExtra(Consts.TASK_POSITION_VALUE, addTaskData.getPosition());
                    Log.e("POSITION", Integer.toString(addTaskData.getPosition()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private void initViews() {
        editTitleTask = findViewById(R.id.editTextTitle);
        editDescriptionTask = findViewById(R.id.editTextDescription);
        createBtn = findViewById(R.id.button_create);
        redEllipse = findViewById(R.id.red_ellipse);
        yellowEllipse = findViewById(R.id.yellow_ellipse);
        greenEllipse = findViewById(R.id.green_ellipse);
        redVectorCheck = findViewById(R.id.red_vector_check);
        yellowVectorCheck = findViewById(R.id.yellow_vector_check);
        greenVectorCheck = findViewById(R.id.green_vector_check);

        //Turn on back button navigation in tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setGreenIndicator() {
        addTaskData.setIndicatorTask(3);
        redVectorCheck.setVisibility(View.INVISIBLE);
        yellowVectorCheck.setVisibility(View.INVISIBLE);
        greenVectorCheck.setVisibility(View.VISIBLE);
    }

    private void setYellowIndicator() {
        addTaskData.setIndicatorTask(2);
        redVectorCheck.setVisibility(View.INVISIBLE);
        yellowVectorCheck.setVisibility(View.VISIBLE);
        greenVectorCheck.setVisibility(View.INVISIBLE);
    }

    private void setRedIndicator() {
        addTaskData.setIndicatorTask(1);
        redVectorCheck.setVisibility(View.VISIBLE);
        yellowVectorCheck.setVisibility(View.INVISIBLE);
        greenVectorCheck.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}
