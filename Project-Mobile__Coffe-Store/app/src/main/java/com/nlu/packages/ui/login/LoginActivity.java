package com.nlu.packages.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nlu.packages.MainActivity;
import com.nlu.packages.R;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton button1, button2, button3;
    EditText editText1,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //add underline for button
            button1 = findViewById(R.id.signUpButton);
            button1.setPaintFlags(button1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            //go to sign up activity
            button1.setOnClickListener(view -> {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            });

            //forgot password
            button2 = findViewById(R.id.forgotPasswordButton);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                }
            });

            //Login
            button3 = findViewById(R.id.LoginButton);
            button3.setOnClickListener(view -> {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            });

            return insets;
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}