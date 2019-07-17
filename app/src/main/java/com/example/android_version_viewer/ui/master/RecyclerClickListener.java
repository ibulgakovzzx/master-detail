package com.example.android_version_viewer.ui.master;

import android.view.View;

public interface RecyclerClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
