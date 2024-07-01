package com.nlu.packages.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nlu.packages.R;
import com.squareup.picasso.Picasso;

public class ForgotActivity extends AppCompatActivity {
    ImageView imageView;
    AppCompatButton button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            imageView=findViewById(R.id.imageView6);
            Picasso.get().load("https://st3.depositphotos.com/7008302/37539/i/450/depositphotos_375393458-stock-photo-watercolor-seamless-pattern-palms-modern.jpg").into(imageView);

            button1=findViewById(R.id.SignUpButton);
            button1.setOnClickListener(view -> {
                startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
            });

            return insets;
        });
    }
}