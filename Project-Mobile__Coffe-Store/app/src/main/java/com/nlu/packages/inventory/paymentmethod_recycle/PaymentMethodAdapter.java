package com.nlu.packages.inventory.paymentmethod_recycle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodViewHolder> {
    Activity context;
    List<PaymentMethodText> list;
    int selectedPosition = -1;
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
        // Kiểm tra nếu vị trí của phần tử hiện tại là phần tử được chọn
        if (selectedPosition == position) {
            int color1 = ContextCompat.getColor(holder.itemView.getContext(), R.color.Secondary);
            holder.paymentMethodButton.setBackgroundColor(color1);
            int color2 = ContextCompat.getColor(holder.itemView.getContext(), R.color.Primary);
            holder.paymentMethodButton.setTextColor(color2);
        } else {
            // Đặt lại màu cho các phần tử khác
            holder.paymentMethodButton.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.black_input_white_stroke));
            int defaultTextColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.Greyscale1);
            holder.paymentMethodButton.setTextColor(defaultTextColor);
        }

        holder.paymentMethodButton.setText(list.get(position).getText());
        holder.paymentMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật vị trí của phần tử được chọn
                selectedPosition = position;
                // Thông báo cho adapter để cập nhật lại các phần tử
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
