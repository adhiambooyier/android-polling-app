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

public class RegisterActivity extends AppCompatActivity {
    AppCompatButton login;
    AppCompatEditText id_number;
    AppCompatEditText name;
    AppCompatEditText email;
    AppCompatEditText password;
    AppCompatButton register;

    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = RegisterActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            Intent i = new Intent(RegisterActivity.this, BrowsePollsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.login);
        id_number = findViewById(R.id.id_number);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isValid = false;

                if (!id_number.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(RegisterActivity.this, "Id field is empty!", Toast.LENGTH_SHORT).show();
                }

                if (!name.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(RegisterActivity.this, "name is empty!", Toast.LENGTH_SHORT).show();
                }

                if (!email.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(RegisterActivity.this, "email is empty!", Toast.LENGTH_SHORT).show();
                }

                if (!password.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(RegisterActivity.this, " password is empty!", Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<User> call = apiInterface.createUser(
                            id_number.getText().toString().trim(),
                            name.getText().toString().trim(),
                            email.getText().toString().trim(),
                            password.getText().toString().trim()
                    );
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Intent i = new Intent(RegisterActivity.this, BrowsePollsActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                            Log.w("RegisterActivity", t);
                        }
                    });
                }
            }
        });
    }
}
