package com.koromyslov.mytodolist.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.koromyslov.mytodolist.R;
import com.koromyslov.mytodolist.Model.TaskUnit;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {

    private List<TaskUnit> taskList;
    private LayoutInflater inflater;
    private Context context;
    private final OnItemClickListener clickListener;

    public RVAdapter(Context context, List<TaskUnit> taskList, OnItemClickListener clickListener) {
        this.taskList = taskList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;


    }

    public interface OnItemClickListener {
        void onCheckBoxClick(TaskUnit taskUnit);

        void onEditBtnClick(TaskUnit taskUnit, int position);

        void onDateBtnClick(TaskUnit taskUnit, int position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskViewHolder taskViewHolder = new TaskViewHolder(inflater.inflate(R.layout.task_unit, parent, false), clickListener);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTask;
        private TextView descriptionTask;
        private CheckBox checkTask;
        private View indicatorTask;
        private View editBtn;
        private Button dateBtn;


        public TaskViewHolder(@NonNull View itemView, @NonNull final OnItemClickListener listener) {
            super(itemView);

            titleTask = itemView.findViewById(R.id.title_task);
            descriptionTask = itemView.findViewById(R.id.description_task);
            checkTask = itemView.findViewById(R.id.checkBox);
            indicatorTask = itemView.findViewById(R.id.indicator_view);
            editBtn = itemView.findViewById(R.id.editBtnView);
            dateBtn = itemView.findViewById(R.id.notify_btn);

            checkTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onCheckBoxClick(taskList.get(position));

                        if (checkTask.isChecked()) {
                            taskList.get(position).setDone(true);
                            crossOutText();
                        }
                        if (!checkTask.isChecked()) {
                            taskList.get(position).setDone(false);
                            clearOutText();
                        }

                    }
                }


            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onEditBtnClick(taskList.get(position), position);

                    }

                }
            });
            dateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onDateBtnClick(taskList.get(position), position);

                    }

                }
            });


        }


        @SuppressLint("ResourceAsColor")
        public void bind(TaskUnit taskUnit) {

            titleTask.setText(taskUnit.getTitleTask());
            descriptionTask.setText(taskUnit.getTextTask());
            checkTask.setChecked(taskUnit.isDone());

            if (checkTask.isChecked()) {
                crossOutText();

            }
            if (!checkTask.isChecked()) {
                clearOutText();


            }

            if (taskUnit.getPriorityType() == 1)
                indicatorTask.setBackgroundTintList(context.getResources().getColorStateList(R.color.red_color));
            if (taskUnit.getPriorityType() == 2)
                indicatorTask.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow_color));
            if (taskUnit.getPriorityType() == 3)
                indicatorTask.setBackgroundTintList(context.getResources().getColorStateList(R.color.green_color));
        }

        public void clearOutText() {
            titleTask.setPaintFlags(titleTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            descriptionTask.setPaintFlags(descriptionTask.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        public void crossOutText() {
            titleTask.setPaintFlags(titleTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            descriptionTask.setPaintFlags(descriptionTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
