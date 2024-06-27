package com.nlu.packages.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nlu.packages.MainActivity;
import com.nlu.packages.R;
import com.squareup.picasso.Picasso;

public class ForgotActivity extends AppCompatActivity {
    ImageView imageView;
    AppCompatButton button1;
    EditText editTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);
        editTextText =findViewById(R.id.editTextText);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            imageView=findViewById(R.id.imageView6);
            Picasso.get().load("https://st3.depositphotos.com/7008302/37539/i/450/depositphotos_375393458-stock-photo-watercolor-seamless-pattern-palms-modern.jpg").into(imageView);


            button1=findViewById(R.id.SignUpButton);
            button1.setOnClickListener(view -> {
                resetPass();

            });

            return insets;
        });
    }

    private void resetPass() {
        String str_email = editTextText.getText().toString().trim();
        if(TextUtils.isEmpty(str_email)){
            editTextText.setError("Email bị để trống");
            editTextText.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            editTextText.setError("khong ddung email");
            editTextText.requestFocus();
        }else{
            FirebaseAuth auth= FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(str_email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent= new Intent(ForgotActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{

                            }
                        }
                    });

        }
    }
}