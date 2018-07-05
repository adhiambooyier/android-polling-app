package com.cognition.app.kingstonuniversityvotingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 4/9/2018.
 */

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollsAdapterViewHolder> {
    Context mContext;
    List<Poll> pollList;
    SharedPreferences sharedPreferences;

    class PollsAdapterViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView pollOwner;
        AppCompatTextView pollQuestion;
        RadioGroup answersGroup;
        AppCompatButton vote;
        AppCompatTextView pollExpiry;
        LinearLayout resultsLayout;

        public PollsAdapterViewHolder(View itemView) {
            super(itemView);

            pollOwner = itemView.findViewById(R.id.pollOwner);
            pollQuestion = itemView.findViewById(R.id.pollQuestion);
            vote = itemView.findViewById(R.id.btnVote);
            answersGroup = itemView.findViewById(R.id.radioAnswers);
            pollExpiry = itemView.findViewById(R.id.pollExpiry);
            resultsLayout = itemView.findViewById(R.id.resultsLayout);
        }
    }

    public PollsAdapter(Context context, List<Poll> list) {
        this.mContext = context;
        this.pollList = list;
        this.sharedPreferences = mContext.getSharedPreferences
                (mContext.getString(R.string.preferences_filename), Context.MODE_PRIVATE);
    }

    @Override
    public PollsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.polls_layout, parent, false);
        return new PollsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PollsAdapterViewHolder holder, int position) {
        final Poll poll = pollList.get(position);

        holder.pollOwner.setText(poll.getUserId());
        holder.pollQuestion.setText(poll.getTopic());
        holder.pollExpiry.setText("Poll Expires: " + poll.getExpiry());
        holder.answersGroup.removeAllViews();
        for (Choice choice : poll.getChoices()) {
            AppCompatRadioButton radioButton = new AppCompatRadioButton(mContext);
            radioButton.setId(choice.getId());
            radioButton.setText(choice.getChoices());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            holder.answersGroup.addView(radioButton);
        }
        holder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answersGroup.getCheckedRadioButtonId() != -1) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<Poll> call = apiInterface.vote(poll.getId(), sharedPreferences.getString("userIdNumber", ""), holder.answersGroup.getCheckedRadioButtonId());
                    call.enqueue(new Callback<Poll>() {
                        @Override
                        public void onResponse(Call<Poll> call, Response<Poll> response) {
                            if (response.isSuccessful()) {
                                holder.answersGroup.setVisibility(View.GONE);
                                holder.vote.setVisibility(View.GONE);

                                for (Choice choice : response.body().getChoices()) {
                                    LinearLayout linearLayout = new LinearLayout(mContext);
                                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                    AppCompatTextView percentage = new AppCompatTextView(mContext);
                                    percentage.setTypeface(percentage.getTypeface(), Typeface.BOLD);
                                    layoutParams.setMargins(0, 0, (int) mContext.getResources().getDisplayMetrics().density * 20, 0);
                                    percentage.setLayoutParams(layoutParams);
                                    percentage.setText(String.format(Locale.ENGLISH, "%d%%\t", choice.getPercentage()));

                                    AppCompatTextView choiceText = new AppCompatTextView(mContext);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    choiceText.setLayoutParams(layoutParams);
                                    choiceText.setText(choice.getChoices());

                                    linearLayout.addView(percentage);
                                    linearLayout.addView(choiceText);
                                    holder.resultsLayout.addView(linearLayout);
                                }

                                holder.resultsLayout.setVisibility(View.VISIBLE);
                            } else {
                                if (response.code() == 400) {
                                    Toast.makeText(mContext, "You have already voted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "An Error Occured, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Poll> call, Throwable t) {
                            Toast.makeText(mContext, "An Error Occured, please try again", Toast.LENGTH_SHORT).show();
                            Log.w("PollsAdapter", t);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pollList.size();
    }
}
