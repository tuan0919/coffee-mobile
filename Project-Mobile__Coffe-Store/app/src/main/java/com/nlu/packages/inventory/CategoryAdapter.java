package com.nlu.packages.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nlu.packages.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<AmountType> {


    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<AmountType> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.text_selected);
        AmountType at = this.getItem(position);
        if(at != null) {
            tvSelected.setText(at.getType());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView tvCategory = convertView.findViewById(R.id.text_view_category);
        AmountType at = this.getItem(position);
        if(at != null) {
            tvCategory.setText(at.getType());
        }
        return convertView;
    }
}
