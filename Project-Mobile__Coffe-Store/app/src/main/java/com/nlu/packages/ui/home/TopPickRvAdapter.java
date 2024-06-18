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

class TopPickRvAdapter extends RecyclerView.Adapter<TopPickRvAdapter.MyHolder> {
    private final TopCoffeeRvInterface topCoffeeRvInterface;
    ArrayList<String> data;
    Context context;

    public TopPickRvAdapter(Context context, ArrayList<String> data, TopCoffeeRvInterface topCoffeeRvInterface) {
        this.context = context;
        this.data = data;
        this.topCoffeeRvInterface = topCoffeeRvInterface;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_pick_rv, null, false);
        return new MyHolder(view, topCoffeeRvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView2.setText(data.get(position));
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLxLKJHXvIkiVTP4oRp0WkgYGaTBtisWBc3mXdJFMv_6A5PHRl2AZbVaQpCdPNHyjlrAE&usqp=CAU").into(holder.imageView2);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView2;
        ImageView imageView2;

        public MyHolder(@NonNull View itemView, TopCoffeeRvInterface topCoffeeRvInterface) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.topPickTitleRv);
            imageView2 = itemView.findViewById(R.id.topPickImageRv);

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
}
