package com.nlu.packages.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.CheckActivity;
import com.nlu.packages.R;
import com.nlu.packages.request_dto.cart.CartItemRequestDTO;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.utils.MyUtils;
import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {
    ImageButton btnBack;
    private RecyclerView recycleView_listProductInCart;
    private CartProductItemAdapter cartProductItemAdapter;
    private TextView textView_totalPrice;
    private CartResponseDTO cartResponseDTO;
    private BiConsumer<CartResponseDTO.CartItemDTO, Integer> onDeleteHandler;
    private Runnable onLoadHandler;
    private BiConsumer<View, CartResponseDTO.CartItemDTO> onChangeQuantityHandler;
    private Set<Integer> chooseSet = new HashSet<>();
    private BiConsumer<Integer, Boolean> onChooseItemHandler;
    private Runnable onCheckoutClickHandler;
    private Button btnCheckout;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        token = MyUtils.get(this,  "token");

        //Định nghĩa các hàm EventHandler
        onDeleteHandler = (item, pos) -> {
            CartItemRequestDTO requestDTO = CartItemRequestDTO
                    .builder().size(item.getSize())
                    .quantity(0)
                    .ingredients(item.getIngredients())
                    .productId(item.getProduct().getId())
                    .build();
            CoffeeService.getRetrofitInstance(token)
                    .putItem(requestDTO).enqueue(new Callback<MessageResponseDTO>() {
                        @Override
                        public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                            Executor executor = Executors.newSingleThreadExecutor();
                            executor.execute(() -> onLoadHandler.run());
                            runOnUiThread(() -> {
                                chooseSet.remove(pos);
                                cartProductItemAdapter.redraw(cartResponseDTO);
                            });
                        }
                        @Override
                        public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                            throw new RuntimeException(throwable);
                        }
                    });
        };
        onLoadHandler = () -> {
            CoffeeService.getRetrofitInstance(token)
                    .getCart()
                    .enqueue(new Callback<CartResponseDTO>() {
                        @Override
                        public void onResponse(Call<CartResponseDTO> call, Response<CartResponseDTO> response) {
                            if(response.isSuccessful()) {
                                cartResponseDTO = response.body();
                                if (cartResponseDTO.getCount() == 0) {
                                    // draw Empty
                                    findViewById(R.id.listViewProductsInCart_EmptyState)
                                            .setVisibility(View.VISIBLE);
                                    recycleView_listProductInCart.setVisibility(View.GONE);
                                    cartProductItemAdapter.redraw(cartResponseDTO);
                                    textView_totalPrice.setText("0đ");
                                }
                                else {
                                    // draw list
                                    findViewById(R.id.listViewProductsInCart_EmptyState)
                                            .setVisibility(View.GONE);
                                    recycleView_listProductInCart.setVisibility(View.VISIBLE);
                                    cartProductItemAdapter.redraw(cartResponseDTO);
                                    textView_totalPrice.setText(cartResponseDTO.getTotal()+"00đ");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CartResponseDTO> call, Throwable throwable) {
                            throw new RuntimeException(throwable);
                        }
                    });
        };
        onChangeQuantityHandler = (view, itemDTO) -> {
            int quantity = itemDTO.getQuantity();
            String tag = view.getTag().toString();
            switch (tag) {
                case "btn_minus": {
                    if (quantity == 1) break;
                    quantity--;
                }
                break;
                case "btn_plus": quantity++;
                    break;
            }
            CartItemRequestDTO requestDTO = CartItemRequestDTO
                    .builder().size(itemDTO.getSize())
                    .quantity(quantity)
                    .ingredients(itemDTO.getIngredients())
                    .productId(itemDTO.getProduct().getId())
                    .build();
            CoffeeService.getRetrofitInstance(token)
                    .putItem(requestDTO).enqueue(new Callback<MessageResponseDTO>() {
                        @Override
                        public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                            Executor executor = Executors.newSingleThreadExecutor();
                            executor.execute(() -> onLoadHandler.run());
                            runOnUiThread(() -> cartProductItemAdapter.redraw(cartResponseDTO));
                        }

                        @Override
                        public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                            throw new RuntimeException(throwable);
                        }
                    });
        };
        onChooseItemHandler = (pos, isChecked) -> {
            if(isChecked)
                chooseSet.add(pos);
            else
                chooseSet.remove(pos);
        };
        onCheckoutClickHandler = () -> {
            Intent intent = new Intent(CartActivity.this, CheckActivity.class);
            var list = getChooseList();
            intent.putExtra("chooseList", (ArrayList<CartResponseDTO.CartItemDTO>) list);
            startActivity(intent);
        };


        setContentView(R.layout.activity_cart);
        btnBack = findViewById(R.id.goBackButton);
        btnBack.setOnClickListener((e) -> finish());
        btnCheckout = findViewById(R.id.cart_items_checkout);
        cartProductItemAdapter = new CartProductItemAdapter(this,
                cartResponseDTO,
                onDeleteHandler,
                onChangeQuantityHandler,
                onChooseItemHandler);

        recycleView_listProductInCart = findViewById(R.id.listViewProductsInCart);
        textView_totalPrice = findViewById(R.id.cart_totalPrice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView_listProductInCart.setLayoutManager(layoutManager);
        recycleView_listProductInCart.setAdapter(cartProductItemAdapter);
        onLoadHandler.run();
        btnCheckout.setOnClickListener((v) -> {
            if (chooseSet.size() <= 0) {
                Toast.makeText(CartActivity.this,
                        "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
                return;
            }
            onCheckoutClickHandler.run();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    List<CartResponseDTO.CartItemDTO> getChooseList() {
        List<CartResponseDTO.CartItemDTO> list = new ArrayList<>();
        var arr = chooseSet.stream().collect(Collectors.toList());
        for (int i = 0; i < chooseSet.size(); i++) {
            list.add(cartResponseDTO.getList().get(arr.get(i)));
        }
        return list;
    }
}