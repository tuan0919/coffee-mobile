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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_previous, container, false);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(OrderPreviousFragment.this.getContext(), CartActivity.class);
        startActivity(intent);
    }
}
