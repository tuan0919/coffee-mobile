package com.nlu.packages.ui.order.OrderMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.nlu.packages.dto.response.product.ProductResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderMenuRvAdapter extends RecyclerView.Adapter<OrderMenuRvAdapter.ViewHolder>{

    private Context context;
    List<ProductResponseDTO> data;
    private final OrderMenuRvInterface orderMenuRvInterface;

    public OrderMenuRvAdapter(Context context, List<ProductResponseDTO> data, OrderMenuRvInterface orderMenuRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.orderMenuRvInterface = orderMenuRvInterface;
    }

    @NonNull
    @Override
    public OrderMenuRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_item_rv,
                parent, false);
        return new ViewHolder(view, orderMenuRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuRvAdapter.ViewHolder holder, int position) {
        holder.productName.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView);

        //xử lý sự kiện cho `add to favorite`
        holder.toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView imageView;
        ToggleButton toggleButton;

        public ViewHolder(@NonNull View itemView, OrderMenuRvInterface orderMenuRvInterface) {
            super(itemView);

            productName = itemView.findViewById(R.id.orderMenuProductName);
            imageView = itemView.findViewById(R.id.orderMenuImageRv);
            toggleButton = itemView.findViewById(R.id.orderMenuFavorite);
        }
    }

    public void updateData(List<ProductResponseDTO> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }
}
