package com.smilearts.smilenotes.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDate {

    private Calendar calendar;//HH:mm:ss
    private SimpleDateFormat simpleDateFormat;
    String date,time;

    public TimeDate() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String getTime(){
        time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public String getDateYMD(){
        String MON = "", DAY = "";
        switch ((calendar.get(Calendar.MONTH)+1)){
            case 1:
                MON = "Jan";
                break;
            case 2:
                MON = "Feb";
                break;
            case 3:
                MON = "Mar";
                break;
            case 4:
                MON = "Apr";
                break;
            case 5:
                MON = "May";
                break;
            case 6:
                MON = "Jun";
                break;
            case 7:
                MON = "Jul";
                break;
            case 8:
                MON = "Aug";
                break;
            case 9:
                MON = "Sep";
                break;
            case 10:
                MON = "Oct";
                break;
            case 11:
                MON = "Nov";
                break;
            case 12:
                MON = "Dec";
                break;
        }
        DAY = String.valueOf(calendar.get(Calendar.DATE));
        date = MON+","+DAY+","+calendar.get(Calendar.YEAR);
        return date;
    }

}
