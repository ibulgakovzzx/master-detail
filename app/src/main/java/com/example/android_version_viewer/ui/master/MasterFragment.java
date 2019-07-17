package com.example.android_version_viewer.ui.master;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_version_viewer.R;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.List;

public class MasterFragment extends Fragment implements MasterView, RecyclerClickListener, View.OnClickListener {

    private static final String FOR_TABLETS_EXTRA = "for_tablets";
    private static final String SELECTED_INDEX_EXTRA = "selected_item_index";

    private RecyclerView rvPlatformVersions;
    private TextView tvListFilter;
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
        setHasOptionsMenu(true);

        if(savedInstanceState != null) {
            listAdapter.setSelectedItemPosition(savedInstanceState.getInt(SELECTED_INDEX_EXTRA, -1));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        rvPlatformVersions = view.findViewById(R.id.rv_platform_versions);
        tvListFilter = view.findViewById(R.id.tv_list_filter);

        tvListFilter.setOnClickListener(this);

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
    public void onClick(View view) {
        if(view.getId() == R.id.tv_list_filter) {
            PopupMenu popupMenu = new PopupMenu(getContext(), tvListFilter);
            popupMenu.inflate(R.menu.master_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_all:
                            presenter.filterItems(ListFilterType.ALL);
                            tvListFilter.setText(R.string.master_menu_all);
                            return true;
                        case R.id.menu_favorite:
                            presenter.filterItems(ListFilterType.FAVORITE);
                            tvListFilter.setText(R.string.master_menu_favorites);
                            return true;
                        case R.id.menu_distribution:
                            presenter.filterItems(ListFilterType.DISTRIBUTION);
                            tvListFilter.setText(R.string.master_menu_distribution);
                            return true;
                    }
                    return false;
                }
            });

            popupMenu.show();
        }
    }

    @Override
    public void showList(List<PlatformVersion> list) {
        listAdapter.setItems(list);
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
    public void onItemClick(View view, int position) {
        PlatformVersion item = listAdapter.getItem(position);
        if(view.getId() == R.id.btn_favourite) {
            presenter.setStartStatus(item);
        } else {
            if(forTablets) {
                listAdapter.setSelectedItem(item);
            }
            presenter.goToDetailScreen(item);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
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
        outState.putInt(SELECTED_INDEX_EXTRA, listAdapter.getSelectedItemPosition());
    }
}
