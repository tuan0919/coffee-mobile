package com.nlu.packages.inventory.paymentmethod_recycle;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;

public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
    AppCompatButton paymentMethodButton;

    public PaymentMethodViewHolder(@NonNull View itemView) {
        super(itemView);
        this.paymentMethodButton = itemView.findViewById(R.id.methodPayOptionButton);
    }
}
