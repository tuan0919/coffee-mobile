package com.nlu.packages.inventory.size;

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

public class SizeAdapter extends ArrayAdapter<SizeType> {
    public SizeAdapter(@NonNull Context context, int resource, @NonNull List<SizeType> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.text_selected);
        SizeType st = this.getItem(position);
        if(st!=null){
            tvSelected.setText(st.stringEPS());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView tvCategory = convertView.findViewById(R.id.text_view_category);
        SizeType st = this.getItem(position);
        if(st!=null){
            tvCategory.setText(st.stringEPS());
        }
        return convertView;
    }
}
