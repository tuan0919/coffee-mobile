package com.nlu.packages.ui.order.OrderMenu;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.CartActivity;
import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderProduct.ProductWithType;

import java.util.ArrayList;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.ViewHolder> implements OrderMenuRvInterface{

    private Activity activity;
    ArrayList<OrderMenuTypeItem> orderMenuTypeItems;

    public OrderMenuAdapter(Activity activity, ArrayList<OrderMenuTypeItem> orderMenuTypeItems) {
        this.activity = activity;
        this.orderMenuTypeItems = orderMenuTypeItems;
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
        OrderMenuTypeItem orderMenuTypeItem = orderMenuTypeItems.get(position);

        holder.productCategory.setText(orderMenuTypeItem.categoyName);

        OrderSubItemAdapter orderSubItemAdapter = new OrderSubItemAdapter(this.activity, orderMenuTypeItem.getCategoryItems(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.nestRv.setLayoutManager(linearLayoutManager);
        holder.nestRv.setAdapter(orderSubItemAdapter);
    }

    @Override
    public int getItemCount() {
        return orderMenuTypeItems.size();
    }

    @Override
    public void onClickedMenuCategory(int position) {
        Intent intent = new Intent(OrderMenuAdapter.this.activity, ProductWithType.class);

        intent.putExtra("Name", orderMenuTypeItems.get(position).categoyName);

        activity.startActivity(intent);
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
