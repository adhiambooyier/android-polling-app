package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Adhiambo Oyier on 4/9/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsAdapterViewHolder> {
    List<Post> postList;
    Context mContext;
    SharedPreferences sharedPreferences;

    class PostsAdapterViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView postOwner;
        AppCompatTextView postQuestion;
        AppCompatTextView commenter;
        AppCompatTextView postComment;
        LinearLayout questionItemParent;


        public PostsAdapterViewHolder(View itemView) {
            super(itemView);

            postOwner = itemView.findViewById(R.id.postOwner);
            postQuestion = itemView.findViewById(R.id.postQuestion);
            commenter = itemView.findViewById(R.id.commenter);
            postComment = itemView.findViewById(R.id.comment);
            questionItemParent = itemView.findViewById(R.id.questionItemParent);
        }
    }

    public PostsAdapter(Context context, List<Post> list) {
        this.mContext = context;
        this.postList = list;
        this.sharedPreferences = mContext.getSharedPreferences
                (mContext.getString(R.string.preferences_filename), Context.MODE_PRIVATE);
    }

    @Override
    public PostsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_forum_layout, parent, false);
        return new PostsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostsAdapterViewHolder holder, int position) {
        final Post post = postList.get(position);

        holder.postOwner.setText(post.getUserId());
        holder.postQuestion.setText(post.getQuestion());
        if (post.getComments().size() > 0) {
            Comment lastComment = post.getComments().get(post.getComments().size() - 1);
            holder.commenter.setText(lastComment.getIdNumber());
            holder.postComment.setText(lastComment.getComment());
        } else {
            holder.commenter.setText("");
            holder.postComment.setText("No comments yet");
        }

        holder.questionItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type type = new TypeToken<List<Comment>>() {
                }.getType();

                Intent intent = new Intent(mContext, ForumCommentsActivity.class);
                intent.putExtra("postOwner", post.getUserId());
                intent.putExtra("postQuestion", post.getQuestion());
                intent.putExtra("commentListJson", new Gson().toJson(post.getComments(), type));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.postList.size();
    }
}
