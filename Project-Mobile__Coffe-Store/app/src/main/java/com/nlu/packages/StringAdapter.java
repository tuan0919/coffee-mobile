package com.nlu.packages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StringAdapter extends ArrayAdapter<String> {

    public StringAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected,parent,false);
        TextView tvCategory=convertView.findViewById(R.id.img_dropdown);

        String category= this.getItem(position);
        if(category != null){
            tvCategory.setText(category);
        }

        return convertView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tvCategory=convertView.findViewById(R.id.text_view_category);

        String category= this.getItem(position);
        if(category != null){
            tvCategory.setText(category);

        }

        return convertView;
    }
}
