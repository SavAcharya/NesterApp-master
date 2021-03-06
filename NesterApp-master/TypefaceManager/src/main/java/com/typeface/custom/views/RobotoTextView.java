package com.typeface.custom.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.typeface.custom.R;
import com.typeface.custom.util.RobotoTypefaceManager;


/**
 *
 */
public class RobotoTextView extends TextView {


    public RobotoTextView(Context context) {
        super(context);
        onInitTypeface(context, null, 0);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitTypeface(context, attrs, 0);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onInitTypeface(context, attrs, defStyle);
    }


    private void onInitTypeface(Context context, AttributeSet attrs,
                                int defStyle) {
        // Typeface.createFromAsset doesn't work in the layout editor, so
        // skipping.
        if (isInEditMode()) {
            return;
        }

        int typefaceValue = RobotoTypefaceManager.ROBOTO_REGULAR;
        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs,
                    R.styleable.roboto_text_style, defStyle, 0);
            if (values != null) {
                typefaceValue = values.getInt(
                        R.styleable.roboto_text_style_typeface, typefaceValue);
                values.recycle();
            }
        }

        Typeface robotoTypeface = RobotoTypefaceManager.obtainTypeface(context,
                typefaceValue);
        setTypeface(robotoTypeface);
    }

}