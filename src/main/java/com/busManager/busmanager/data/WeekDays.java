package com.busManager.busmanager.data;

public enum WeekDays {
    MONDAY(1,"Monday"), TUESDAY(2,"Tuesday"), WEDNESDAY(3,"Wednesday"), THURSDAY(4,"Thursday"), FRIDAY(5,"Friday"), SATURDAY(6,"Saturday"), SUNDAY(7,"Sunday");

    public int index;
    public String value;

    WeekDays(int index,String value) {
        this.index = index;
        this.value= value;
    }
}
