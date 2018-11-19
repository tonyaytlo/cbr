package com.dev.anton.cbr.presentation;

import android.app.Application;
import android.content.Context;

import com.dev.anton.cbr.data.di.DataModuleImpl;
import com.dev.anton.cbr.domain.di.DomainModuleImpl;
import com.dev.anton.cbr.presentation.di.PresentationModuleImpl;

public class CbrApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        PresentationModuleImpl.build(new DomainModuleImpl(new DataModuleImpl()));
    }

    public static Context getAppContext() {
        return appContext;
    }
}
