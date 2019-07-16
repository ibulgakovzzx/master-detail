package com.example.android_version_viewer.data.db.convertor;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

public class PlatformVersionConverterImplementation implements PlatformVersionConverter {
    @Override
    public PlatformVersion getDbStructureFromCursor(Cursor cursor) {
        if(cursor == null)
            return null;

        return  new PlatformVersion(
                getStringFromCursor(cursor, PlatformVersion.VERSION),
                getStringFromCursor(cursor, PlatformVersion.NAME),
                getStringFromCursor(cursor, PlatformVersion.RELEASED),
                getIntegerFromCursor(cursor, PlatformVersion.API),
                getFloatFromCursor(cursor, PlatformVersion.DISTRIBUTION),
                getBooleanFromCursor(cursor, PlatformVersion.FAVOURITE),
                getStringFromCursor(cursor, PlatformVersion.DESCRIPTION));
    }

    @Override
    public ContentValues getContentValuesFromDbStructure(PlatformVersion dbStructure) {
        ContentValues values = new ContentValues();

        if(dbStructure == null)
            return values;

        values.put(PlatformVersion.VERSION, dbStructure.getVersion());
        values.put(PlatformVersion.NAME, dbStructure.getName());
        values.put(PlatformVersion.RELEASED, dbStructure.getReleased());
        values.put(PlatformVersion.API, dbStructure.getApi());
        values.put(PlatformVersion.DISTRIBUTION, dbStructure.getDistribution());
        values.put(PlatformVersion.FAVOURITE, dbStructure.getFavourite());
        values.put(PlatformVersion.DESCRIPTION, dbStructure.getDescription());
        return values;
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex != -1){
            return cursor.getString(columnIndex);
        }

        return null;
    }

    private Integer getIntegerFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex != -1){
            return cursor.getInt(columnIndex);
        }

        return 0;
    }

    private Float getFloatFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex != -1){
            return cursor.getFloat(columnIndex);
        }

        return 0.0f;
    }

    private Boolean getBooleanFromCursor(Cursor cursor, String columnName) {
        Integer value = getIntegerFromCursor(cursor, columnName);
        return value.equals(1);
    }
}
