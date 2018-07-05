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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ForumCommentsActivity extends AppCompatActivity {
    ImageView menu;
    CommentsAdapter commentsAdapter;
    RecyclerView commentList;
    AppCompatTextView postOwner;
    AppCompatTextView postQuestion;

    SharedPreferences sharedPreferences;
    Intent receivedIntent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_comments);

        sharedPreferences = ForumCommentsActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);
        receivedIntent = getIntent();
        Type type = new TypeToken<List<Comment>>() {
        }.getType();

        List<Comment> comments = new Gson().fromJson(receivedIntent.getStringExtra("commentListJson"),type);
        commentsAdapter = new CommentsAdapter(ForumCommentsActivity.this, comments);
        
        menu = findViewById(R.id.menuIcon);
        commentList = findViewById(R.id.commentList);
        postOwner = findViewById(R.id.postOwner);
        postQuestion = findViewById(R.id.postQuestion);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ForumCommentsActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(ForumCommentsActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(ForumCommentsActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(ForumCommentsActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(ForumCommentsActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(ForumCommentsActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(ForumCommentsActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(ForumCommentsActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                        }

                        return  false;
                    }
                });
            }
        });
        commentList.setLayoutManager(new LinearLayoutManager(ForumCommentsActivity.this));
        commentList.setItemAnimator(new DefaultItemAnimator());
        commentList.setAdapter(commentsAdapter);

        sharedPreferences = ForumCommentsActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        postOwner.setText(receivedIntent.getStringExtra("postOwner"));
        postQuestion.setText(receivedIntent.getStringExtra("postQuestion"));
    }
}
