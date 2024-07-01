package com.nlu.packages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nlu.packages.dto.response.cart.CartResponseDTO;
import com.nlu.packages.inventory.checkout_recycle.CheckOutSummaryAdapter;
import com.nlu.packages.inventory.stupid_data.DataToPayment;

import java.io.Serializable;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // recycleOrderSummary
//        Đoạn code này nhận list từ Activity Cart
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//            List<CartResponseDTO.CartItemDTO> list = getIntent().getParcelableArrayListExtra("chooseList", CartResponseDTO.CartItemDTO.class);
//        }
//        Hoặc đoạn này nhưng CartResponseDTO.CartItemDTO implements Serializable
//        List<CartResponseDTO.CartItemDTO> list =  (List<CartResponseDTO.CartItemDTO>) getIntent().getSerializableExtra("chooseList");
        RecyclerView recyclerView = findViewById(R.id.recycleOrderSummary);
        // Fake Data
        DataToPayment datu = new DataToPayment();
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //-> Thay đổi được
//        recyclerView.setAdapter(new CheckOutSummaryAdapter(this,list));
        recyclerView.setAdapter(new CheckOutSummaryAdapter(this,datu.getList()));
        //<- Thay đổi được
        double total = 0;
        //-> Thay đổi được
//        for (CartResponseDTO.CartItemDTO bu : list){
//            total+=bu.getPrice();
//        }
        for (CartResponseDTO.CartItemDTO bu : datu.getList()){
            total+=bu.getPrice();
        }
        //<- Thay đổi được
        TextView subtotal = findViewById(R.id.subtotal);
        subtotal.setText("$ "+Double.toString(total));
        TextView truetotal = findViewById(R.id.total);
        truetotal.setText("$ "+Double.toString(total));

        AppCompatButton button = findViewById(R.id.proceedToPaymentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, PaymentMethodActivity.class);
                startActivity(intent);
            }
        });

        ImageButton goBack = findViewById(R.id.goBackButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}