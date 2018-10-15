package com.excel.livechannels.logic;

import java.util.Calendar;
import java.util.Locale;

public class DateLogic {

    public static String getReadableDateFromMilis( long millis ){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( millis );

        int day = cal.get( Calendar.DATE );
        String month = cal.getDisplayName( Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        int year = cal.get( Calendar.YEAR );
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append( day ).append( " " ).append( month ).append( " " ).append( year );
        return formattedDate.toString();
    }
}
