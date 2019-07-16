package com.example.android_version_viewer.ui.master;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_version_viewer.R;
import com.example.android_version_viewer.data.db.model.PlatformVersion;

import java.util.ArrayList;
import java.util.List;

public class PlatformVersionsAdapter extends RecyclerView.Adapter<PlatformVersionsAdapter.PlatformVersionViewHolder> {

    private final List<PlatformVersion> items;
    private final LayoutInflater inflater;
    private final RecyclerClickListener recyclerClickListener;

    private int selectedItemPosition = -1;

    PlatformVersionsAdapter(LayoutInflater inflater, RecyclerClickListener recyclerClickListener) {
        this.items = new ArrayList<>();
        this.inflater = inflater;
        this.recyclerClickListener = recyclerClickListener;
    }

    @NonNull
    @Override
    public PlatformVersionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_platform_version, parent, false);
        return new PlatformVersionViewHolder(view, recyclerClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformVersionViewHolder holder, int position) {
        holder.bind(items.get(position), isItemSelected(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    PlatformVersion getItem(int position) {
        return items.get(position);
    }

    void setItems(List<PlatformVersion> items) {
        this.items.clear();;
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    void updateItem(PlatformVersion item) {
        int index = items.indexOf(item);
        if(index != -1) {
            notifyItemChanged(index);
        }
    }

    void removeItem(PlatformVersion item) {
        int index = items.indexOf(item);
        if(index != -1) {
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    void setSelectedItem(PlatformVersion item) {
        int index = items.indexOf(item);
        if(index != -1) {
            selectedItemPosition = index;
            notifyDataSetChanged();
        }
    }

    void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }
    int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    private boolean isItemSelected(int position) {
        return selectedItemPosition != -1 && selectedItemPosition == position;
    }

    static class PlatformVersionViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener,
            View.OnLongClickListener {

        private TextView tvVersion;
        private TextView tvName;
        private Button btnFavourite;

        private final RecyclerClickListener recyclerClickListener;

        PlatformVersionViewHolder(@NonNull View itemView, RecyclerClickListener recyclerClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvVersion = itemView.findViewById(R.id.tv_version);
            tvName = itemView.findViewById(R.id.tv_name);
            btnFavourite = itemView.findViewById(R.id.btn_favourite);

            this.recyclerClickListener = recyclerClickListener;
        }

        void bind(PlatformVersion item, boolean isSelected) {
            itemView.setBackgroundColor(isSelected ? Color.LTGRAY : Color.TRANSPARENT);
            tvVersion.setText(item.getVersion());
            tvName.setText(item.getName());
            btnFavourite.setSelected(item.getFavourite());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                if(recyclerClickListener != null) {
                    recyclerClickListener.onItemClick(position);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                if(recyclerClickListener != null) {
                    recyclerClickListener.onItemLongClick(position);
                    return true;
                }
            }
            return false;
        }
    }
}
