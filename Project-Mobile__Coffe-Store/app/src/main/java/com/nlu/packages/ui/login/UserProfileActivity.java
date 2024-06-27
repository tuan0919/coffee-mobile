package com.nlu.packages.ui.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nlu.packages.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    CircleImageView avatar;
    ImageView select_img ,background;
    TextView txthoten,txtsdt,txtdiachi,txtemail;
    AppCompatButton btnupload,btnupload_infor;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        select_img = findViewById(R.id.select_img);
        avatar = findViewById(R.id.avatar);
        background = findViewById(R.id.background);
        btnupload = findViewById(R.id.btnupload);
        btnupload_infor= findViewById(R.id.btnupload_infor);
        txthoten =findViewById(R.id.txthoten);
        txtsdt=findViewById(R.id.txtsdt);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtemail=findViewById(R.id.txtemail);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(firebaseAuth!= null){
            txthoten.setText(user.getDisplayName());
            txtemail.setText(user.getEmail());

        }

        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take pictures from galary.
                PickImgFromGalary();

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadImage();
            }
        });

        // Thiết lập hành động khi bấm vào TextView txthoten
        btnupload_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditInfoDialog();


            }
        });

    }

    private void showEditInfoDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_infor_user);
        EditText etName = dialog.findViewById(R.id.et_name);
        EditText etPhone = dialog.findViewById(R.id.et_phone);
        EditText etAddress = dialog.findViewById(R.id.et_address);
        EditText etEmail = dialog.findViewById(R.id.et_email);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_etName= etName.getText().toString().trim();
                String str_etPhone= etPhone.getText().toString().trim();
                String str_etAddress= etAddress.getText().toString().trim();
                String str_etEmail = etEmail.getText().toString().trim();


            }
        });

    }

    private void upLoadImage() {
        // get infor from textView.
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM__dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName= formatter.format(now);

        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        avatar.setImageURI(null);
//                        Log.d("image","UpLoad thanh cong");
//                        if (progressDialog.isShowing()){
//                            progressDialog.dismiss();
//                        }
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                Log.d("image", "Upload thành công: " + imageUrl);
                                // update URL

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(Uri.parse(imageUrl))
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("image", "User profile updated.");
                                                // Cập nhật ImageView với ảnh mới
                                                Glide.with(UserProfileActivity.this)
                                                        .load(imageUrl)
                                                        .into(avatar);
                                            }
                                        });
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();

                        }
                        Log.d("image","UpLoad thanh cong");

                    }
                });
    }

    private void PickImgFromGalary() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==100 && data!= null && data.getData() != null){
            imageUri = data.getData();
            avatar.setImageURI(imageUri);
        }
    }
}