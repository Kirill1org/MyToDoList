package com.koromyslov.mytodolist;

import android.widget.TextView;

public class TaskUnit {

    private String titleTast;
    private String textTask;
    private int priorityType;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public TaskUnit(String titleTast, String textTask, int priorityType, boolean isDone) {
        this.titleTast = titleTast;
        this.textTask = textTask;
        this.priorityType = priorityType;
        this.isDone=isDone;
    }

    public String getTitleTast() {
        return titleTast;
    }

    public void setTitleTast(String titleTast) {
        this.titleTast = titleTast;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public int getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(int priorityType) {
        this.priorityType = priorityType;
    }
}
