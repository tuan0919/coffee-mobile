package com.nlu.packages.inventory.paymentmethod_recycle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodViewHolder> {
    Activity context;
    List<PaymentMethodText> list;

    public PaymentMethodAdapter(Activity context, List<PaymentMethodText> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentMethodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_method_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder holder, int position) {
        holder.paymentMethodText.setText(list.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
