package com.nlu.packages.inventory.checkout_recycle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;

public class CheckOutSummaryViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView,priceView,quantityView;
    public CheckOutSummaryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_image);
        nameView = itemView.findViewById(R.id.productName);
        priceView = itemView.findViewById(R.id.productPrice);
        quantityView = itemView.findViewById(R.id.quantity);
    }
}
