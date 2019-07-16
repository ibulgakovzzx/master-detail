package com.example.android_version_viewer.ui.main;

public interface MainPresenter {
    void onAttach(MainView view, boolean forTablets);
    void onDetach();
}
