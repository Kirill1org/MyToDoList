package com.koromyslov.mytodolist.Model;

public class TaskUnit {

    private String titleTask;
    private String textTask;
    private int priorityType;
    private boolean isDone;

    public TaskUnit(String titleTast, String textTask, int priorityType, boolean isDone) {
        this.titleTask = titleTast;
        this.textTask = textTask;
        this.priorityType = priorityType;
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
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
