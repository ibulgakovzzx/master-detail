package com.example.android_version_viewer.data.db.dao;

import android.database.ContentObserver;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public interface PlatformVersionDao {
    void insert(PlatformVersion dbStructure);
    void delete(PlatformVersion dbStructure);
    void update(PlatformVersion dbStructure);
    PlatformVersion get(PlatformVersion dbStructure);
    List<PlatformVersion> getList();
    void listenChanges(ContentObserver observer);
    void stopListenChanges(ContentObserver observer);
}
