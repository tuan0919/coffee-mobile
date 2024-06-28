package com.nlu.packages.ui.order.OrderFavorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderPrevious.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderFavoriteAdapter extends RecyclerView.Adapter<OrderFavoriteAdapter.MyHolder> {
    Context context;
    ArrayList<OrderItem> data;

    public OrderFavoriteAdapter(Context context, ArrayList<OrderItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_order, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        OrderItem currentItem = data.get(position);
        holder.nameProduct.setText(currentItem.getName());
        holder.priceTitle.setText(currentItem.getPrice());
        holder.imageView1.setImageResource(currentItem.getImageResourceId());
        //  su kien click favoriteButton
        holder.favoriteButton.setOnClickListener(view -> {
            Toast.makeText(context, "Removed " + currentItem.getName() + " from favorites", Toast.LENGTH_SHORT).show();
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView nameProduct;
        TextView priceTitle;
        ImageView imageView1;
        ImageView favoriteButton;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.orderTitle);
            imageView1 = itemView.findViewById(R.id.orderImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            priceTitle = itemView.findViewById(R.id.priceTitle);
        }
    }
}
