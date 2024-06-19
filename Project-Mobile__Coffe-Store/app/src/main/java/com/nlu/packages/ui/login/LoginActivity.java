package com.nlu.packages.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nlu.packages.MainActivity;
import com.nlu.packages.R;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    AppCompatButton button1, button2, button3;
    EditText editText1,editText2;
    ImageView google;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN =9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        editText1 = findViewById(R.id.logInEmail);
        editText2 = findViewById(R.id.logInPassword);
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

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
                Login();
            });
            //login google.
            google = findViewById(R.id.google);
            google.setOnClickListener(view -> {
                LoginByGoogle();
            });

            return insets;
        });
    }

    private void LoginByGoogle() {
        Intent signinIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());

            }
            catch (ApiException e)
            {
                Toast.makeText(this,"login failed:"+e,Toast.LENGTH_SHORT).show();
                String errorMessage = "Google sign-in failed: " + e.getStatusCode() + " - " + e.getMessage();
                Log.e("LoginFaile", "login failed:"+e, e);
            }
        }
    }
    private void firebaseAuth(String idToken){
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            // thêm dữ liệu user nếu chưa tồn tại.
                            firebaseUser = auth.getCurrentUser();

                        }
                        else{
                            Toast.makeText(LoginActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void Login() {
        String str_email = editText1.getText().toString().trim();
        String str_pass = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)) {
            editText1.setError("Email bị để trống");
            editText1.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            editText1.setError("không đúng định dạng ");
            editText1.requestFocus();
        } else if (TextUtils.isEmpty(str_pass)) {
            editText2.setError("mật khẩu bị để trống");
            editText2.requestFocus();
        }else{
            auth.signInWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(LoginActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = auth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Log.d("sign", "Login success "+firebaseUser.getEmail());
                            } else {
                                Log.d("sign", "Un Login success");
                            }

                        }
                    });

        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}