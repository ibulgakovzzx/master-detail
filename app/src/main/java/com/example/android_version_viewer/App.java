package com.example.android_version_viewer;

import android.app.Application;

import com.example.android_version_viewer.data.db.AppDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppDatabase.init(getApplicationContext());
    }
}
