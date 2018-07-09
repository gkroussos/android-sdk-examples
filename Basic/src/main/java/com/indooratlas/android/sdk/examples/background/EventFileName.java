package com.indooratlas.android.sdk.examples.background;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

class EventFileName {
    private static String filename;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public void setFileName(String phoneid) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        filename =  sdf.format(timestamp);

    }
    public String getFileName(){
        return filename;
    }
}