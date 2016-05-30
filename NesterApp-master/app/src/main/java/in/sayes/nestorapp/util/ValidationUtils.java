package in.sayes.nestorapp.util;

/**
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class ValidationUtils {

    private static final int VALID_MOBILE_NO_LENGTH = 10;
    private static final int VALID_OTP_NO_LENGTH = 6;


    /**
     * Name can not be blank
     *
     * @param name
     * @return
     */
    public static boolean isNameValid(String name) {

        return (name.isEmpty()) ? false : true;

    }


    /**
     * Name can not be blank
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {

        return (password.isEmpty()) ? false : true;

    }
    /**
     * OTP can not be blank
     *
     * @param otp
     * @return
     */
    public static boolean isOTPValid(String otp) {

        return (otp.length() < VALID_OTP_NO_LENGTH) ? false : true;

    }

    /**
     * Check email validation on basis of standard email pattern
     *
     * @param email
     * @return true if email is valid
     */
    public static boolean isEmailValid(String email) {

        return (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) ? false : true;
    }


    /**
     * Check phone no. is 10 digits
     *
     * @param phoneNo
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNo) {

        return (phoneNo.length() < VALID_MOBILE_NO_LENGTH) ? false : true;
    }




}
