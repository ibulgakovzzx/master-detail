package com.example.android_version_viewer.data.db.dao;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public interface PlatformVersionDao {
    void insert(PlatformVersion dbStructure);
    void insert(List<PlatformVersion> dbStructures);
    void delete(PlatformVersion dbStructure);
    void update(PlatformVersion dbStructure);
    List<PlatformVersion> getList();
}
