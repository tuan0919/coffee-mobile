package com.nlu.packages.ui.order.OrderFavorite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderFavoriteFragment extends Fragment implements OrderFavoriteRvInterface {
    private RecyclerView.LayoutManager layoutManager;
    private OrderFavoriteAdapter orderFavoriteAdapter;
    private RecyclerView orderFavoriteRv;
    private CoffeeApi coffeeApi;
    private List<ProductResponseDTO> dataSource = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_favorites, container, false);

        //order menu recyclerview
        orderFavoriteRv = view.findViewById(R.id.favoritesRecyclerView);

        //setting the data source
        orderFavoriteAdapter = new OrderFavoriteAdapter(this.getContext(), dataSource, this);
        orderFavoriteRv.setAdapter(orderFavoriteAdapter);
        orderFavoriteRv.setHasFixedSize(true);

        //setting grid layout
        layoutManager = new GridLayoutManager(this.getContext(), 2);
        orderFavoriteRv.setLayoutManager(layoutManager);

        //fetching api
        getWishList();

        return view;
    }

    //khởi tạo coffee api có kèm thêm token
    private void initCoffeeApi() {
        String token = MyUtils.get(this.getContext(), "token");
        if (token == null || !token.contains(".")) {
            Log.e("CoffeForYouRvAdapter", "Token is invalid: " + token);
            return;
        }
        coffeeApi = CoffeeService.getRetrofitInstance(token);
    }

    public void getWishList() {
        initCoffeeApi();
        Call<List<ProductResponseDTO>> call = coffeeApi.getWishList();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                if (response.isSuccessful()) {
                    //get the response
                    dataSource = response.body();
                    orderFavoriteAdapter.updateData(dataSource);
                } else {
                    onFailure(call, new Throwable("Response is not successful"));
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Override
    public void onClickedFavoriteItem(int position) {
        Intent intent = new Intent(OrderFavoriteFragment.this.getContext(), CartActivity.class);

        intent.putExtra("ProductOrder", dataSource.get(position));

        startActivity(intent);
    }

    @Override
    public void updateUI() {
        orderFavoriteAdapter.updateData(dataSource);
    }
}
