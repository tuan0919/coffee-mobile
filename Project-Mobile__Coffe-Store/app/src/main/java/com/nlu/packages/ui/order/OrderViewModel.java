package com.nlu.packages.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nlu.packages.dto.response.product.ProductResponseDTO;
import com.nlu.packages.service.CoffeeApi;
import com.nlu.packages.service.CoffeeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<List<ProductResponseDTO>> coffeeList;
    private CoffeeApi coffeeApi;

    public LiveData<List<ProductResponseDTO>> getCoffeeList() {
        if (coffeeList == null) {
            coffeeList = new MutableLiveData<>();
            loadCoffees();
        }
        return coffeeList;
    }

    private void loadCoffees() {
        coffeeApi = CoffeeService.getClient();
        Call<List<ProductResponseDTO>> call = coffeeApi.getAllProduct();
        call.enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                if (response.isSuccessful()) {
                    coffeeList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
