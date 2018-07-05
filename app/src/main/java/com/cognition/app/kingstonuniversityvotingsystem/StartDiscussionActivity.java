package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartDiscussionActivity extends AppCompatActivity {
    AppCompatEditText question;
    AppCompatTextView userName;
    AppCompatButton buttonPost;;
    ImageView menu;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_discussion);

        sharedPreferences = StartDiscussionActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);
        buttonPost = findViewById(R.id.post);
        question = findViewById(R.id.txtQuestion);
        userName = findViewById(R.id.user_name);
        userName.setText(sharedPreferences.getString("userName", ""));

        menu = findViewById(R.id.menuIcon);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(StartDiscussionActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(StartDiscussionActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(StartDiscussionActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(StartDiscussionActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(StartDiscussionActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(StartDiscussionActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(StartDiscussionActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(StartDiscussionActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = false;
                if (!question.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(StartDiscussionActivity.this, "Add a question!", Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<Post> call = apiInterface.createPost(
                            sharedPreferences.getString("userIdNumber", "").toString().trim(),
                            question.getText().toString().trim()
                    );
                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                question.setText("");
                                Toast.makeText(StartDiscussionActivity.this, "Post created successfuly", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(StartDiscussionActivity.this, DiscussionPageActivity.class);
                                startActivity(i);
                            } else {
                                try {
                                    Log.e("CreatePostActivity", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(StartDiscussionActivity.this, "Post not created", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Toast.makeText(StartDiscussionActivity.this, "Post not created", Toast.LENGTH_SHORT).show();
                            Log.w("StartDiscussionActivity", t);
                        }
                    });
                }
            }
        });
    }
}
