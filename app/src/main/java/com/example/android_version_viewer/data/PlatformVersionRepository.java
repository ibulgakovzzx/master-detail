package com.example.android_version_viewer.data;

import android.database.ContentObserver;

import com.example.android_version_viewer.data.db.AppDatabase;
import com.example.android_version_viewer.data.db.dao.PlatformVersionDao;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public class PlatformVersionRepository {

    private final PlatformVersionDao dao;

    public PlatformVersionRepository(AppDatabase database) {
        dao = database.getPlatformVersionDao();
    }

    public List<PlatformVersion> getList() {
        return dao.getList();
    }

    public PlatformVersion getItem(PlatformVersion version) {
        return dao.get(version);
    }

    public void updateItem(PlatformVersion item) {
        dao.update(item);
    }

    public void deleteItem(PlatformVersion item) {
        dao.delete(item);
    }

    public void listenUpdates(ContentObserver observer) {
        dao.listenChanges(observer);
    }

    public void stopListenUpdates(ContentObserver observer) {
        dao.stopListenChanges(observer);
    }
}
