package in.sayes.nestorapp.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class DateTimeUtil {

    private static final String TAG = "DateTimeUtil";

    public static final String LAST_SEEN_UPLOAD_TIME_FORMAT = "yyyy-MM-dd,HH:mm:ss";
    public static final String LAST_SEEN_SAVE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static String getTimeZoneId() {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        Log.d(TAG, "Timezone: " + tz.getID());

        return tz.getID();
    }


    public static String getFormattedLastSeenDate(){

        return new SimpleDateFormat(LAST_SEEN_SAVE_TIME_FORMAT, Locale
                .getDefault()).format(new Date());
    }


    public static String getFormattedLastSeenUpload(){

        return new SimpleDateFormat(LAST_SEEN_UPLOAD_TIME_FORMAT, Locale
                .getDefault()).format(new Date());
    }

}
