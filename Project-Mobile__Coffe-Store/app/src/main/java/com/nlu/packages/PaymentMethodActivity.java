package com.nlu.packages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nlu.packages.enums.EPaymentMethod;
import com.nlu.packages.inventory.paymentmethod_recycle.RecycleViewPaymentMethodAdapter;
import com.nlu.packages.request_dto.order.CreateOrderRequestDTO;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PaymentMethodActivity extends AppCompatActivity {
    private EPaymentMethod method = null;
    private Consumer<EPaymentMethod> onChoosePaymentHandler;
    private long storeId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_method);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Event handler
        onChoosePaymentHandler = (payment) -> {
            method = payment;
            System.out.println(method);
        };

        List<EPaymentMethod> list = Arrays.asList(
                EPaymentMethod.COD,
                EPaymentMethod.MOMO,
                EPaymentMethod.BANK_CART,
                EPaymentMethod.CREDIT_CART
        );
        String token = MyUtils.get(this, "token");
        RecyclerView recyclerView = findViewById(R.id.paymentMethodRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecycleViewPaymentMethodAdapter(this, list, onChoosePaymentHandler));
        double total = getIntent().getDoubleExtra("total",0.0);
        AppCompatButton button = findViewById(R.id.proceedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Long> chooseIds = (ArrayList<Long>)
                        getIntent().getSerializableExtra("chosenProductIds");
                if (method == null) {
                    Toast.makeText(PaymentMethodActivity.this, "Cần chọn 1 phương thức thanh toán",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (method == EPaymentMethod.COD) {
                    CoffeeService
                            .getRetrofitInstance(token).createOrder(CreateOrderRequestDTO
                                    .builder()
                                    .method(method)
                                    .storeId(storeId)
                                    .chosenProductIds(chooseIds)
                                    .build()
                            ).enqueue(new Callback<MessageResponseDTO>() {
                                @Override
                                public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                                    System.out.println(chooseIds);
                                    System.out.println(method.name());
                                    System.out.println(response.message());
                                    System.out.println(response.errorBody());
                                    System.out.println(response.raw());
                                    if (response.isSuccessful()) {
                                        startActivity(new Intent(PaymentMethodActivity.this, EndPaymentActivity.class));
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                                    throw new RuntimeException(throwable);
                                }
                            });

                } else {
                    Intent intent = new Intent(PaymentMethodActivity.this,PaymentActivity.class);
                    intent.putExtra("total",total);
                    intent.putExtra("requestDTO", CreateOrderRequestDTO
                            .builder()
                            .method(method)
                            .storeId(storeId)
                            .chosenProductIds(chooseIds)
                            .build()
                    );
                    startActivity(intent);
                }
            }
        });
        ImageButton goBack = findViewById(R.id.goBackButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView totalText = findViewById(R.id.total);
        totalText.setText(String.valueOf(total));
    }
}