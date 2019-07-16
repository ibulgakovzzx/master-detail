package com.example.android_version_viewer.ui.master;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_version_viewer.R;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public class MasterFragment extends Fragment implements MasterView, RecyclerClickListener {

    private static final String FOR_TABLETS_EXTRA = "for_tablets";
    private static final String SELECTED_INDEX = "selected_item_index";

    private RecyclerView rvPlatformVersions;
    private PlatformVersionsAdapter listAdapter;

    private boolean forTablets;
    private MasterPresenter presenter;

    public static MasterFragment newInstance(boolean forTablets) {

        Bundle args = new Bundle();
        args.putBoolean(FOR_TABLETS_EXTRA, forTablets);

        MasterFragment fragment = new MasterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if(args != null) {
            forTablets = args.getBoolean(FOR_TABLETS_EXTRA);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new PlatformVersionsAdapter(getLayoutInflater(), this);
        presenter = new MasterPresenterImplementation(getActivity().getSupportFragmentManager(), forTablets);

        if(savedInstanceState != null) {
            listAdapter.setSelectedItemPosition(savedInstanceState.getInt(SELECTED_INDEX, -1));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        rvPlatformVersions = view.findViewById(R.id.rv_platform_versions);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPlatformVersions.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvPlatformVersions.setAdapter(listAdapter);

        presenter.onAttach(this);
    }

    @Override
    public void showList(List<PlatformVersion> list) {
        listAdapter.setItems(list);
    }

    @Override
    public void updateListItem(PlatformVersion item) {
        listAdapter.updateItem(item);
    }

    @Override
    public void deleteItem(PlatformVersion item) {
        listAdapter.removeItem(item);
    }

    @Override
    public int getContainerId() {
        return R.id.fl_container;
    }

    @Override
    public int getDetailContainerId() {
        return R.id.fl_detail_container;
    }

    @Override
    public void onItemClick(int position) {
        PlatformVersion item = listAdapter.getItem(position);
        if(forTablets) {
            listAdapter.setSelectedItem(item);
        }
        presenter.goToDetailScreen(item);
    }

    @Override
    public void onItemLongClick(int position) {
        showDeleteAlertDialog(listAdapter.getItem(position));
    }

    private void showDeleteAlertDialog(final PlatformVersion item) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Warning! Do you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.deleteItem(item);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_INDEX, listAdapter.getSelectedItemPosition());
    }
}
