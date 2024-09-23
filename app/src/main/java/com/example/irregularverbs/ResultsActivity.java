package com.example.irregularverbs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = getIntent();
        int score = i.getIntExtra("score", 0);
        ((TextView) findViewById(R.id.score)).setText(score+"");
        ((ProgressBar) findViewById(R.id.progressBar2)).setProgress(score);
        TextView comment = findViewById(R.id.comment);
        if(score == 100){
            comment.setText(getString(R.string.res0));
        }else if(score >= 90){
            comment.setText(getString(R.string.res1));
        }else if(score >= 75){
            comment.setText(getString(R.string.res2));
        }else if(score >= 60){
            comment.setText(getString(R.string.res3));
        }else{
            comment.setText(getString(R.string.res4));
        }

        findViewById(R.id.proceed_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.numbers).setVisibility(View.GONE);
                findViewById(R.id.detailed_results).setVisibility(View.VISIBLE);

                LinearLayout all = findViewById(R.id.details);

                for(int j = 1; j <= MainActivity.getUser().numQuestions; j++){

                    String[] questionInfo = i.getStringArrayExtra(j+"");

                    ConstraintLayout row = new ConstraintLayout(getApplicationContext());
                    row.setId(View.generateViewId());
                    ConstraintSet set = new ConstraintSet();
                    if(!questionInfo[0].equals(questionInfo[1])){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            if (getResources().getConfiguration().isNightModeActive()) {
                                row.setBackgroundColor(getResources().getColor(R.color.color_primary_variant_night));
                            }else {
                            row.setBackgroundColor(getResources().getColor(R.color.color_primary_variant));
                            }
                        }else {
                            row.setBackgroundColor(getResources().getColor(R.color.color_primary_variant));
                        }
                    }
                    row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    all.addView(row);

                    int padding = (int) (4 * getResources().getDisplayMetrics().density);

                    TextView question = new TextView(getApplicationContext());
                    question.setId(View.generateViewId());
                    question.setText(questionInfo[2]+" "+questionInfo[3].toLowerCase());
                    question.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    question.setPadding(padding, padding, padding, padding);
                    if(!questionInfo[0].equals(questionInfo[1])) {
                        question.setTextColor(getResources().getColor(R.color.black));
                    }
                    row.addView(question, 0);
                    question.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    TextView userAnswer = new TextView(getApplicationContext());
                    userAnswer.setId(View.generateViewId());
                    userAnswer.setText(questionInfo[0]);
                    userAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    userAnswer.setPadding(padding, padding, padding, padding);
                    if(!questionInfo[0].equals(questionInfo[1])) {
                        userAnswer.setTextColor(getResources().getColor(R.color.black));
                    }
                    row.addView(userAnswer, 1);
                    userAnswer.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    TextView correctAnswer = new TextView(getApplicationContext());
                    correctAnswer.setId(View.generateViewId());
                    correctAnswer.setText(questionInfo[1]);
                    correctAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    correctAnswer.setPadding(padding, padding, padding, padding);
                    if(!questionInfo[0].equals(questionInfo[1])) {
                        correctAnswer.setTextColor(getResources().getColor(R.color.black));
                    }
                    row.addView(correctAnswer, 2);
                    correctAnswer.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                    set.clone(row);

                    set.connect(question.getId(), ConstraintSet.START, row.getId(), ConstraintSet.START);
                    set.connect(question.getId(), ConstraintSet.END, userAnswer.getId(), ConstraintSet.START);
                    set.connect(question.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
                    set.connect(question.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

                    set.connect(userAnswer.getId(), ConstraintSet.START, question.getId(), ConstraintSet.END);
                    set.connect(userAnswer.getId(), ConstraintSet.END, correctAnswer.getId(), ConstraintSet.START);
                    set.connect(userAnswer.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
                    set.connect(userAnswer.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

                    set.connect(correctAnswer.getId(), ConstraintSet.START, userAnswer.getId(), ConstraintSet.END);
                    set.connect(correctAnswer.getId(), ConstraintSet.END, row.getId(), ConstraintSet.END);
                    set.connect(correctAnswer.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
                    set.connect(correctAnswer.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

                    set.applyTo(row);

                }

                findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });

    }
}