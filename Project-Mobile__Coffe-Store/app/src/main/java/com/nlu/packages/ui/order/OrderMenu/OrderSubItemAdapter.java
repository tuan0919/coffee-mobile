package com.nlu.packages.ui.order.OrderMenu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.CartActivity;
import com.nlu.packages.R;
import com.nlu.packages.ui.order.OrderProduct.ProductWithType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderSubItemAdapter extends RecyclerView.Adapter<OrderSubItemAdapter.ViewHolder>{
    Context context;
    ArrayList<OrderMenuCategoryItem> orderMenuCategoryItems;
    private final OrderMenuRvInterface orderMenuRvInterface;

    public OrderSubItemAdapter(Context context, ArrayList<OrderMenuCategoryItem> orderMenuCategoryItems, OrderMenuRvInterface orderMenuRvInterface) {
        this.context = context;
        this.orderMenuCategoryItems = orderMenuCategoryItems;
        this.orderMenuRvInterface = orderMenuRvInterface;
    }

    @NonNull
    @Override
    public OrderSubItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_list_sub_item, parent, false);

        return new ViewHolder(view, orderMenuRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSubItemAdapter.ViewHolder holder, int position) {
        OrderMenuCategoryItem orderMenuCategoryItem = orderMenuCategoryItems.get(position);

        Picasso.get().load(orderMenuCategoryItem.getImageUrl()).into(holder.productImage);
        holder.productName.setText(orderMenuCategoryItem.getProductName());

    }

    @Override
    public int getItemCount() {
        return orderMenuCategoryItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        CircleImageView productImage;
        public ViewHolder(@NonNull View itemView, OrderMenuRvInterface orderMenuRvInterface) {
            super(itemView);

            productImage = itemView.findViewById(R.id.orderMenuSubImage);
            productName = itemView.findViewById(R.id.orderMenuSubProductName);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(orderMenuRvInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            orderMenuRvInterface.onClickedMenuCategory(position);
                        }
                    }
                }
            });
        }
    }
}
