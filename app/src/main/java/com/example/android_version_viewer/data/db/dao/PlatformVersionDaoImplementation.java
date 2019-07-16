package com.example.android_version_viewer.data.db.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android_version_viewer.data.db.AppContentProvider;
import com.example.android_version_viewer.data.db.convertor.PlatformVersionConverter;
import com.example.android_version_viewer.data.db.convertor.PlatformVersionConverterImplementation;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.ArrayList;
import java.util.List;

public class PlatformVersionDaoImplementation implements PlatformVersionDao {

    private final ContentResolver contentResolver;
    private final Uri uri;
    private final PlatformVersionConverter converter;

    public PlatformVersionDaoImplementation(Context context) {
        contentResolver = context.getContentResolver();
        uri = Uri.withAppendedPath(AppContentProvider.CONTENT_URI, PlatformVersion.TABLE_NAME);
        converter = new PlatformVersionConverterImplementation();
    }

    @Override
    public void insert(PlatformVersion dbStructure) {
        contentResolver.insert(uri, converter.getContentValuesFromDbStructure(dbStructure));
    }

    @Override
    public void insert(List<PlatformVersion> dbStructures) {
        for (PlatformVersion dbStructure : dbStructures) {
            insert(dbStructure);
        }
    }

    @Override
    public void delete(PlatformVersion dbStructure) {
        contentResolver.delete(uri, PlatformVersion.VERSION + " = ?", new String[] { dbStructure.getVersion() });
    }

    @Override
    public void update(PlatformVersion dbStructure) {
        contentResolver.update(uri, converter.getContentValuesFromDbStructure(dbStructure), "version = ?", new String[] { dbStructure.getVersion() });
    }

    @Override
    public List<PlatformVersion> getList() {
        Cursor cursor = contentResolver.query(uri, null, null, null, PlatformVersion.API + " DESC");
        if(cursor != null) {
            List<PlatformVersion> dbStructures = new ArrayList<>();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                dbStructures.add(converter.getDbStructureFromCursor(cursor));
            }

            cursor.close();

            return dbStructures;
        }

        return new ArrayList<>();
    }


}
