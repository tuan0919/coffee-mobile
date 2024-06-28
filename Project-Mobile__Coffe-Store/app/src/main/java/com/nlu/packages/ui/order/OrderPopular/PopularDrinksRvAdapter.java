package com.nlu.packages.ui.order.OrderPopular;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularDrinksRvAdapter extends RecyclerView.Adapter<PopularDrinksRvAdapter.ViewHolder> {

    Context context;
    ArrayList<String> data;
    private final PopularDrinksRvInterface popularDrinksRvInterface;

    public PopularDrinksRvAdapter(Context context, ArrayList<String> data, PopularDrinksRvInterface popularDrinksRvInterface) {
        this.context = context;
        this.data = data;
        this.popularDrinksRvInterface = popularDrinksRvInterface;
    }

    @NonNull
    @Override
    public PopularDrinksRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_drink_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, popularDrinksRvInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularDrinksRvAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        Picasso.get().
                load("https://media.istockphoto.com/id/1358132613/photo/refreshing-hot-cup-of-coffee-at-a-cafe.jpg?s=612x612&w=0&k=20&c=ObwIF28Vt3k93Nch9U4QYUdOwMA_eiMwVVCvKbypnNc=").
                into(holder.imageView);

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
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ToggleButton toggleButton;

        //set lại nôi dung của hình ảnh với chữ
        public ViewHolder(@NonNull View itemView, PopularDrinksRvInterface popularDrinksRvInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.popularDrinkImageRv);
            textView = itemView.findViewById(R.id.popularDrinkTitleRv);
            toggleButton = itemView.findViewById(R.id.popularDrinkFavorite);

            //xử lý sự kiện khi và 1 hình ảnh được nhấn vào sẽ chuyển qua trang chi tiết sản phẩm
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (popularDrinksRvInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            popularDrinksRvInterface.onItemClickPopularDrinks(position);
                        }
                    }
                }
            });
        }
    }
}
