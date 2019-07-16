package com.example.android_version_viewer.ui.detail;

import com.example.android_version_viewer.data.PlatformVersionRepository;
import com.example.android_version_viewer.data.db.AppDatabase;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

public class DetailPresenterImplementation implements DetailPresenter {
    private DetailView view;
    private final PlatformVersionRepository repository;

    DetailPresenterImplementation() {
        repository = new PlatformVersionRepository(AppDatabase.getInstance());
    }

    @Override
    public void onAttach(DetailView view, PlatformVersion model) {
        this.view = view;

        this.view.showDetails(getDetailsFromModel(model));
        this.view.setFavoriteStatus(model.getFavourite());
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void addToFavorite(PlatformVersion model) {
        model.setFavourite(!model.getFavourite());
        repository.updateItem(model);

        view.setFavoriteStatus(model.getFavourite());
    }

    private String getDetailsFromModel(PlatformVersion model) {
        return "Version: " + model.getVersion() + "\n" +
                "Name: " + model.getName() + "\n" +
                "Released: " + model.getReleased() + "\n" +
                "API: " + model.getApi() + "\n" +
                "Distribution: " + model.getDistribution() + "\n" +
                model.getDescription();
    }
}
