package com.example.android_version_viewer.ui.detail;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.example.android_version_viewer.data.PlatformVersionRepository;
import com.example.android_version_viewer.data.db.AppDatabase;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

public class DetailPresenterImplementation implements DetailPresenter {
    private final PlatformVersion currentModel;
    private final PlatformVersionRepository repository;
    private final ContentObserver observer;

    private DetailView view;

    DetailPresenterImplementation(PlatformVersion model) {
        currentModel = model;
        repository = new PlatformVersionRepository(AppDatabase.getInstance());
        observer = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                updateDetailInformation();
            }
        };
    }

    @Override
    public void onAttach(DetailView view) {
        this.view = view;

        displayDetailInformation();
        repository.listenUpdates(observer);
    }

    @Override
    public void onDetach() {
        this.repository.stopListenUpdates(observer);
        this.view = null;
    }

    @Override
    public void addToFavorite(PlatformVersion model) {
        model.setFavourite(!model.getFavourite());
        repository.updateItem(model);
    }

    private void updateDetailInformation() {
        currentModel.update(repository.getItem(currentModel));
        displayDetailInformation();
    }

    private void displayDetailInformation() {
       view.showDetails(getDetailsFromModel(currentModel));
       view.setFavoriteStatus(currentModel.getFavourite());
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
