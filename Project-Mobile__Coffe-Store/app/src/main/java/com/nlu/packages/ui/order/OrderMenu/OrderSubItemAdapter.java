package com.nlu.packages.ui.order.OrderMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderSubItemAdapter extends RecyclerView.Adapter<OrderSubItemAdapter.ViewHolder> {

    ArrayList<OrderMenuProductItem> orderMenuProductItems;

    public OrderSubItemAdapter(ArrayList<OrderMenuProductItem> orderMenuProductItems) {
        this.orderMenuProductItems = orderMenuProductItems;
    }

    @NonNull
    @Override
    public OrderSubItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_list_sub_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSubItemAdapter.ViewHolder holder, int position) {
        OrderMenuProductItem orderMenuProductItem = orderMenuProductItems.get(position);

        Picasso.get().load(orderMenuProductItem.imageUrl).into(holder.productImage);
        holder.productName.setText(orderMenuProductItem.productName);

    }

    @Override
    public int getItemCount() {
        return orderMenuProductItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        CircleImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.orderMenuSubImage);
            productName = itemView.findViewById(R.id.orderMenuSubProductName);
        }
    }
}
