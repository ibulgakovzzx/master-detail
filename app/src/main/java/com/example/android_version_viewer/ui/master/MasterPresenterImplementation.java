package com.example.android_version_viewer.ui.master;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import androidx.fragment.app.FragmentManager;

import com.example.android_version_viewer.data.PlatformVersionRepository;
import com.example.android_version_viewer.data.db.AppDatabase;
import com.example.android_version_viewer.data.db.model.PlatformVersion;
import com.example.android_version_viewer.ui.detail.DetailFragment;
import com.example.android_version_viewer.util.FragmentUtil;

import java.util.ArrayList;
import java.util.List;

public class MasterPresenterImplementation implements MasterPresenter {

    private final PlatformVersionRepository repository;
    private final ContentObserver observer;
    private final FragmentUtil fragmentUtil;
    private final boolean forTablets;

    private MasterView view;
    private ListFilterType currentFilterType = ListFilterType.ALL;

    MasterPresenterImplementation(FragmentManager fragmentManager, boolean forTablets) {
        this.repository = new PlatformVersionRepository(AppDatabase.getInstance());
        this.fragmentUtil = new FragmentUtil(fragmentManager);
        this.forTablets = forTablets;

        this.observer = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                showList();
            }
        };
    }

    @Override
    public void onAttach(MasterView view) {
        this.view = view;
        this.repository.listenUpdates(observer);

        showList();
    }

    @Override
    public void onDetach() {
        this.repository.stopListenUpdates(observer);
        this.view = null;
    }

    @Override
    public void goToDetailScreen(PlatformVersion item) {
        fragmentUtil.changeFragment(!forTablets
                ? view.getContainerId()
                : view.getDetailContainerId(),
                DetailFragment.newInstance(item),
                true);
    }


    @Override
    public void setStartStatus(PlatformVersion item) {
        item.setFavourite(!item.getFavourite());
        repository.updateItem(item);
    }

    @Override
    public void deleteItem(PlatformVersion item) {
        repository.deleteItem(item);
    }

    @Override
    public void filterItems(ListFilterType filterType) {
        currentFilterType = filterType;
        switch (filterType) {
            case ALL:
                view.showList(repository.getList());
                break;
            case FAVORITE:
                view.showList(getFilteredVersions(new SimplePredicate<PlatformVersion>() {
                    @Override
                    public boolean test(PlatformVersion item) {
                        return item.getFavourite();
                    }
                }));
                break;
            case DISTRIBUTION:
                view.showList(getFilteredVersions(new SimplePredicate<PlatformVersion>() {
                    @Override
                    public boolean test(PlatformVersion item) {
                        return item.getDistribution() < 3.0;
                    }
                }));
                break;
        }
    }

    private List<PlatformVersion> getFilteredVersions(SimplePredicate<PlatformVersion> predicate) {
        List<PlatformVersion> list = repository.getList();
        List<PlatformVersion> filteredList = new ArrayList<>();

        for (PlatformVersion model : list) {
            if(predicate.test(model))
                filteredList.add(model);
        }
        return filteredList;
    }

    private void showList() {
        filterItems(currentFilterType);
    }
}
