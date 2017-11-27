package com.rednik.muriel.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mauricio on 24/11/17.
 */
@SuppressLint("AppCompatCustomView")
public class MulticolorTextView extends TextView {


    public MulticolorTextView(Context context) {
        super(context);
    }

    public MulticolorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MulticolorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Paint text with differents colors
     *
     * @param text   to show
     * @param colors list colors to use in text
     */
    public void setColorizedText(@NonNull String text, int[] colors) {
        float parts = (float) text.length() / (float) colors.length;
        int roundInt = (int) Math.ceil(parts);
        String[] splittedText = text.split("(?<=\\G.{" + roundInt + "})");

        for (int i = 0; i < splittedText.length; i++) {
            Spannable word = new SpannableString(splittedText[i]);
            word.setSpan(new ForegroundColorSpan(colors[i]), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            append(word);
        }
    }
}
