package com.nlu.packages.inventory.checkout_recycle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckOutSummaryAdapter extends RecyclerView.Adapter<CheckOutSummaryViewHolder> {
    Activity context;
    List<CartResponseDTO.CartItemDTO> lists;

    public CheckOutSummaryAdapter(Activity context, List<CartResponseDTO.CartItemDTO> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public CheckOutSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckOutSummaryViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.summary_checkout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutSummaryViewHolder holder, int position) {
        holder.nameView.setText(lists.get(position).getProduct().getName());
        holder.priceView.setText(Double.toString(lists.get(position).getPrice()));
        holder.quantityView.setText("X "+lists.get(position).getQuantity());
        Picasso.get().load(lists.get(position).getProduct().getAvatar()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
