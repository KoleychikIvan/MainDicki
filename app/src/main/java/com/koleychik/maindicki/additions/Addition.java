package com.koleychik.maindicki.additions;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Addition {

    public static String getTime(){
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDateTime now = LocalDateTime.now();
//        return dtf.format(now);
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("YY:MM:dd");
        return formatForDateNow.format(dateNow);
    }

}
