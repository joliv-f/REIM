package com.example.joliv.anchi13;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fecha {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String fecchai = (dateFormat.format(Calendar.getInstance().getTime()));

}
