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

public class SignUpActivity extends AppCompatActivity {
    ImageView imageView;
    AppCompatButton button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //banner
            imageView=findViewById(R.id.imageView2);
            Picasso.get().load("https://athome.starbucks.com/sites/default/files/2023-08/1_CAH_Article_HeartSbxCraftedCoffee_2880x1660.jpg").into(imageView);

            //go to login activity (use for sign up soon)
            button1=findViewById(R.id.SignUpButton);
            button1.setOnClickListener(view -> {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            });

            //go to login activity
            button2=findViewById(R.id.backToLogin);
            button2.setOnClickListener(view ->{
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            });
            return insets;
        });
    }
}