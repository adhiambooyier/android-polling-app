package com.cognition.app.kingstonuniversityvotingsystem;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Adhiambo Oyier on 4/9/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsAdapterViewHolder> {
    List<Post> postList;

    class PostsAdapterViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView pollOwner;
        AppCompatTextView pollQuestion;

        public PostsAdapterViewHolder(View itemView) {
            super(itemView);

            pollOwner = itemView.findViewById(R.id.pollOwner);
            pollQuestion = itemView.findViewById(R.id.pollQuestion);
        }
    }

    public PostsAdapter(List<Post> list) {
        this.postList = list;
    }

    @Override
    public PostsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_views, parent, false);
        return new PostsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostsAdapterViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.pollOwner.setText(post.getUserId());
        holder.pollQuestion.setText(post.getQuestion());
    }

    @Override
    public int getItemCount() {
        return this.postList.size();
    }
}
