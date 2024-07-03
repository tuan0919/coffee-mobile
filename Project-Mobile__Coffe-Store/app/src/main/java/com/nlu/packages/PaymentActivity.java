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
import com.nlu.packages.request_dto.order.CreateOrderRequestDTO;
import com.nlu.packages.response_dto.MessageResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String token = MyUtils.get(this, "token");
        CreateOrderRequestDTO dto = (CreateOrderRequestDTO)
                getIntent().getSerializableExtra("requestDTO");
        ImageButton goBack = findViewById(R.id.goBackButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
            }
        });
        TextView totalText = findViewById(R.id.total);
        totalText.setText(String.valueOf(getIntent().getDoubleExtra("total",0.0)));
        AppCompatButton payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffeeService
                        .getRetrofitInstance(token).createOrder(dto).enqueue(new Callback<MessageResponseDTO>() {
                            @Override
                            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(PaymentActivity.this, response.body().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PaymentActivity.this,EndPaymentActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                                throw new RuntimeException(throwable);
                            }
                        });
            }
        });
    }
}