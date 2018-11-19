package com.dev.anton.cbr.presentation;

import android.app.Application;

import com.dev.anton.cbr.data.di.DataModuleImpl;
import com.dev.anton.cbr.domain.di.DomainModuleImpl;
import com.dev.anton.cbr.presentation.di.PresentationModuleImpl;

public class CbrApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PresentationModuleImpl.build(new DomainModuleImpl(new DataModuleImpl(this)));
    }
}
