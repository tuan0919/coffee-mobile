package com.nlu.packages.ui.order.OrderFavorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlu.packages.R;

import com.nlu.packages.ui.order.OrderPrevious.OrderItem;


import java.util.ArrayList;

public class OrderFavoriteFragment extends Fragment {

    ArrayList<OrderItem> favoritesDataSource;
    OrderFavoriteAdapter favoritesAdapter;
    RecyclerView favoritesRecyclerView;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_favorites, container, false);

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);

        // Initialize the data source with sample data
        favoritesDataSource = new ArrayList<>();
        favoritesDataSource.add(new OrderItem("Iced Coffee", "$2.60", R.drawable.black_coffee));
        favoritesDataSource.add(new OrderItem("Vanilla Latte", "$2.45", R.drawable.milk_coffee));
        favoritesDataSource.add(new OrderItem("Chocolate Donut", "$3.90", R.drawable.soft_coffee));
        favoritesDataSource.add(new OrderItem("Expresso", "$2.00", R.drawable.black_coffee));

        // Set up the adapter and layout manager
        favoritesAdapter = new OrderFavoriteAdapter(this.getContext(), favoritesDataSource);
        gridLayoutManager = new GridLayoutManager(OrderFavoriteFragment.this.getContext(), 2);
        favoritesRecyclerView.setLayoutManager(gridLayoutManager);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        return view;
    }
}
