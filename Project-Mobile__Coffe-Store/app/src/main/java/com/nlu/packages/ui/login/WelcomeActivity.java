package com.nlu.packages.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nlu.packages.R;
import com.nlu.packages.utils.MyUtils;
import com.squareup.picasso.Picasso;

public class WelcomeActivity extends AppCompatActivity {
    ImageView imageView1;
    AppCompatButton button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //khởi tạo token
            MyUtils.save(this, "token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJucWF0MDkxOSIsImlhdCI6MTcxOTgyMzYzOSwiZXhwIjoxNzE5OTEwMDM5fQ.6Kbk5WwJoohN2Y_gWoGbP-zL3O4j6mv1UYKibufMfi4");

            //load image with url using picasso
            imageView1 = findViewById(R.id.imageView);
            Picasso.get().load("https://images.unsplash.com/photo-1599231110146-c7cb40d0c6ff?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Z3JlZW4lMjBjb2ZmZWUlMjBiZWFufGVufDB8fDB8fHww").into(imageView1);

            //button to sign up activity
            button1=findViewById(R.id.createAccountButton);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
                }
            });

            //button to change to login activity
            button2=findViewById(R.id.loginButton);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
            });

            return insets;
        });
    }
}