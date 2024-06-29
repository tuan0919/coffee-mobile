package com.nlu.packages.ui.detailstore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nlu.packages.R;

import java.util.List;

public class StoreAdapter extends ArrayAdapter<OurData> {
    private final Context context;
    private final List<OurData> list;

    public StoreAdapter(Context context, List<OurData> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        OurData item = list.get(position);

        ImageView imageView = convertView.findViewById(R.id.images);
        TextView title = convertView.findViewById(R.id.title);
        TextView desc = convertView.findViewById(R.id.desc);
        TextView price = convertView.findViewById(R.id.price);

        title.setText(list.get(position).getTitle());
        price.setText(list.get(position).getPrice());
        desc.setText(list.get(position).getDescription());
        imageView.setImageResource(list.get(position).getImage());

        return convertView;

    }

}
