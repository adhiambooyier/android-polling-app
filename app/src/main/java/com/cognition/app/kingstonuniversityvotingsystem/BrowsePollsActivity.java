package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePollsActivity extends AppCompatActivity {
    RecyclerView polls;
    PollsAdapter pollsAdapter;
    ImageView menu;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_polls);

        sharedPreferences = BrowsePollsActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        polls = findViewById(R.id.pollList);
        menu = findViewById(R.id.menuIcon);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BrowsePollsActivity.this);
        polls.setLayoutManager(layoutManager);
        polls.setItemAnimator(new DefaultItemAnimator());

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(BrowsePollsActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(BrowsePollsActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(BrowsePollsActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(BrowsePollsActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(BrowsePollsActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(BrowsePollsActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(BrowsePollsActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(BrowsePollsActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

        retrievePolls();
    }

    private void retrievePolls() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Poll>> call = apiInterface.retrievePolls();
        call.enqueue(new Callback<List<Poll>>() {
            @Override
            public void onResponse(Call<List<Poll>> call, Response<List<Poll>> response) {
                if (response.isSuccessful()) {
                    pollsAdapter = new PollsAdapter(BrowsePollsActivity.this, response.body());
                    polls.setAdapter(pollsAdapter);
                } else {
                    Toast.makeText(BrowsePollsActivity.this, "failed to load polls", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Poll>> call, Throwable t) {
                Log.w("BrowsePollsActivity", t);
                Toast.makeText(BrowsePollsActivity.this, "failed to load polls", Toast.LENGTH_SHORT).show();
            }
        });
    }
}