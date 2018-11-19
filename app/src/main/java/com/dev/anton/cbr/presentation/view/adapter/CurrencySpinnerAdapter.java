package com.dev.anton.cbr.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.anton.cbr.domain.model.Currency;

import java.util.List;

public class CurrencySpinnerAdapter extends ArrayAdapter<Currency> {

    private final Context context;
    private final List<Currency> currency;

    public CurrencySpinnerAdapter(Context context, int textViewResourceId, List<Currency> currency) {
        super(context, textViewResourceId, currency);
        this.context = context;
        this.currency = currency;
    }

    @Override
    public int getCount() {
        return currency.size();
    }

    @Override
    public Currency getItem(int position) {
        return currency.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvLabel = (TextView) super.getView(position, convertView, parent);
        Currency item = currency.get(position);
        tvLabel.setText(item.getCharCode());
        return tvLabel;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView tvLabel = (TextView) super.getDropDownView(position, convertView, parent);
        Currency item = currency.get(position);
        SpannableStringBuilder str = new SpannableStringBuilder(String.format("%s - %s", item.getCharCode(), item.getName()));
        str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, item.getCharCode().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLabel.setText(str);
        return tvLabel;
    }
}
