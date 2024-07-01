package com.nlu.packages.ui.order.OrderPrevious;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.nlu.packages.R;
import com.nlu.packages.ui.cart.CartActivity;

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
