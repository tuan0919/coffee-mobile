package com.nlu.packages.ui.order.OrderProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductWithCategoryRvAdapter extends RecyclerView.Adapter<ProductWithCategoryRvAdapter.ViewHolder> {
    Context context;
    ArrayList<String> data;
    private final ProductWithCategoryRvInterface productWithCategoryRvInterface;

    public ProductWithCategoryRvAdapter(Context context, ArrayList<String> data, ProductWithCategoryRvInterface productWithCategoryRvInterface) {
        this.context = context;
        this.data = data;
        this.productWithCategoryRvInterface = productWithCategoryRvInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_with_category_rv, null, false);
        return new ViewHolder(view, productWithCategoryRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductWithCategoryRvAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(data.get(position));
        Picasso.get().load("https://media.istockphoto.com/id/1358132613/photo/refreshing-hot-cup-of-coffee-at-a-cafe.jpg?s=612x612&w=0&k=20&c=ObwIF28Vt3k93Nch9U4QYUdOwMA_eiMwVVCvKbypnNc=").into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;

        //set lại nôi dung của hình ảnh với chữ
        public ViewHolder(@NonNull View itemView, ProductWithCategoryRvInterface productWithCategoryRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.productWithCategoryRvTitle);
            imageView1 = itemView.findViewById(R.id.productWithCategoryRvImage);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productWithCategoryRvInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            productWithCategoryRvInterface.onItemClickProductWithCategory(position);
                        }
                    }
                }
            });
        }
    }
}
