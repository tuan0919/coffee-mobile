package com.nlu.packages.ui.order.OrderPopular;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.cart.CartActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderPopularFragment extends Fragment implements TrendThisMonthRvInterface, PopularDrinksRvInterface {
    //data source
    private List<ProductResponseDTO> trendThisMonthDataSource, popularDrinksDataSource = new ArrayList<>();

    //adapter
    TrendThisMonthRvAdapter trendThisMonthRvAdapter;
    PopularDrinksRvAdapter popularDrinksRvAdapter;

    //recycle view
    RecyclerView trendThisMonthRv, popularDrinksRv;

    //layout manager
    RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    CoffeeApi coffeeApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_popular, container, false);
        // Inflate the layout for this fragment

        //setting for `trend this month` recycle view
        trendThisMonthRv = view.findViewById(R.id.trendThisMonthRv);

        //get data from api
        getListCoffee();

        //setting the `trend this month` adapter
        linearLayoutManager = new LinearLayoutManager(OrderPopularFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        trendThisMonthRvAdapter = new TrendThisMonthRvAdapter(this.getContext(), (ArrayList<ProductResponseDTO>) trendThisMonthDataSource, this);
        trendThisMonthRv.setLayoutManager(linearLayoutManager);
        trendThisMonthRv.setAdapter(trendThisMonthRvAdapter);

        //setting grid layout for `polular drink` recycle view
        popularDrinksRv = view.findViewById(R.id.popularDrinksRv);
        layoutManager = new GridLayoutManager(this.getContext(), 2);
        popularDrinksRv.setLayoutManager(layoutManager);

        //setting the `polular drink` adapter
        popularDrinksRvAdapter = new PopularDrinksRvAdapter(this.getContext(), (ArrayList<ProductResponseDTO>) popularDrinksDataSource, this);
        popularDrinksRv.setAdapter(popularDrinksRvAdapter);
        popularDrinksRv.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onItemClickPopularDrinks(int position) {
        Intent intent = new Intent(OrderPopularFragment.this.getContext(), CartActivity.class);

        intent.putExtra("ProductOrder", popularDrinksDataSource.get(position));

        startActivity(intent);
    }

    @Override
    public void onItemClickTrendThisMonth(int position) {
        Intent intent = new Intent(OrderPopularFragment.this.getContext(), CartActivity.class);

        intent.putExtra("ProductOrder", trendThisMonthDataSource.get(position));


        startActivity(intent);
    }
    public void getListCoffee(){
        coffeeApi = CoffeeService.getClient();
        Call<List<ProductResponseDTO>> call = coffeeApi.getAllProduct();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                if (response.isSuccessful()) {
                    //get response data for `popular drinks`
                    List<ProductResponseDTO> responseDTOS = response.body();
                    popularDrinksDataSource = responseDTOS;

                    //get response data for `trend this month`
                    trendThisMonthDataSource = responseDTOS;

                    //update `popular drinks` adapter
                    popularDrinksRvAdapter.updateData(responseDTOS);

                    //shuffle the data
                    Collections.shuffle(responseDTOS);

                    //update `trend this month` adapter
                    trendThisMonthRvAdapter.updateData(responseDTOS);

                } else {
                    System.out.println("lỗi lấy data");
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

}