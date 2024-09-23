package com.example.irregularverbs;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        User user = MainActivity.getUser();
        User proxy_user = User.copy(user);

        ((SwitchCompat) findViewById(R.id.goalsSwitch)).setChecked(proxy_user.dailyGoalsOn);
        ((SwitchCompat) findViewById(R.id.remindersswitch)).setChecked(proxy_user.remindersOn);
        ((SwitchCompat) findViewById(R.id.askSwitch)).setChecked(proxy_user.askAboutRangeEachTime);

        if(proxy_user.dailyGoalsOn){
            findViewById(R.id.goal_block).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.goal_block).setVisibility(View.GONE);
        }

        if(proxy_user.remindersOn){
            findViewById(R.id.reminder_block).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.reminder_block).setVisibility(View.GONE);
        }

        if(proxy_user.remindersOn || proxy_user.dailyGoalsOn){
            findViewById(R.id.days_block).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.days_block).setVisibility(View.GONE);
        }


        ((SwitchCompat) findViewById(R.id.goalsSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proxy_user.dailyGoalsOn = !proxy_user.dailyGoalsOn;
                if(!proxy_user.dailyGoalsOn){
                    findViewById(R.id.goal_block).setVisibility(View.GONE);
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                            Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
                    for (int d = 0; d < proxy_user.daysOfPractice.length; d++) {
                        if (proxy_user.daysOfPractice[d] && daysOfWeek[d] == day) {
                            proxy_user.totalGoals--;
                        }
                    }
                    if(!proxy_user.remindersOn){
                        findViewById(R.id.days_block).setVisibility(View.GONE);
                    }
                }else{
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                            Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
                    for (int d = 0; d < proxy_user.daysOfPractice.length; d++) {
                        if (proxy_user.daysOfPractice[d] && daysOfWeek[d] == day) {
                            proxy_user.totalGoals++;
                        }
                    }
                    findViewById(R.id.goal_block).setVisibility(View.VISIBLE);
                    findViewById(R.id.days_block).setVisibility(View.VISIBLE);
                }
            }
        });

        ((TextView) findViewById(R.id.goalDisplay)).setText(proxy_user.goal+"");

        findViewById(R.id.decreaseGoalBtn).setOnClickListener(v -> {
            if(proxy_user.goal >= 2){
                proxy_user.goal--;
                if(proxy_user.goal <= proxy_user.completedToday){
                    proxy_user.completedGoals ++;
                }
                ((TextView) findViewById(R.id.goalDisplay)).setText(proxy_user.goal+"");
            }
        });

        findViewById(R.id.increaseGoalBtn).setOnClickListener(v -> {
            proxy_user.goal++;
            if(proxy_user.goal == proxy_user.completedToday + 1){
                proxy_user.completedGoals --;
            }
            ((TextView) findViewById(R.id.goalDisplay)).setText(proxy_user.goal+"");
        });

        ((SwitchCompat) findViewById(R.id.remindersswitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proxy_user.remindersOn = !proxy_user.remindersOn;
                if(!proxy_user.remindersOn){
                    findViewById(R.id.reminder_block).setVisibility(View.GONE);
                    if(!proxy_user.dailyGoalsOn){
                        findViewById(R.id.days_block).setVisibility(View.GONE);
                    }
                }else{
                    findViewById(R.id.reminder_block).setVisibility(View.VISIBLE);
                    findViewById(R.id.days_block).setVisibility(View.VISIBLE);
                }
            }
        });

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, proxy_user.reminderHour);
        time.set(Calendar.MINUTE, proxy_user.reminderMinute);
        ((Button) findViewById(R.id.reminderTimeDisplay)).setText(DateUtils.formatDateTime(SettingsActivity.this,
                time.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));

        TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
            Calendar rTime = Calendar.getInstance();
            rTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            rTime.set(Calendar.MINUTE, minute);
            ((Button) findViewById(R.id.reminderTimeDisplay)).setText(DateUtils.formatDateTime(SettingsActivity.this,
                    rTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));

            user.reminderHour = hourOfDay;
            user.reminderMinute = minute;

        };

        findViewById(R.id.reminderTimeDisplay).setOnClickListener(v -> new TimePickerDialog(SettingsActivity.this, t,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), true)
                .show());

        Button[] days_buttons = new Button[]{findViewById(R.id.monbtn), findViewById(R.id.tuebtn),
                findViewById(R.id.wedbtn), findViewById(R.id.thubtn), findViewById(R.id.fribtn),
                findViewById(R.id.satbtn), findViewById(R.id.sunbtn)
        };

        for(int i = 0; i < days_buttons.length; i++){
            if(proxy_user.daysOfPractice[i]){
                days_buttons[i].setBackground(getDrawable(R.drawable.stats_background));
            }else {
                days_buttons[i].setBackground(getDrawable(R.drawable.days_btn_normal_background));
            }
            days_buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = 0;
                    if(v.getId() == R.id.tuebtn){
                        number = 1;
                    }else if(v.getId() == R.id.wedbtn){
                        number = 2;
                    }else if(v.getId() == R.id.thubtn){
                        number = 3;
                    }else if(v.getId() == R.id.fribtn){
                        number = 4;
                    }else if(v.getId() == R.id.satbtn){
                        number = 5;
                    }else if(v.getId() == R.id.sunbtn){
                        number = 6;
                    }
                    proxy_user.setDayOfPractice(number, !proxy_user.daysOfPractice[number]);
                    int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                            Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
                    if(proxy_user.daysOfPractice[number]){
                        if(daysOfWeek[number] == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
                            proxy_user.totalGoals++;
                        }
                        v.setBackground(getDrawable(R.drawable.stats_background));
                    }else{
                        if(daysOfWeek[number] == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
                            proxy_user.totalGoals--;
                        }
                        v.setBackground(getDrawable(R.drawable.days_btn_normal_background));
                    }
                }
            });
        }

        ((TextView) findViewById(R.id.questionsDisplay)).setText(proxy_user.numQuestions+"");

        findViewById(R.id.decreaseQuestionsBtn).setOnClickListener(v -> {
            if(proxy_user.numQuestions >= 2){
                proxy_user.numQuestions--;
                ((TextView) findViewById(R.id.questionsDisplay)).setText(proxy_user.numQuestions+"");
            }
        });

        findViewById(R.id.increaseQuestionsBtn).setOnClickListener(v -> {
            if (proxy_user.numQuestions < TestActivity1.verbs.length) {
                proxy_user.numQuestions++;
                ((TextView) findViewById(R.id.questionsDisplay)).setText(proxy_user.numQuestions+"");
            }
        });

        RadioButton[] typeChoiceButtons = new RadioButton[]{findViewById(R.id.type1),
                findViewById(R.id.type2), findViewById(R.id.type3)
        };

        typeChoiceButtons[proxy_user.questionType].setChecked(true);

        for(int i = 0; i < typeChoiceButtons.length; i++){
            int num = i;
            typeChoiceButtons[i].setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked){
                    proxy_user.questionType = num;
                }
            });
        }

        ((SwitchCompat) findViewById(R.id.askSwitch)).setChecked(proxy_user.askAboutRangeEachTime);

        ((EditText) findViewById(R.id.inputFrom)).setText(proxy_user.testRangeStart+"");

        ((EditText) findViewById(R.id.inputTo)).setText(proxy_user.testRangeEnd+"");

        ((SwitchCompat) findViewById(R.id.askSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proxy_user.askAboutRangeEachTime = isChecked;
            }
        });

        findViewById(R.id.savebtn).setOnClickListener(v -> {
            if(!proxy_user.remindersOn){
                Intent i1 = new Intent(getApplicationContext(), NotifyReceiver.class);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, i1, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                am.cancel(pendingIntent1);
            }else{
                AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                Intent i1 = new Intent(getApplicationContext(), NotifyReceiver.class);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, i1, PendingIntent.FLAG_IMMUTABLE);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, user.reminderHour);
                calendar1.set(Calendar.MINUTE, user.reminderMinute);
                calendar1.set(Calendar.SECOND, 0);
                calendar1.set(Calendar.MILLISECOND, 0);

                am.cancel(pendingIntent1);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(!am.canScheduleExactAlarms()) {
                        am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                    }else{
                        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                    }
                }else{
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                }
            }
            proxy_user.testRangeEnd = Integer.parseInt(((EditText) findViewById(R.id.inputTo)).getText().toString());
            proxy_user.testRangeStart = Integer.parseInt(((EditText) findViewById(R.id.inputFrom)).getText().toString());
            if(proxy_user.testRangeEnd - proxy_user.testRangeStart + 1 < proxy_user.numQuestions) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_to_few_verbs), Toast.LENGTH_LONG).show();
            }else if(proxy_user.testRangeEnd > TestActivity1.verbs.length){
                Toast.makeText(getApplicationContext(), "The upper boundary must be at most "+TestActivity1.verbs.length, Toast.LENGTH_LONG).show();
            }else if(proxy_user.testRangeStart < 1){
                Toast.makeText(getApplicationContext(), "The lower boundary should be at least 1", Toast.LENGTH_LONG).show();
            }else if((proxy_user.remindersOn || proxy_user.dailyGoalsOn)){
                for(int i = 0; i < proxy_user.daysOfPractice.length; i++){
                    if(proxy_user.daysOfPractice[i]){
                        proxy_user.reminderHour = user.reminderHour;
                        proxy_user.reminderMinute = user.reminderMinute;
                        MainActivity.setUser(proxy_user);
                        try {
                            Gson gson = new Gson();
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user.json", Context.MODE_PRIVATE));
                            outputStreamWriter.write(gson.toJson(proxy_user));
                            outputStreamWriter.close();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Unable to save your data", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        break;
                    }
                    else if(i == proxy_user.daysOfPractice.length - 1) {
                        Toast.makeText(getApplicationContext(), "Choose practice days", Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                MainActivity.setUser(proxy_user);
                try {
                    Gson gson = new Gson();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user.json", Context.MODE_PRIVATE));
                    outputStreamWriter.write(gson.toJson(proxy_user));
                    outputStreamWriter.close();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to save your data", Toast.LENGTH_SHORT).show();                }
                finish();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                findViewById(R.id.view).setVisibility(View.VISIBLE);
                findViewById(R.id.closebtn).setVisibility(View.VISIBLE);
                findViewById(R.id.closebtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.textView9).setVisibility(View.GONE);
                        findViewById(R.id.view).setVisibility(View.GONE);
                        findViewById(R.id.closebtn).setVisibility(View.GONE);
                    }
                });
            }
        });

        findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.reminderMinute = proxy_user.reminderMinute;
                user.reminderHour = proxy_user.reminderHour;
                finish();
            }
        });
    }
}