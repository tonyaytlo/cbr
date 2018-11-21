package com.dev.anton.cbr.presentation.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dev.anton.cbr.R;
import com.dev.anton.cbr.domain.model.Currency;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.presentation.model.CurrencyConverter;
import com.dev.anton.cbr.presentation.view.adapter.CurrencySpinnerAdapter;

public class CurrencyConvertView extends CardView {

    private CurrencyEditText etCurrencyFrom;
    private Spinner srCurrencyFrom;
    private TextView tvCurrencyTo;
    private Spinner srCurrencyTo;
    private Button btnConvert;
    private TextView tvDesc;
    private ImageView ivClock;

    private CurrencyConverter converter = new CurrencyConverter();
    private Currency currencyFrom;
    private Currency currencyTo;
    private OnCurrencySelectListener internalSelectListener;

    public CurrencyConvertView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public CurrencyConvertView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CurrencyConvertView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet atttrs) {
        setRadius(getContext().getResources().getDimension(R.dimen.corner_radius));
        inflate(getContext(), R.layout.currency_exchange_view_big, this);

        etCurrencyFrom = findViewById(R.id.etCurrencyFrom);
        srCurrencyFrom = findViewById(R.id.srCurrencyFrom);
        tvCurrencyTo = findViewById(R.id.tvCurrencyTo);
        srCurrencyTo = findViewById(R.id.srCurrencyTo);
        btnConvert = findViewById(R.id.btnConvert);
        tvDesc = findViewById(R.id.tvDesc);
        ivClock = findViewById(R.id.ivClock);

        btnConvert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
        internalSelectListener = new OnCurrencySelectListener() {
            @Override
            public void onCurrencySelect(Currency currencyFrom, Currency currencyTo) {
                enableConvert(currencyFrom != null && currencyTo != null);
                clearToCurrency();
            }
        };
    }

    public void setConverter(CurrencyConverter converter) {
        this.converter = converter;
    }

    public void populate(CurrencyInfo currencyInfo) {
        final String date = currencyInfo.getDate();
        if (date != null && !date.isEmpty()) {
            setDescription(String.format(getContext().getString(R.string.date_update_format), date));
        } else {
            setDescriptionVisibility(false);
        }

        final CurrencySpinnerAdapter adapterFrom = new CurrencySpinnerAdapter(this.getContext(), R.layout.spinner_item, currencyInfo.getCurrencies());
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srCurrencyFrom.setAdapter(adapterFrom);
        srCurrencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyFrom = adapterFrom.getItem(position);
                internalSelectListener.onCurrencySelect(currencyFrom, currencyTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final CurrencySpinnerAdapter adapterTo = new CurrencySpinnerAdapter(this.getContext(), R.layout.spinner_item, currencyInfo.getCurrencies());
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srCurrencyTo.setAdapter(adapterTo);
        srCurrencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyTo = adapterFrom.getItem(position);
                internalSelectListener.onCurrencySelect(currencyFrom, currencyTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void convertCurrency() {
        tvCurrencyTo.setText(converter.convert(etCurrencyFrom.getValute(), currencyFrom, currencyTo).getValuteString());
    }

    public void enableConvert(boolean enable) {
        btnConvert.setEnabled(enable);
    }

    public void setDescription(String text) {
        tvDesc.setText(text);
        setDescriptionVisibility(true);
    }

    public void setDescriptionVisibility(boolean visible) {
        tvDesc.setVisibility(visible ? VISIBLE : GONE);
        ivClock.setVisibility(visible ? VISIBLE : GONE);
    }

    private void clearToCurrency() {
        tvCurrencyTo.setText("0");
    }

    private interface OnCurrencySelectListener {
        void onCurrencySelect(Currency currencyFrom, Currency currencyTo);
    }
}
