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

public class MyPollsActivity extends AppCompatActivity {
    RecyclerView polls;
    PollsAdapter pollsAdapter;
    AppCompatTextView userName;
    ImageView menu;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_polls);

        sharedPreferences = MyPollsActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        userName = findViewById(R.id.user_name);
        userName.setText(sharedPreferences.getString("userName", ""));

        polls = findViewById(R.id.pollList);

        menu = findViewById(R.id.menuIcon);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MyPollsActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(MyPollsActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(MyPollsActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(MyPollsActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(MyPollsActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(MyPollsActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(MyPollsActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(MyPollsActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

        polls.setLayoutManager(new LinearLayoutManager(MyPollsActivity.this));
        polls.setItemAnimator(new DefaultItemAnimator());

        retrieveMyPolls();
    }

    private void retrieveMyPolls() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Poll>> call = apiInterface.retrieveMyPolls(sharedPreferences.getString("userIdNumber", ""));
        call.enqueue(new Callback<List<Poll>>() {
            @Override
            public void onResponse(Call<List<Poll>> call, Response<List<Poll>> response) {
                if (response.isSuccessful()) {
                    pollsAdapter = new PollsAdapter(response.body());
                    polls.setAdapter(pollsAdapter);
                } else {
                    Toast.makeText(MyPollsActivity.this, "failed to load polls", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Poll>> call, Throwable t) {

                Toast.makeText(MyPollsActivity.this, "failed to load polls", Toast.LENGTH_SHORT).show();
                Log.w("CreatePollActivity", t);
            }
        });
    }
}
