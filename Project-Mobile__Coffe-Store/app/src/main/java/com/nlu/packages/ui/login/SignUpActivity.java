package com.nlu.packages.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nlu.packages.R;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {
    ImageView imageView;
    AppCompatButton button1,button2;
    EditText signUpName,signUpEmail,signUpPassword,retypePassword;
    CheckBox checkBox;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        signUpName = findViewById(R.id.signUpName);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        retypePassword = findViewById(R.id.retypePassword);
        checkBox = findViewById(R.id.checkBox);
        // Thiết lập chạm để đóng bàn phím
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //banner
            imageView=findViewById(R.id.imageView2);
            Picasso.get().load("https://athome.starbucks.com/sites/default/files/2023-08/1_CAH_Article_HeartSbxCraftedCoffee_2880x1660.jpg").into(imageView);

            //go to login activity (use for sign up soon)
            button1=findViewById(R.id.SignUpButton);
            button1.setOnClickListener(view -> {
//                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                signup();
            });

            //go to login activity
            button2=findViewById(R.id.backToLogin);
            button2.setOnClickListener(view ->{
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            });
            return insets;
        });
    }

    private void hideKeyboard() {// phươngh thức này dủng đển keyboard sau click vào vị trí trống trong màn hình
        View view= this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void signup() {
        String str_signUpName = signUpName.getText().toString().trim();
        String str_signUpEmail = signUpEmail.getText().toString().trim();
        String str_signUpPassword = signUpPassword.getText().toString().trim();
        String str_retypePassword = retypePassword.getText().toString().trim();


        if(TextUtils.isEmpty(str_signUpName)) {
            signUpName.setError("Tên bị để trống!!!!!");
            signUpName.requestFocus();

        }else if(TextUtils.isEmpty(str_signUpEmail)){
            signUpEmail.setError("Email bị để trống");
            signUpEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(str_signUpEmail).matches()){
            signUpEmail.setError("Không có @gmail.com");
            signUpEmail.requestFocus();
        } else if(TextUtils.isEmpty(str_signUpPassword)){
            signUpPassword.setError("Mật khẩu bị để trống");
            signUpPassword.requestFocus();
        } else if(TextUtils.isEmpty(str_retypePassword)){
            retypePassword.setError("Mật khẩu nhập lại bị để trống");
            retypePassword.requestFocus();
        } else if (!checkBox.isChecked()){
            checkBox.setError("");
        } else if (str_signUpPassword.equals(str_retypePassword)){
            auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(str_signUpEmail,str_signUpPassword).addOnCompleteListener(SignUpActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                if(user != null){
                                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                                            .setDisplayName(str_signUpName)
                                            .build();
                                    user.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Log.d("Profile Update", "User profile updated success");
                                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                    }
                                                    Log.d("Profile Update", "User profile updated fail");
                                                }
                                            });

                                }

//                                Toast.makeText(SignUpActivity.this, "đăng ký thành công", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                            }else{
                                Toast.makeText(SignUpActivity.this, "đăng ký thất bại", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }

    }
}