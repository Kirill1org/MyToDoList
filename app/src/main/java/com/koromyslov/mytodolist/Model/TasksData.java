package com.koromyslov.mytodolist.Model;

import java.util.List;

public class TasksData {

    private List<TaskUnit> taskList;

    public List<TaskUnit> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskUnit> taskList) {
        this.taskList = taskList;
    }

    public void removeTask(int position) {
        taskList.remove(position);
    }

    public void addTask(int position, TaskUnit taskUnit) {
        taskList.add(position, taskUnit);

    }


}
