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

import com.nlu.packages.inventory.paymentmethod_recycle.PaymentMethodAdapter;
import com.nlu.packages.inventory.paymentmethod_recycle.PaymentMethodText;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {

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
        List<PaymentMethodText> list = new ArrayList<>();
        list.add(new PaymentMethodText("Credit Card"));
        list.add(new PaymentMethodText("Debit Card"));
        list.add(new PaymentMethodText("Paypal"));
        list.add(new PaymentMethodText("Google Pay"));
        list.add(new PaymentMethodText("Apple Pay"));
        RecyclerView recyclerView = findViewById(R.id.paymentMethodRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PaymentMethodAdapter(this,list));
        double total = getIntent().getDoubleExtra("total",0.0);
        AppCompatButton button = findViewById(R.id.proceedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethodActivity.this,PaymentActivity.class);
                intent.putExtra("total",total);
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
        TextView totalText = findViewById(R.id.total);
        totalText.setText(String.valueOf(total));
    }
}