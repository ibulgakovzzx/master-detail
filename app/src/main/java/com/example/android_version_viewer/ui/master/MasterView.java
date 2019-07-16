package com.example.android_version_viewer.ui.master;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public interface MasterView {
    void showList(List<PlatformVersion> list);
    void updateListItem(PlatformVersion item);
    void deleteItem(PlatformVersion item);

    int getContainerId();
    int getDetailContainerId();
}
