package com.example.android_version_viewer.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    private final FragmentManager fragmentManager;

    public FragmentUtil(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, false);
    }

    public void changeFragment(int containerId, Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment);

        if(addBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}
