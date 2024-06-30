package com.nlu.packages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.inventory.cart.CartProductItemAdapter;
import com.nlu.packages.net_working.CoffeeAppService;
import com.nlu.packages.response_dto.cart.CartResponseDTO;
import com.nlu.packages.response_dto.product.ProductResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    ImageButton btnBack;
    private RecyclerView recycleView_listProductInCart;
    private CartProductItemAdapter adapter;
    private TextView textView_totalPrice;
    private CartResponseDTO cartResponseDTO;
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJucWF0MDkxOSIsImlhdCI6MTcxOTY5Mjc5OCwiZXhwIjoxNzE5Nzc5MTk4fQ.FChvNJIIgIosR8IvL57opthjBCy3fIau1hZca0pHJUY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        btnBack = findViewById(R.id.goBackButton);
        adapter = new CartProductItemAdapter(this, cartResponseDTO);


        recycleView_listProductInCart = findViewById(R.id.listViewProductsInCart);
        textView_totalPrice = findViewById(R.id.cart_totalPrice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView_listProductInCart.setLayoutManager(layoutManager);
        recycleView_listProductInCart.setAdapter(adapter);
        {
            loadCartItems(adapter);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void loadCartItems(CartProductItemAdapter adapter) {
        CoffeeAppService.getInstance(token)
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
                                adapter.redraw(cartResponseDTO);
                                textView_totalPrice.setText("0đ");
                            }
                            else {
                                // draw list
                                findViewById(R.id.listViewProductsInCart_EmptyState)
                                        .setVisibility(View.GONE);
                                recycleView_listProductInCart.setVisibility(View.VISIBLE);
                                adapter.redraw(cartResponseDTO);
                                textView_totalPrice.setText(cartResponseDTO.getTotal()+"00đ");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponseDTO> call, Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });
    }
}