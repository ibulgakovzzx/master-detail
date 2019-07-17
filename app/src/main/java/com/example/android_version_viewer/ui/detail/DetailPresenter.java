package com.example.android_version_viewer.ui.detail;

import com.example.android_version_viewer.data.db.model.PlatformVersion;

public interface DetailPresenter {
    void onAttach(DetailView view);
    void onDetach();

    void addToFavorite(PlatformVersion model);
}
