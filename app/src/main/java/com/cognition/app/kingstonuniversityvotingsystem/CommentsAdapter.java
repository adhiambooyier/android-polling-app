package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Adhiambo Oyier on 4/9/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsAdapterViewHolder> {
    List<Comment> commentList;
    Context mContext;
    SharedPreferences sharedPreferences;

    class CommentsAdapterViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView commenter;
        AppCompatTextView postComment;


        public CommentsAdapterViewHolder(View itemView) {
            super(itemView);

            commenter = itemView.findViewById(R.id.commenter);
            postComment = itemView.findViewById(R.id.postComment);
        }
    }

    public CommentsAdapter(Context context, List<Comment> list) {
        this.mContext = context;
        this.commentList = list;
        this.sharedPreferences = mContext.getSharedPreferences
                (mContext.getString(R.string.preferences_filename), Context.MODE_PRIVATE);
    }

    @Override
    public CommentsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_layout, parent, false);
        return new CommentsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentsAdapterViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        holder.commenter.setText(comment.getIdNumber());
        holder.postComment.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return this.commentList.size();
    }
}
