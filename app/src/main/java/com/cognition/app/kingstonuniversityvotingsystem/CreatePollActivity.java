package com.cognition.app.kingstonuniversityvotingsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePollActivity extends AppCompatActivity {
    static AppCompatEditText datePicker;
    AppCompatEditText topic;
    AppCompatTextView userName;
    AppCompatButton createPoll;
    AppCompatButton moreAnswers;
    LinearLayout answersLayout;
    ImageView menu;

    SharedPreferences sharedPreferences;

    List<AppCompatEditText> answerEditTexts;
    int answersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        sharedPreferences = CreatePollActivity.this.getSharedPreferences
                (getString(R.string.preferences_filename), Context.MODE_PRIVATE);

        answerEditTexts = new ArrayList<>();

        datePicker = findViewById(R.id.datePicker);
        topic = findViewById(R.id.topic);
        userName = findViewById(R.id.user_name);
        createPoll = findViewById(R.id.createPoll);
        moreAnswers = findViewById(R.id.moreAnswers);
        answersLayout = findViewById(R.id.answers);
        menu = findViewById(R.id.menuIcon);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CreatePollActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.logout:
                                sharedPreferences.edit().clear().apply();
                                startActivity(new Intent(CreatePollActivity.this, RegisterActivity.class));
                                ActivityCompat.finishAffinity(CreatePollActivity.this);
                                return true;
                            case R.id.createPoll:
                                startActivity(new Intent(CreatePollActivity.this, CreatePollActivity.class));
                                return true;
                            case R.id.createforum:
                                startActivity(new Intent(CreatePollActivity.this, StartDiscussionActivity.class));
                                return true;
                            case R.id.browseForum:
                                startActivity(new Intent(CreatePollActivity.this, DiscussionPageActivity.class));
                                return true;
                            case R.id.browsePolls:
                                startActivity(new Intent(CreatePollActivity.this, BrowsePollsActivity.class));
                                return true;
                            case R.id.myPolls:
                                startActivity(new Intent(CreatePollActivity.this, MyPollsActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

        for (answersCount = 1; answersCount <= 4; answersCount++) {
            AppCompatEditText editText = new AppCompatEditText(CreatePollActivity.this);
            editText.setHint(String.format(Locale.ENGLISH, "Answer %d", answersCount));
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            answersLayout.addView(editText);
            answerEditTexts.add(editText);
        }

        userName.setText(sharedPreferences.getString("userName", ""));
        datePicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerFragment mDatePicker = new DatePickerFragment();
                    mDatePicker.show(CreatePollActivity.this.getSupportFragmentManager(), "Select date");
                }
            }
        });

        moreAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText editText = new AppCompatEditText(CreatePollActivity.this);
                editText.setHint(String.format(Locale.ENGLISH, "Answer %d", ++answersCount));
                editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                answersLayout.addView(editText);
                answerEditTexts.add(editText);
            }
        });

        createPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray answerArray = new JSONArray();
                for (AppCompatEditText editText : answerEditTexts) {
                    String answer = editText.getText().toString().trim();
                    if (!answer.isEmpty())
                        answerArray.put(answer);
                }

                boolean isValid = false;

                if (!topic.getText().toString().trim().isEmpty()) {
                    isValid = true;
                } else {
                    isValid = false;
                    Toast.makeText(CreatePollActivity.this, "Add a topic!", Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<Poll> call = apiInterface.createPoll(
                            sharedPreferences.getString("userIdNumber", "").trim(),
                            topic.getText().toString().trim(),
                            datePicker.getText().toString().trim(),
                            answerArray.toString()
                    );
                    call.enqueue(new Callback<Poll>() {
                        @Override
                        public void onResponse(Call<Poll> call, Response<Poll> response) {
                            if (response.isSuccessful()) {
                                topic.setText("");
                                datePicker.setText("");
                                answersLayout.removeAllViews();
                                for (answersCount = 1; answersCount <= 4; answersCount++) {
                                    AppCompatEditText editText = new AppCompatEditText(CreatePollActivity.this);
                                    editText.setHint(String.format(Locale.ENGLISH, "Answer %d", answersCount));
                                    editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                    answersLayout.addView(editText);
                                    answerEditTexts.add(editText);
                                }
                                Toast.makeText(CreatePollActivity.this, "Poll created and pending approval", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CreatePollActivity.this, BrowsePollsActivity.class);
                                startActivity(i);
                            } else {
                                try {
                                    Log.e("CreatePollActivity", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CreatePollActivity.this, "Poll not created", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Poll> call, Throwable t) {
                            Toast.makeText(CreatePollActivity.this, "Poll not created", Toast.LENGTH_SHORT).show();
                            Log.w("CreatePollActivity", t);
                        }
                    });
                }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            datePicker.setText(String.format(Locale.ENGLISH, "%d/%d/%d", day, month, year));
        }
    }
}
