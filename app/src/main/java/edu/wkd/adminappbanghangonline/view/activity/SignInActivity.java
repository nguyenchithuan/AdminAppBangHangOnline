package edu.wkd.adminappbanghangonline.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import edu.wkd.adminappbanghangonline.R;
import edu.wkd.adminappbanghangonline.data.api.ApiService;
import edu.wkd.adminappbanghangonline.databinding.ActivitySignInBinding;
import edu.wkd.adminappbanghangonline.model.response.UserResponse;
import edu.wkd.adminappbanghangonline.ultil.ProgressDialogLoading;
import edu.wkd.adminappbanghangonline.ultil.UserUltil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private ProgressDialogLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();

        onBack();//Quay trở lại sự kiện trước đó

        goToMainActivity();
    }
    private void goToMainActivity() {
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidate();
            }
        });
    }
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void CheckValidate(){
        String email = binding.edEmailOrPhoneNumberSignIn.getText().toString().trim();
        if(isValidEmail(email)){
            loading.show();
            loginUser();
        }else {
            Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show();
        }
    }
    public void loginUser(){
        String strUsername = binding.edEmailOrPhoneNumberSignIn.getText().toString().trim();
        String strPassword = binding.edPasswordSignIn.getText().toString().trim();
        ApiService.apiService.loginUser(strUsername, strPassword).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    UserResponse response1 = response.body();
                    if(response1.isSuccess()){
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        UserUltil.user = response1.getResult().get(0);
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Log.d("zzzz", "onResponse: " + UserUltil.user);
                    }else {
                        Toast.makeText(SignInActivity.this, "Email or UserName or PassWord invalid", Toast.LENGTH_SHORT).show();
                    }
                    loading.cancel();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "call api err", Toast.LENGTH_SHORT).show();
                loading.cancel();
            }
        });

    }
    private void initView() {
        loading = new ProgressDialogLoading(this);
    }
    private void onBack() {
        binding.arrowBackSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slidle_in_right, R.anim.slidle_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slidle_in_right, R.anim.slidle_out_right);
    }
}