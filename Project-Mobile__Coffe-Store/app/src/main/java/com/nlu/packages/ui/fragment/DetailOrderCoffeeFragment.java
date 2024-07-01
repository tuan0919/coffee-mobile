package com.nlu.packages.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.nlu.packages.R;
import com.nlu.packages.enums.EIngredient;
import com.nlu.packages.enums.EProductSize;
import com.nlu.packages.request_dto.cart.CartItemRequestDTO;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.ui.cart.CartActivity;
import com.nlu.packages.utils.MyUtils;
import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailOrderCoffeeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Spinner spnSize;
    private Spinner spnMilk;
    private Spinner spnSweet;
    private Spinner spnToppings;
    private AppCompatButton minusButtonQuantitty;
    private AppCompatButton plusButtonQuantity;

    private TextView quantityText;
    public static List<ProductResponseDTO> products;
    private ImageView productPicture;
    private TextView productName, priceProduct, priceTotal;
    private AppCompatButton addToCartButton;
    private CartItemRequestDTO requestDTO;

    public static List<String> sz = new ArrayList<>();
    public static List<String> milks = new ArrayList<>();
    public static List<String> toppings = new ArrayList<>();
    public static List<String> sweeteners = new ArrayList<>();
    private static Map<String, EProductSize> sz_Map = new HashMap<>();
    private static Map<String, ProductResponseDTO.IngredientDTO> milks_Map = new HashMap<>();
    private static Map<String, ProductResponseDTO.IngredientDTO> toppings_Map = new HashMap<>();
    private static Map<String, ProductResponseDTO.IngredientDTO> sweeteners_Map = new HashMap<>();

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
        spnToppings = view.findViewById(R.id.spinner_decaf);
        spnSweet = view.findViewById(R.id.spinner_sweet);
        minusButtonQuantitty = view.findViewById(R.id.minusButtonQuantitty);
        plusButtonQuantity = view.findViewById(R.id.plusButtonQuantity);
        quantityText = view.findViewById(R.id.quantityText);
        priceTotal = view.findViewById(R.id.priceTrueTotalProduct);
        addToCartButton = view.findViewById(R.id.addToCartButton);

        addToCartButton.setOnClickListener(btn -> {
            String token = MyUtils.get(getActivity(), "token");
            CoffeeService.getRetrofitInstance(token)
                    .putItem(requestDTO).enqueue(new Callback<MessageResponseDTO>() {
                        @Override
                        public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                            Intent intent = new Intent(getActivity(), CartActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                            throwable.printStackTrace();
                            throw new RuntimeException(throwable);
                        }
                    });
        });

        minusButtonQuantitty.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityText.getText().toString());
            if (currentQuantity > 1) {
                quantityText.setText(String.valueOf(--currentQuantity));
            }
            calculatePrice(products.get(0));
        });

        plusButtonQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(quantityText.getText().toString());
            quantityText.setText(String.valueOf(++currentQuantity));
            calculatePrice(products.get(0));
        });
        List<ProductResponseDTO> productResponseDTO =
                (List<ProductResponseDTO>) getActivity().getIntent().getSerializableExtra("productOrder");
        products = productResponseDTO;
        updateUI();
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
            sz_Map.put(size.getSizeEnum().name(), size.getSizeEnum());
        }

        for (ProductResponseDTO.IngredientDTO ingredient : product.getAvailableIngredients()) {
            switch (ingredient.getIngredientType()) {
                case MILKS:
                    milks.add(ingredient.getIngredientName());
                    milks_Map.put(ingredient.getIngredientName(), ingredient);
                    break;
                case TOPPINGS:
                    toppings.add(ingredient.getIngredientName());
                    toppings_Map.put(ingredient.getIngredientName(), ingredient);
                    break;
                case SWEETENERS:
                    sweeteners.add(ingredient.getIngredientName());
                    sweeteners_Map.put(ingredient.getIngredientName(), ingredient);
                    break;
            }
            calculatePrice(products.get(0));
        }

        var spinEvent = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculatePrice(products.get(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                calculatePrice(products.get(0));
            }
        };

        spnSize.setOnItemSelectedListener(spinEvent);
        spnMilk.setOnItemSelectedListener(spinEvent);
        spnToppings.setOnItemSelectedListener(spinEvent);
        spnSweet.setOnItemSelectedListener(spinEvent);

        setSpinnerAdapter(spnSize, sz);
        setSpinnerAdapter(spnMilk, milks);
        setSpinnerAdapter(spnToppings, toppings);
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

    public void calculatePrice(ProductResponseDTO dto) {
        EProductSize size = null;
        var txtSize = ((TextView) spnSize.getSelectedView());
        String sizeStr = txtSize != null ? txtSize.getText().toString() : null;
        size = sz_Map.get(sizeStr);
        List<EIngredient> list = new ArrayList<>();
        double ingredientsPrice = 0d;
        var txtMilk = ((TextView)spnMilk.getSelectedView());
        String milk = txtMilk != null ? txtMilk.getText().toString() : null;
        if (milk != null && !milk.isEmpty()) {
            var ie = milks_Map.get(milk);
            ingredientsPrice += ie.getAddPrice();
            list.add(ie.getIngredientEnum());
        }
        var txtSweet = ((TextView)spnSweet.getSelectedView());
        String sweet = txtSweet != null ? txtSweet.getText().toString() : null;
        if (sweet != null && !sweet.isEmpty()) {
            var ie = sweeteners_Map.get(sweet);
            ingredientsPrice += ie.getAddPrice();
            list.add(ie.getIngredientEnum());
        }
        var txtTopping = ((TextView)spnToppings.getSelectedView());
        String topping = txtTopping != null ? txtTopping.getText().toString() : null;
        if (topping != null && !topping.isEmpty()) {
            var ie = toppings_Map.get(topping);
            ingredientsPrice += ie.getAddPrice();
            list.add(ie.getIngredientEnum());
        }
        int quantity = Integer.valueOf(quantityText.getText().toString());
        double basePrice = dto.getBasePrice();
        requestDTO = CartItemRequestDTO.builder()
                .quantity(quantity)
                .ingredients(list)
                .size(size)
                .productId(dto.getProductId())
                .build();
        float multipler = size != null ? size.getMultipler() : 1;
        var price = (basePrice + ingredientsPrice) * multipler * quantity;
        priceTotal.setText(price+"00đ");
    }
}
