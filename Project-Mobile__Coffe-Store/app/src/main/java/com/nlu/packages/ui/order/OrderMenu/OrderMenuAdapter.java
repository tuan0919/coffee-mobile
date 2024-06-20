package com.nlu.packages.ui.order.OrderMenu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;

import java.util.ArrayList;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.ViewHolder> {

    private Activity activity;
    ArrayList<OrderMenuCategoryItem> orderMenuCategoryItems;
    ArrayList<OrderMenuProductItem> orderMenuProductItems;

    public OrderMenuAdapter(Activity activity, ArrayList<OrderMenuCategoryItem> orderMenuCategoryItems, ArrayList<OrderMenuProductItem> orderMenuProductItems) {
        this.activity = activity;
        this.orderMenuCategoryItems = orderMenuCategoryItems;
        this.orderMenuProductItems = orderMenuProductItems;
    }

    @NonNull
    @Override
    public OrderMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_list_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuAdapter.ViewHolder holder, int position) {
        OrderMenuCategoryItem orderMenuCategoryItem = orderMenuCategoryItems.get(position);

        holder.productCategory.setText(orderMenuCategoryItem.categoyName);

        OrderSubItemAdapter orderSubItemAdapter = new OrderSubItemAdapter(orderMenuProductItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.nestRv.setLayoutManager(linearLayoutManager);
        holder.nestRv.setAdapter(orderSubItemAdapter);
    }

    @Override
    public int getItemCount() {
        return orderMenuCategoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productCategory;
        RecyclerView nestRv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productCategory = itemView.findViewById(R.id.typeProductOrderMenu);
            nestRv = itemView.findViewById(R.id.orderMenuSubRecycleView);
        }
    }
}
