package com.nlu.packages.inventory.paymentmethod_recycle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;

public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
    TextView paymentMethodText;

    public PaymentMethodViewHolder(@NonNull View itemView) {
        super(itemView);
        this.paymentMethodText = itemView.findViewById(R.id.methodPayText);
    }
}
