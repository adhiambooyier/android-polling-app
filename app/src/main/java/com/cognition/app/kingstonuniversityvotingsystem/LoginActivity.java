package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton reset;
    AppCompatButton login;
    AppCompatEditText id_number;
    AppCompatEditText password;

    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = LoginActivity.this.getSharedPreferences(getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("isLoggedIn", false)){
            Intent i = new Intent(LoginActivity.this, BrowsePollsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reset = findViewById(R.id.reset);
        id_number = findViewById(R.id.id_number);
        password = findViewById(R.id.password);
        login = findViewById(R.id.Login);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, PasswordResetActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                boolean isValid = false;
                if (!id_number.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    Toast.makeText(LoginActivity.this, "ID field is empty", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                if (!password.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    Toast.makeText(LoginActivity.this, "ID field is empty", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
                if (isValid) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<User> call = apiInterface.loginUser(
                            id_number.getText().toString().trim(),
                            password.getText().toString().trim()
                    );
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("userID", response.body().getId());
                                editor.putString("userIdNumber", response.body().getId_number());
                                editor.putString("userName", response.body().getName());
                                editor.putString("userEmail", response.body().getEmail());
                                editor.putString("userPassword", response.body().getPassword());
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();

                                Intent i = new Intent(LoginActivity.this, BrowsePollsActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.w("LoginActivity", t);
                            Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
