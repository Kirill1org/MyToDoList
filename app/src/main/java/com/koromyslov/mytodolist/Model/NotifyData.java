package com.koromyslov.mytodolist.Model;

public class NotifyData {

    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    private String taskTitle;
    private int taskId;

    public int getDayFinal() {
        return dayFinal;
    }

    public void setDayFinal(int dayFinal) {
        this.dayFinal = dayFinal;
    }

    public int getMonthFinal() {
        return monthFinal;
    }

    public void setMonthFinal(int monthFinal) {
        this.monthFinal = monthFinal;
    }

    public int getYearFinal() {
        return yearFinal;
    }

    public void setYearFinal(int yearFinal) {
        this.yearFinal = yearFinal;
    }

    public int getHourFinal() {
        return hourFinal;
    }

    public void setHourFinal(int hourFinal) {
        this.hourFinal = hourFinal;
    }

    public int getMinuteFinal() {
        return minuteFinal;
    }

    public void setMinuteFinal(int minuteFinal) {
        this.minuteFinal = minuteFinal;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
