package com.cognition.app.kingstonuniversityvotingsystem;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by user on 4/9/2018.
 */

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsAdapterViewHolder> {
    List<Poll> pollList;

    class PollsAdapterViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView pollOwner;
        AppCompatTextView pollQuestion;

        public PollsAdapterViewHolder(View itemView) {
            super(itemView);

            pollOwner = itemView.findViewById(R.id.pollOwner);
            pollQuestion = itemView.findViewById(R.id.pollQuestion);
        }
    }

    public PollsAdapter(List<Poll> list) {
        this.pollList = list;
    }

    @Override
    public PollsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_views, parent, false);
        return new PollsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PollsAdapterViewHolder holder, int position) {
        Poll poll = pollList.get(position);

        holder.pollOwner.setText(poll.getUserId());
        holder.pollQuestion.setText(poll.getTopic());
    }

    @Override
    public int getItemCount() {
        return this.pollList.size();
    }
}
