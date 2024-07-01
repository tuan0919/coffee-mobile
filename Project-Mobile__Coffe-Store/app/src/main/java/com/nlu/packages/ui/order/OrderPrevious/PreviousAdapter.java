package com.nlu.packages.ui.order.OrderPrevious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.product.ProductResponseDTO;

import java.util.ArrayList;

public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyHolder> {
    Context context;
    ArrayList<ProductResponseDTO> data;
    private final PreviousAdapterInterface previousAdapterInterface;

    public PreviousAdapter(Context context, ArrayList<ProductResponseDTO> data, PreviousAdapterInterface previousAdapterInterface) {
        this.context = context;
        this.data = data;
        this.previousAdapterInterface = previousAdapterInterface;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previous_order, parent, false);
        return new MyHolder(view, previousAdapterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        holder.itemView.setOnClickListener(view -> {
            if (previousAdapterInterface != null) {
                previousAdapterInterface.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView nameProduct;
        TextView priceTitle;
        ImageView imageView1;
        Button reorderButton;

        public MyHolder(@NonNull View itemView, PreviousAdapterInterface previousAdapterInterface) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.orderTitle);
            imageView1 = itemView.findViewById(R.id.orderImage);
            reorderButton = itemView.findViewById(R.id.reorderButton);
            priceTitle = itemView.findViewById(R.id.priceTitle);
            itemView.setOnClickListener(view -> {
                if (previousAdapterInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        previousAdapterInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
