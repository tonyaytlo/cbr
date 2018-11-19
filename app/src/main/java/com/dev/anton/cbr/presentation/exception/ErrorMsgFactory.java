package com.dev.anton.cbr.presentation.exception;

import android.content.Context;

import com.dev.anton.cbr.R;
import com.dev.anton.cbr.data.exception.CursNotFoundException;

public class ErrorMsgFactory {

    private ErrorMsgFactory() {
    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.error_unknown);

        if (exception instanceof CursNotFoundException) {
            message = context.getString(R.string.error_curs_not_found);
        }

        return message;
    }
}
