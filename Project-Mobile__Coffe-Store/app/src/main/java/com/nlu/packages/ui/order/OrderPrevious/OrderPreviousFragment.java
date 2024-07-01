package com.nlu.packages.ui.order.OrderPrevious;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.R;

import java.util.ArrayList;

public class OrderPreviousFragment extends Fragment implements PreviousAdapterInterface {

    ArrayList<OrderItem> todayDataSource, wednesdayDataSource;
    PreviousAdapter todayAdapter, wednesdayAdapter;
    RecyclerView todayRV, wednesdayRV;
    LinearLayoutManager todayLayoutManager;
    LinearLayoutManager wednesdayLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_previous, container, false);

        todayRV = view.findViewById(R.id.todayRV);
        wednesdayRV = view.findViewById(R.id.wedRV);

        // Initialize the data sources with sample data
        todayDataSource = new ArrayList<>();
        todayDataSource.add(new OrderItem("Chocolate Donut", "$3.90", R.drawable.black_coffee));
        todayDataSource.add(new OrderItem("Iced Mocha", "$2.90", R.drawable.black_coffee));
        todayDataSource.add(new OrderItem("Chocolate Donut", "$3.90", R.drawable.black_coffee));
        todayDataSource.add(new OrderItem("Iced Mocha", "$2.90", R.drawable.black_coffee));
        wednesdayDataSource = new ArrayList<>();
        wednesdayDataSource.add(new OrderItem("Cappuccino", "$2.45", R.drawable.black_coffee));
        wednesdayDataSource.add(new OrderItem("Coffee Cake", "$3.90", R.drawable.black_coffee));
        todayDataSource.add(new OrderItem("Chocolate Donut", "$3.90", R.drawable.black_coffee));
        todayDataSource.add(new OrderItem("Iced Mocha", "$2.90", R.drawable.black_coffee));
        // Set up the adapters and layout managers
        todayAdapter = new PreviousAdapter(this.getContext(), todayDataSource, this);
        todayLayoutManager = new LinearLayoutManager(OrderPreviousFragment.this.getContext());
        todayRV.setLayoutManager(todayLayoutManager);
        todayRV.setAdapter(todayAdapter);

        wednesdayAdapter = new PreviousAdapter(this.getContext(), wednesdayDataSource, this);
        wednesdayLayoutManager = new LinearLayoutManager(OrderPreviousFragment.this.getContext());
        wednesdayRV.setLayoutManager(wednesdayLayoutManager);
        wednesdayRV.setAdapter(wednesdayAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(OrderPreviousFragment.this.getContext(), CartActivity.class);
        startActivity(intent);
    }
}
