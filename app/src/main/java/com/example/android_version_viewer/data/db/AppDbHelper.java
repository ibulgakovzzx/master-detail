package com.example.android_version_viewer.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AppDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/com.example.android_version_viewer/databases/";
    private static final String DATABASE_NAME = "app.db";

    private static final String CREATE_PLATFORM_VERSION_QUERY =
            "CREATE TABLE IF NOT EXISTS " + PlatformVersion.TABLE_NAME + "(" +
                    PlatformVersion.VERSION + " TEXT PRIMARY KEY," +
                    PlatformVersion.NAME + " TEXT NOT NULL," +
                    PlatformVersion.RELEASED + " TEXT NOT NULL," +
                    PlatformVersion.API + " INTEGER NOT NULL," +
                    PlatformVersion.DESCRIPTION + " REAL NOT NULL," +
                    PlatformVersion.FAVOURITE + " INTEGER DEFAULT 0," +
                    PlatformVersion.DESCRIPTION + " TEXT NOT NULL);";

    AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLATFORM_VERSION_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void createDatabase(Context context){
        try {
            if(!isDatabaseExist()) {
                copyDataBase(context);
            }
        } catch (IOException ignore) {
        }

    }

    private void copyDataBase(Context context) throws IOException {
        File databaseDir = new File(DATABASE_PATH);
        if(!databaseDir.exists()) {
            databaseDir.mkdir();
        }

        InputStream inStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outStream = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        outStream.flush();
        outStream.close();
        inStream.close();
    }

    private boolean isDatabaseExist() {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openDatabase(
                    DATABASE_PATH + DATABASE_NAME,
                    null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException ignore) {
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return db != null;
    }
}
