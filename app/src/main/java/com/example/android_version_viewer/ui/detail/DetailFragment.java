package com.example.android_version_viewer.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.android_version_viewer.R;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

public class DetailFragment extends Fragment implements DetailView, View.OnClickListener {

    private static final String PLATFORM_VERSION_EXTRA = "platform_version";

    private TextView tvDetail;
    private AppCompatButton btnAddToFavourite;

    private DetailPresenter presenter;
    private PlatformVersion platformVersion;

    public static DetailFragment newInstance(PlatformVersion platformVersion) {

        Bundle args = new Bundle();
        args.putSerializable(PLATFORM_VERSION_EXTRA, platformVersion);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if(args != null) {
            platformVersion = (PlatformVersion) args.getSerializable(PLATFORM_VERSION_EXTRA);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new DetailPresenterImplementation(platformVersion);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        tvDetail = view.findViewById(R.id.tv_detail);
        btnAddToFavourite = view.findViewById(R.id.btn_favourite);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddToFavourite.setOnClickListener(this);

        presenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_favourite) {
            presenter.addToFavorite(platformVersion);
        }
    }

    @Override
    public void showDetails(String details) {
        tvDetail.setText(details);
    }

    @Override
    public void setFavoriteStatus(boolean isFavourite) {
            btnAddToFavourite.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    isFavourite ? R.drawable.ic_star : R.drawable.ic_unstar,
                    0);
    }
}


