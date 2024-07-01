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
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductSearchRvAdapter extends RecyclerView.Adapter<ProductSearchRvAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductResponseDTO> data;
    private final ProductSearchRvInterface productSearchRvInterface;

    public ProductSearchRvAdapter(Context context, ArrayList<ProductResponseDTO> data, ProductSearchRvInterface productSearchRvInterface) {
        this.context = context;
        this.data = data;
        this.productSearchRvInterface = productSearchRvInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_with_category_rv, null, false);
        return new ViewHolder(view, productSearchRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchRvAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(data.get(position).getProductName());
        Picasso.get().
                load(data.get(position).getAvatar())
                .into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;

        //set lại nôi dung của hình ảnh với chữ
        public ViewHolder(@NonNull View itemView, ProductSearchRvInterface productSearchRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.productWithCategoryRvTitle);
            imageView1 = itemView.findViewById(R.id.productWithCategoryRvImage);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productSearchRvInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            productSearchRvInterface.onItemClickProductWithCategory(position);
                        }
                    }
                }
            });
        }
    }
}
