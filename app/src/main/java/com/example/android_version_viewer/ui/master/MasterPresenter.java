package com.example.android_version_viewer.ui.master;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

public interface MasterPresenter {
    void onAttach(MasterView view);
    void onDetach();

    void goToDetailScreen(PlatformVersion item);

    void setStartStatus(PlatformVersion item);
    void deleteItem(PlatformVersion item);
}
