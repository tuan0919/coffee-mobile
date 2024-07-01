package com.nlu.packages.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nlu.packages.R;
import com.nlu.packages.api.ApiService;
import com.nlu.packages.response.product.ProductResponseDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderCoffeeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Spinner spnSize;
    private Spinner spnMilk;
    private Spinner spnSweet;
    private Spinner spnDecaf;
    private AppCompatButton minusButtonQuantitty;
    private AppCompatButton plusButtonQuantity;

    private TextView quantityText;
    public static List<ProductResponseDTO> products;
    private ImageView productPicture;
    private TextView productName, priceProduct;
    private List<ProductResponseDTO.IngredientDTO> ingredients;
    private ArrayAdapter<String> arrayAdapter;

    public static List<String> sz = new ArrayList<>();
    public static List<String> milks = new ArrayList<>();
    public static List<String> toppings = new ArrayList<>();
    public static List<String> sweeteners = new ArrayList<>();

    public DetailOrderCoffeeFragment() {
        // Required empty public constructor
    }

    public static DetailOrderCoffeeFragment newInstance(String param1, String param2) {
        DetailOrderCoffeeFragment fragment = new DetailOrderCoffeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_order_coffee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productPicture = view.findViewById(R.id.productPicture);
        productName = view.findViewById(R.id.productName);
        priceProduct = view.findViewById(R.id.priceProduct);
        spnSize = view.findViewById(R.id.spinner_size);
        spnMilk = view.findViewById(R.id.spinner_milk);
        spnDecaf = view.findViewById(R.id.spinner_decaf);
        spnSweet = view.findViewById(R.id.spinner_sweet);
        minusButtonQuantitty = view.findViewById(R.id.minusButtonQuantitty);
        plusButtonQuantity = view.findViewById(R.id.plusButtonQuantity);
        quantityText = view.findViewById(R.id.quantityText);

        minusButtonQuantitty.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityText.getText().toString());
            if (currentQuantity > 1) {
                quantityText.setText(String.valueOf(--currentQuantity));
            }
        });

        plusButtonQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityText.getText().toString());
            quantityText.setText(String.valueOf(++currentQuantity));
        });

        ApiService.api.getProduct(1).enqueue(new Callback<List<ProductResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ProductResponseDTO>> call, Response<List<ProductResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products = response.body();
                    updateUI();
                } else {
                    Log.d("DetailOrder", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponseDTO>> call, Throwable t) {
                Log.d("DetailOrder", "API call failed: " + t.getMessage());
            }
        });
    }

    private void updateUI() {
        if (products == null || products.isEmpty()) return;

        ProductResponseDTO product = products.get(0);
        Glide.with(getContext()).load(product.getAvatar()).into(productPicture);
        productName.setText(product.getProductName());
        priceProduct.setText(String.valueOf(product.getBasePrice()));

        sz.clear();
        milks.clear();
        toppings.clear();
        sweeteners.clear();

        for (ProductResponseDTO.ProductSizeDTO size : product.getAvailableSizes()) {
            sz.add(size.getSizeEnum().name());
//            size.getMultipler()==1.0
        }

        for (ProductResponseDTO.IngredientDTO ingredient : product.getAvailableIngredients()) {
            switch (ingredient.getIngredientType()) {
                case MILKS:
                    milks.add(ingredient.getIngredientName().toUpperCase());
                    break;
                case TOPPINGS:
                    toppings.add(ingredient.getIngredientName().toUpperCase());
                    break;
                case SWEETENERS:
                    sweeteners.add(ingredient.getIngredientName().toUpperCase());
                    break;
            }
        }

        setSpinnerAdapter(spnSize, sz);
        setSpinnerAdapter(spnMilk, milks);
        setSpinnerAdapter(spnDecaf, toppings);
        setSpinnerAdapter(spnSweet, sweeteners);
    }

    private void setSpinnerAdapter(Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public static List<String> getSz() {
        return sz;
    }

    public static List<String> getMilks() {
        return milks;
    }

    public static List<String> getToppings() {
        return toppings;
    }

    public static List<String> getSweeteners() {
        return sweeteners;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sz.clear();
        milks.clear();
        toppings.clear();
        sweeteners.clear();
    }
}
