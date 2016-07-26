package in.sayes.nestorapp.chat.helper.model;

/**
 * Created by sourav on 03/05/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.chat.helper.model
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class InputFromType {
/*
    horizontal_scroll
    cascade_cards
    numeric_keyboard
    floating
    camera_image
    grid
    month_year_keyboard*/

    // Notes : Image -> Image / Grid , ViewPager -> COMBO / rest all input type -> TEXT

    public static final String NUMERIC_KEYBOARD="numeric_keyboard";
    public static final String FLOATING ="floating";// yes / no
    public static final String CUSTOM_KEYBOARD="custom_keyboard";
    public static final String LIST_INPUT ="cascade_cards";
    public static final String SELECT_VAL= "select-val";
    public static final String GRID="grid";
    public static final String IMAGE="camera_image";
    public static final String VIEWPAGER="horizontal_scroll";
    public static final String DATE_PICKER="calendar";

    public static final String SELECT_TYPE_SINGLE="single";
    public static final String SELECT_TYPE_MULTI="multi";


    public static final String PIE="pie";
}
