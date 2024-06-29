package com.nlu.packages.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;
import com.nlu.packages.dto.response.product.ProductResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//class nầy để tạo 1 recycle view, để lấy dữ liệu lên trên màn hình, là phần code có thể mở rộng, nó là phần hỗ
//trợ hiển thị giao diện cho phần Top Pick Coffee trên màn hình Home
class TopPickRvAdapter extends RecyclerView.Adapter<TopPickRvAdapter.MyHolder> {
    private final TopCoffeeRvInterface topCoffeeRvInterface;
    ArrayList<ProductResponseDTO> data;
    Context context;

    public TopPickRvAdapter(Context context, ArrayList<ProductResponseDTO> data, TopCoffeeRvInterface topCoffeeRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.topCoffeeRvInterface = topCoffeeRvInterface;
    }

    //khỏi tạo view holder, để hiển thị giao diện lên fragment gọi nó
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_pick_rv, null, false);
        return new MyHolder(view, topCoffeeRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView2.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //khai báo textview vói image view để chứa hình ảnh với chữ
    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView textView2;
        ImageView imageView2;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, TopCoffeeRvInterface topCoffeeRvInterface) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.topPickTitleRv);
            imageView2 = itemView.findViewById(R.id.topPickImageRv);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(topCoffeeRvInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            topCoffeeRvInterface.onItemClickTopCoffee(position);
                        }
                    }
                }
            });
        }
    }
    public void updateData(List<ProductResponseDTO> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }
}
