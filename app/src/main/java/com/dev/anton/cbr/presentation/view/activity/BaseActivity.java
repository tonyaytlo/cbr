package com.dev.anton.cbr.presentation.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    public void showToastError(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
