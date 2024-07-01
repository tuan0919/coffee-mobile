package com.nlu.packages.inventory.store_recycle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;

public class StoreViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameStore,addressStore;

    public StoreViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageStore);
        nameStore = itemView.findViewById(R.id.storeName);
        addressStore = itemView.findViewById(R.id.storeAddress);
    }
}
