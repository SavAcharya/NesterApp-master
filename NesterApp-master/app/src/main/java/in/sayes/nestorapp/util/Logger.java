package in.sayes.nestorapp.util;

import android.util.Log;

import java.util.Locale;

/**
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class Logger {

    public static final String LOG_TAG = "LOGGER";

    private static boolean DEBUG = true;
    private static final boolean VERBOSE = true;



    public static void enableDebugLog(boolean enable){
        DEBUG = enable;
    }

    public static void v(String msg) {
        if(VERBOSE) Log.v(LOG_TAG, buildMessage(msg));
    }

    public static void d(String msg) {
        if(DEBUG) Log.d(LOG_TAG, buildMessage(msg));
    }

    public static void e(String msg) {
        if(DEBUG)   Log.e(LOG_TAG, buildMessage(msg));
    }

    public static void e(String msg,Throwable tr) {
        Log.e(LOG_TAG, buildMessage(msg), tr);
    }

    public static void wtf(String msg) {
        Log.wtf(LOG_TAG, buildMessage(msg));
    }

    public static void wtf(String msg, Throwable tr) {
        Log.wtf(LOG_TAG, buildMessage(msg), tr);
    }


    //this method will append the log message with <classname>.<methodname>:<linenumber> of the caller of log method
    private static String buildMessage(String msg) {
        StackTraceElement[] trace = (new Throwable()).fillInStackTrace().getStackTrace();
        String caller = "<unknown>";

        for(int i = 2; i < trace.length; ++i) {
            Class clazz = trace[i].getClass();
            if(!clazz.equals(Logger.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf(46) + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf(36) + 1);
                caller = callingClass + "." + trace[i].getMethodName()+":"+trace[i].getLineNumber();
                break;
            }
        }

        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), caller, msg});
    }


    public static void i(String s) {
        if(VERBOSE) Log.i(LOG_TAG, buildMessage(s));

    }
}
