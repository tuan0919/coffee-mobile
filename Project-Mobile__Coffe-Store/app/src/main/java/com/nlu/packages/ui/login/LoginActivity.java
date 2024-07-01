package com.nlu.packages.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nlu.packages.MainActivity;
import com.nlu.packages.R;
import com.nlu.packages.request_dto.LoginRequestDTO;
import com.nlu.packages.response_dto.TokenResponseDTO;
import com.nlu.packages.service.CoffeeService;
import com.nlu.packages.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton button1, button2, button3;
    EditText editText1,editText2;
    private Runnable onLoginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            editText1 = findViewById(R.id.logInEmail);
            editText2 = findViewById(R.id.logInPassword);

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
            onLoginHandler = () -> {
                String email = editText1.getText().toString();
                String password = editText2.getText().toString();
                CoffeeService.getClient().login(LoginRequestDTO.builder()
                        .email(email)
                        .password(password).build())
                .enqueue(new Callback<TokenResponseDTO>() {
                    @Override
                    public void onResponse(Call<TokenResponseDTO> call, Response<TokenResponseDTO> response) {
                        if (response.isSuccessful() && response.body().getToken() != null) {
                            String token = response.body().getToken();
                            MyUtils.save(LoginActivity.this, "token", token);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponseDTO> call, Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });
            };
            button3.setOnClickListener(s -> onLoginHandler.run());
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