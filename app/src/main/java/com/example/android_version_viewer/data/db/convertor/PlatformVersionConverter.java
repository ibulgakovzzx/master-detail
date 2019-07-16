package com.example.android_version_viewer.data.db.convertor;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

public interface PlatformVersionConverter {
    PlatformVersion getDbStructureFromCursor(Cursor cursor);
    ContentValues getContentValuesFromDbStructure(PlatformVersion dbStructure);
}
