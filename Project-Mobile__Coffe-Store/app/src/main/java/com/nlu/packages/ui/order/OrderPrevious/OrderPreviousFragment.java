package com.nlu.packages.ui.order.OrderPrevious;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.R;
import com.nlu.packages.response_dto.order.OrderResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.ui.order.OrderFavorite.OrderFavoriteAdapter;
import com.nlu.packages.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class OrderPreviousFragment extends Fragment implements PreviousAdapterInterface {
    private OrderResponseDTO orderResponse;
    private RecyclerView previous_recycleView;
    private PreviousAdapter orderFavoriteAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_previous, container, false);
        previous_recycleView = view.findViewById(R.id.previous_recycleView);
        orderFavoriteAdapter = new PreviousAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        previous_recycleView.setAdapter(orderFavoriteAdapter);
        previous_recycleView.setLayoutManager(layoutManager);
        return view;
    }



    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(OrderPreviousFragment.this.getContext(), CartActivity.class);
        startActivity(intent);
    }

    public void loadPreviousOrderData() {
        String token = MyUtils.get(getContext(), "token");
        CoffeeService
                .getRetrofitInstance(token).getOrder()
                .enqueue(new Callback<List<OrderResponseDTO>>() {
                    @Override
                    public void onResponse(Call<List<OrderResponseDTO>> call, Response<List<OrderResponseDTO>> response) {
                        if (response.isSuccessful()) {
                            // Yêu cầu adapter update giao diện
                            orderResponse = response.body().get(0);
                            orderFavoriteAdapter.renderView(orderResponse);
                            System.out.println(orderResponse);
                        } else {
                            System.out.println(response.code());
                            System.out.println(response.raw());
                        }
                        System.out.println(orderResponse);
                    }

                    @Override
                    public void onFailure(Call<List<OrderResponseDTO>> call, Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });
    }
}
