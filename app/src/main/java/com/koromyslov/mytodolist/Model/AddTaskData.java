package com.koromyslov.mytodolist.Model;

public class AddTaskData {

    private String titleTask;
    private String descriptionTask;
    private int indicatorTask;
    private boolean isCreated;
    private int position;

    public AddTaskData(){
        titleTask = "";
        descriptionTask = "";
        indicatorTask = 1;
        isCreated = false;
        position = 0;

    }
    public AddTaskData(String titleTask, String descriptionTask, int indicatorTask, int position, boolean isCreated){
        this.titleTask=titleTask;
        this.descriptionTask=descriptionTask;
        this.indicatorTask=indicatorTask;
        this.position = position;
        this.isCreated = isCreated;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public int getIndicatorTask() {
        return indicatorTask;
    }

    public void setIndicatorTask(int indicatorTask) {
        this.indicatorTask = indicatorTask;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public int getPosition() {
        return position;
    }


}
