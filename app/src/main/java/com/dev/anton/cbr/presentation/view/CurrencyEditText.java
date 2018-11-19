package com.dev.anton.cbr.presentation.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.dev.anton.cbr.domain.model.CurrencyValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyEditText extends android.support.v7.widget.AppCompatEditText {

    private final Pattern currencyMask = Pattern.compile("^[0-9]*.[0-9]{0,4}");

    public CurrencyEditText(Context context) {
        super(context);
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //removeTextChangedListener(this);
                Matcher currencyMatcher = currencyMask.matcher(s.toString());
                if (currencyMatcher.find()) {
                    String trimZero = currencyMatcher.group().replaceFirst("^[0]+", "0");
                    if (!trimZero.equals(s.toString())) {
                        setText(trimZero);
                    }
                } else {
                    resetCurrency();
                }
                //addTextChangedListener(this);
            }
        });
        resetCurrency();
    }

    public void resetCurrency() {
        this.setText("0");
    }

    public CurrencyValue getValute() {
        return new CurrencyValue(getText().toString());
    }
}
