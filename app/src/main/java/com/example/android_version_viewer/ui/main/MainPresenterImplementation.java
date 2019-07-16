package com.example.android_version_viewer.ui.main;

import androidx.fragment.app.FragmentManager;

import com.example.android_version_viewer.ui.master.MasterFragment;
import com.example.android_version_viewer.util.FragmentUtil;

public class MainPresenterImplementation implements MainPresenter {

    private MainView view;
    private final FragmentUtil fragmentUtil;

    MainPresenterImplementation(FragmentManager fragmentManager) {
        this.fragmentUtil = new FragmentUtil(fragmentManager);
    }

    @Override
    public void onAttach(MainView view, boolean forTablets) {
        this.view = view;
        this.fragmentUtil.changeFragment(view.getContainerId(), MasterFragment.newInstance(forTablets));
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
