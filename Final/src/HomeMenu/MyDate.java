package HomeMenu;

import java.util.Calendar;

public class MyDate {
    int year;
    int month;
    int day;
    Calendar c = Calendar.getInstance();

    public MyDate() {
        c.setTimeInMillis(System.currentTimeMillis());
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public MyDate(long elapseTime){
        c.setTimeInMillis(elapseTime);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public MyDate(int year , int month , int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDate(long elapseTime){
        c.setTimeInMillis(elapseTime);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
}
}
