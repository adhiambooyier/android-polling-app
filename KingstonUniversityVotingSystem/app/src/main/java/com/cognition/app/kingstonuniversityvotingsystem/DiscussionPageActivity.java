package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionPageActivity extends AppCompatActivity {
    RecyclerView posts;
    PostsAdapter postsAdapter;
    AppCompatTextView userName;
    ImageView menu;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_page);
        posts = findViewById(R.id.postList);

        menu = findViewById(R.id.menuIcon);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DiscussionPageActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(DiscussionPageActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(DiscussionPageActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(DiscussionPageActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(DiscussionPageActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(DiscussionPageActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(DiscussionPageActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(DiscussionPageActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        
        posts.setLayoutManager(new LinearLayoutManager(DiscussionPageActivity.this));
        posts.setItemAnimator(new DefaultItemAnimator());

        sharedPreferences = DiscussionPageActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        userName = findViewById(R.id.user_name);
        userName.setText(sharedPreferences.getString("userName", ""));

        retrievePosts();
    }

    private void retrievePosts() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Post>> call = apiInterface.retrievePosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postsAdapter = new PostsAdapter(response.body());
                    posts.setAdapter(postsAdapter);
                } else {
                    Toast.makeText(DiscussionPageActivity.this, "failed to load posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.w("DiscussionPageActivity", t);
                Toast.makeText(DiscussionPageActivity.this, "failed to load posts", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
