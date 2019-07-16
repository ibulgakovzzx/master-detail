package com.example.android_version_viewer.data;

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

    public void updateItem(PlatformVersion item) {
        dao.update(item);
    }

    public void deleteItem(PlatformVersion item) {
        dao.delete(item);
    }
}
