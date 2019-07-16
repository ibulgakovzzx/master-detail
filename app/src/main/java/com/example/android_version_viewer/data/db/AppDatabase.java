package com.example.android_version_viewer.data.db;

import android.content.Context;

import com.example.android_version_viewer.data.db.dao.PlatformVersionDao;
import com.example.android_version_viewer.data.db.dao.PlatformVersionDaoImplementation;

public class AppDatabase {

    private static AppDatabase INSTANCE;

    public static void init(Context context){
        if (INSTANCE == null) {
            INSTANCE = new AppDatabase(context);
        }
    }

    public static AppDatabase getInstance() {
        return INSTANCE;
    }

    private PlatformVersionDao platformVersionDao;

    private AppDatabase(Context context) {
        platformVersionDao = new PlatformVersionDaoImplementation(context);
    }

    public PlatformVersionDao getPlatformVersionDao() {
        return platformVersionDao;
    }
}
