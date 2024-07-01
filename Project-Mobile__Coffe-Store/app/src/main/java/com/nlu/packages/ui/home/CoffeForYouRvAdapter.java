package com.nlu.packages.ui.home;

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
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//class nầy để tạo 1 recycle view (được gọi là adapter), được dùng để lấy dữ liệu lên trên màn hình,
//là phần code có thể mở rộng, nó là phần hỗ trợ giao diện cho mục Coffee for you trên màn hình Home
class CoffeForYouRvAdapter extends RecyclerView.Adapter<CoffeForYouRvAdapter.MyHolder> {
    Context context;
    List<ProductResponseDTO> data;
    private final CoffeeForYouRvInterface coffeeForYouRvInterface;

    public CoffeForYouRvAdapter(Context context, ArrayList<ProductResponseDTO> data, CoffeeForYouRvInterface coffeeForYouRvInterface) {
        this.context = context;
        this.data = data != null ? data : new ArrayList<>();
        this.coffeeForYouRvInterface = coffeeForYouRvInterface;
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
        holder.textView1.setText(data.get(position).getProductName());
        Picasso.get().load(data.get(position).getAvatar()).into(holder.imageView1);

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

    //khai báo textview vói image view để chứa hình ảnh với chữ
    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;
        ToggleButton toggleButton;

        //set lại nôi dung của hình ảnh với chữ
        public MyHolder(@NonNull View itemView, CoffeeForYouRvInterface coffeeForYouRvInterface) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.homeCoffeeTitleRv);
            imageView1 = itemView.findViewById(R.id.homeCoffeeImageRv);
            toggleButton = itemView.findViewById(R.id.coffeeForYouFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (coffeeForYouRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            coffeeForYouRvInterface.onItemClickCoffeeForYou(position);
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
