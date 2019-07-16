package com.example.android_version_viewer.data.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.contentprovider/");

    private AppDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new AppDbHelper(getContext());
        dbHelper.createDatabase(getContext());
        return true;
    }

    @Override
    public String getType(@Nullable Uri uri) {
        if (uri == null)
            return null;
        return getTableName(uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String where, String[] args) {
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        int value = dataBase.delete(getTableName(uri), where, args);

        notifyUriChange(uri);

        return value;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues initialValues) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long value = database.insert(getTableName(uri), null, initialValues);

        notifyUriChange(uri);

        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(value));
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        notifyUriChange(uri);

        return database.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String whereClause,
                      String[] whereArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int value =  database.updateWithOnConflict(getTableName(uri), values, whereClause, whereArgs, SQLiteDatabase.CONFLICT_REPLACE);

        notifyUriChange(uri);

        return value;
    }

    private void notifyUriChange(Uri uri) {
        Context context = getContext();
        if(context == null)
            return;

        context.getContentResolver().notifyChange(uri, null);
    }

    public String getTableName(@NonNull Uri uri) {
        String value = uri.getPath();
        return value.replace("/", "");
    }
}
