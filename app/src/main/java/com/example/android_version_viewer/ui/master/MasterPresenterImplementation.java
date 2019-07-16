package com.example.android_version_viewer.ui.master;

import androidx.fragment.app.FragmentManager;

import com.example.android_version_viewer.data.PlatformVersionRepository;
import com.example.android_version_viewer.data.db.AppDatabase;
import com.example.android_version_viewer.data.db.model.PlatformVersion;
import com.example.android_version_viewer.ui.detail.DetailFragment;
import com.example.android_version_viewer.util.FragmentUtil;

public class MasterPresenterImplementation implements MasterPresenter {

    private final PlatformVersionRepository repository;
    private final FragmentUtil fragmentUtil;
    private final boolean forTablets;

    private MasterView view;

    MasterPresenterImplementation(FragmentManager fragmentManager, boolean forTablets) {
        this.repository = new PlatformVersionRepository(AppDatabase.getInstance());
        this.fragmentUtil = new FragmentUtil(fragmentManager);
        this.forTablets = forTablets;
    }

    @Override
    public void onAttach(MasterView view) {
        this.view = view;
        this.view.showList(repository.getList());
    }

    @Override
    public void onDetach() {
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
        view.updateListItem(item);
    }

    @Override
    public void deleteItem(PlatformVersion item) {
        repository.deleteItem(item);
        view.deleteItem(item);
    }
}
