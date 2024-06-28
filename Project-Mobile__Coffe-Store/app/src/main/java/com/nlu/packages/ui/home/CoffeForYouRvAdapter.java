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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//class nầy để tạo 1 recycle view (được gọi là adapter), được dùng để lấy dữ liệu lên trên màn hình,
//là phần code có thể mở rộng, nó là phần hỗ trợ giao diện cho mục Coffee for you trên màn hình Home
class CoffeForYouRvAdapter extends RecyclerView.Adapter<CoffeForYouRvAdapter.MyHolder> {
    private final CoffeeForYouRvInterface coffeeForYouRvInterface;
    ArrayList<String> data;
    Context context;

    public CoffeForYouRvAdapter(Context context, ArrayList<String> data, CoffeeForYouRvInterface coffeeForYouRvInterface) {
        this.context=context;
        this.data = data;
        this.coffeeForYouRvInterface=coffeeForYouRvInterface;
    }

    //khỏi tạo view holder, để hiển thị giao diện lên fragment gọi nó
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_for_you_rv, null, false);
        return new MyHolder(view, coffeeForYouRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView1.setText(data.get(position));
        Picasso.get().load("https://media.istockphoto.com/id/1358132613/photo/refreshing-hot-cup-of-coffee-at-a-cafe.jpg?s=612x612&w=0&k=20&c=ObwIF28Vt3k93Nch9U4QYUdOwMA_eiMwVVCvKbypnNc=").into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //khai báo textview vói image view để chứa hình ảnh với chữ
    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, CoffeeForYouRvInterface coffeeForYouRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.homeCoffeeTitleRv);
            imageView1 = itemView.findViewById(R.id.homeCoffeeImageRv);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(coffeeForYouRvInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            coffeeForYouRvInterface.onItemClickCoffeeForYou(position);
                        }
                    }
                }
            });
        }
    }
}