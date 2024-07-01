package com.nlu.packages.inventory.store_recycle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.nlu.packages.response_dto.order.OrderResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreViewHolder> {
    Activity context;
    List<OrderResponseDTO.StoreDTO> list;

    public StoreAdapter(Activity context, List<OrderResponseDTO.StoreDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.store_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getAvatar()).into(holder.imageView);
        holder.nameStore.setText(list.get(position).getStoreName());
        holder.addressStore.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
