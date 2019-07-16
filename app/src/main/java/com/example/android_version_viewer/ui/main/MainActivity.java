package com.example.android_version_viewer.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_version_viewer.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImplementation(getSupportFragmentManager());

        boolean forTablets = findViewById(R.id.fl_detail_container) != null;

        if(savedInstanceState == null) {
            presenter.onAttach(this, forTablets);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public int getContainerId() {
        return R.id.fl_container;
    }
}
