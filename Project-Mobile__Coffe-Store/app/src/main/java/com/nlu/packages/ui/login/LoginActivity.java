package com.nlu.packages.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nlu.packages.MainActivity;
import com.nlu.packages.R;
import com.nlu.packages.api.ApiService;
import com.nlu.packages.model.Login;
import com.nlu.packages.model.Section;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    AppCompatButton button1, button2, button3;
    EditText editText1,editText2;
    Button google;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN =9001;
    Login login;

    private TextView jsonString;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 100;

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
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

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

        jsonString = findViewById(R.id.jsonString);
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId("653580284590-18e8cnb86rmk7f8qo0thkt5404aj1uak.apps.googleusercontent.com") // TODO
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
    }

    public void buttonGoogleSignIn(View view) {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("TAG", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d("TAG", e.getLocalizedMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    String fullName = credential.getDisplayName();
                    String firstName = credential.getFamilyName();
                    String lastName = credential.getGivenName();
                    String id = credential.getId();
                    String phone = credential.getPhoneNumber();
//                    String avatar = credential.getProfilePictureUri().toString();
                    jsonString.setText(username);  // test
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d("TAG", "Got ID token.");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
//                        Log.d("idToken", idToken);
//                        Log.d("username", username);
//                        Log.d("password", password);
//                        Log.d("fullName", fullName);
//                        Log.d("firstName", firstName);
//                        Log.d("lastName", lastName);
//                        Log.d("id", id);
//                        Log.d("phone", phone);
//                        Log.d("avatar", avatar);
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d("TAG", "Got password.");
                    }
                } catch (ApiException e) {
                    jsonString.setText(e.toString());
                }
                break;
        }
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
//            auth.signInWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(LoginActivity.this,
//                    new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                firebaseUser = auth.getCurrentUser();
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                Log.d("sign", "Login success "+firebaseUser.getEmail());
//                            } else {
//                                Log.d("sign", "Un Login success");
//                            }
//
//                        }
//                    });
            login= new Login();
            login.setEmail(str_email);
            login.setPassword(str_pass);
            ApiService.api.loginUser(login).enqueue(new Callback<Section>() {

                @Override
                public void onResponse(Call<Section> call, Response<Section> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAGGGGGG", "API call successful, response received.");
                        Section section = response.body();
                        if (section != null) {
                            Log.d("TAGGGGGG", "Token: " + section.getToken()); // Giả sử có phương thức getToken() trong model Section.
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Log.d("TAGGGGGG", "Response body is null");
                        }
                    } else {
                        Log.d("TAGGGGGG", "API call not successful, HTTP status code: " + response.code());
                        try {
                            Log.d("TAGGGGGG", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("TAGGGGGG", "Error reading error body", e);
                        }
                    }
//                    Log.d("TAGGGGGG","SUCESS");
//                    Section section = response.body();
//                    if(section != null){
//                        Log.d("TAGGGGGG",section.getToken());
//                    }

                }
                @Override
                public void onFailure(Call<Section> call, Throwable t) {
                    Log.d("TAGGGGGG","FAILED",t);
                }
            });

        }
    }
    private void hideKeyboard() {// phươngh thức này dủng đển keyboard sau click vào vị trí trống trong màn hình
        View view= this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void LoginByGoogle() {
        Intent signinIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent,RC_SIGN_IN);
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RC_SIGN_IN){
//            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account= task.getResult(ApiException.class);
//                firebaseAuth(account.getIdToken());
//
//            }
//            catch (ApiException e)
//            {
//                Toast.makeText(this,"login failed:"+e,Toast.LENGTH_SHORT).show();
//                String errorMessage = "Google sign-in failed: " + e.getStatusCode() + " - " + e.getMessage();
//                Log.e("LoginFaile", "login failed:"+e, e);
//            }
//        }
//    }
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
                            Log.d("TAGGGGGGG", firebaseUser.getEmail());
                            Log.d("TAGGGGGGG", firebaseUser.getDisplayName());
                            Log.d("TAGGGGGGG", firebaseUser.getPhoneNumber());
                            Log.d("TAGGGGGGG", firebaseUser.getPhotoUrl().toString());
                            Log.d("TAGGGGGGG", firebaseUser.getEmail());

                        }
                        else{
                            Toast.makeText(LoginActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}