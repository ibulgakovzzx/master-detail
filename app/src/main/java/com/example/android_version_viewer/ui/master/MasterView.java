package com.example.android_version_viewer.ui.master;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public interface MasterView {
    void showList(List<PlatformVersion> list);

    int getContainerId();
    int getDetailContainerId();
}
