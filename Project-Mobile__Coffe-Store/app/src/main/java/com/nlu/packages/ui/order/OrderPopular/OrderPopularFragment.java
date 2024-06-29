package com.nlu.packages.ui.order.OrderPopular;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nlu.packages.CartActivity;
import com.nlu.packages.R;

import java.util.ArrayList;

public class OrderPopularFragment extends Fragment implements TrendThisMonthRvInterface, PopularDrinksRvInterface {
    //data source
    ArrayList<String> trendThisMonthDataSource, popularDrinksDataSource;

    //adapter
    TrendThisMonthRvAdapter trendThisMonthRvAdapter;
    PopularDrinksRvAdapter popularDrinksRvAdapter;

    //recycle view
    RecyclerView trendThisMonthRv, popularDrinksRv;

    //layout manager
    RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_popular, container, false);
        // Inflate the layout for this fragment

        //setting for `trend this month` recycle view
        trendThisMonthRv = view.findViewById(R.id.trendThisMonthRv);

        //setting the data source for `trend this month`
        trendThisMonthDataSource = new ArrayList<>();
        trendThisMonthDataSource.add("Expesso");
        trendThisMonthDataSource.add("Cappuccino");
        trendThisMonthDataSource.add("Latte");
        trendThisMonthDataSource.add("Mocha");

        //setting the `trend this month` adapter
        linearLayoutManager = new LinearLayoutManager(OrderPopularFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        trendThisMonthRvAdapter = new TrendThisMonthRvAdapter(this.getContext(), trendThisMonthDataSource, this);
        trendThisMonthRv.setLayoutManager(linearLayoutManager);
        trendThisMonthRv.setAdapter(trendThisMonthRvAdapter);

        //setting for `polular drink` recycle view
        popularDrinksRv = view.findViewById(R.id.popularDrinksRv);
        layoutManager = new GridLayoutManager(this.getContext(), 2);
        popularDrinksRv.setLayoutManager(layoutManager);

        //setting the data source for `polular drink`
        popularDrinksDataSource = new ArrayList<>();
        popularDrinksDataSource.add("Expesso");
        popularDrinksDataSource.add("Cappuccino");
        popularDrinksDataSource.add("Latte");
        popularDrinksDataSource.add("Mocha");

        //setting the `polular drink` adapter
        popularDrinksRvAdapter = new PopularDrinksRvAdapter(this.getContext(), popularDrinksDataSource, this);
        popularDrinksRv.setAdapter(popularDrinksRvAdapter);
        popularDrinksRv.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onItemClickPopularDrinks(int position) {
        Intent intent = new Intent(OrderPopularFragment.this.getContext(), CartActivity.class);

        intent.putExtra("Name", trendThisMonthDataSource.get(position));

        startActivity(intent);
    }

    @Override
    public void onItemClickTrendThisMonth(int position) {
        Intent intent = new Intent(OrderPopularFragment.this.getContext(), CartActivity.class);

        intent.putExtra("Name", trendThisMonthDataSource.get(position));

        startActivity(intent);
    }
}