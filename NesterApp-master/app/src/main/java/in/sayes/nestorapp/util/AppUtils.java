package in.sayes.nestorapp.util;

/**
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class AppUtils {


    /**
     * Extract OTP numbers from SMS
     *
     * @param msg
     * @return
     */
    public static String getDigitFromSms(String msg) {
        return msg.replaceAll("[^0-9]", "").trim();
    }
}
