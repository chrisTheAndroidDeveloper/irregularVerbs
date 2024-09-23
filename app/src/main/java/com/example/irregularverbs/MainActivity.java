package com.example.irregularverbs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser){
        user = User.copy(newUser);
    }

    SharedPreferences prefs = null;

    private void setUp() {

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, user.reminderHour);
        calendar1.set(Calendar.MINUTE, user.reminderMinute);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        findViewById(R.id.dismiss_goal).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        findViewById(R.id.goal_completed).setVisibility(View.GONE);

        if(user.day == -1){
            user.day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            user.year = Calendar.getInstance().get(Calendar.YEAR);
        }

        if(user.day < Calendar.getInstance().get(Calendar.DAY_OF_YEAR) || user.year < Calendar.getInstance().get(Calendar.YEAR)){

            if(user.dailyGoalsOn) {
                user.completedToday = 0;
                boolean[] days = user.daysOfPractice;
                int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                        Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};

                int d = user.day;
                int y = user.year;
                Calendar currentDay = Calendar.getInstance();
                currentDay.set(Calendar.DAY_OF_YEAR, d);
                currentDay.set(Calendar.YEAR, y);
                while(currentDay.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().get(Calendar.DAY_OF_YEAR) || currentDay.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)){
                    currentDay.setTimeInMillis(currentDay.getTimeInMillis()+86400000);
                    for(int i = 0; i < daysOfWeek.length; i++){
                        if(currentDay.get(Calendar.DAY_OF_WEEK) == daysOfWeek[i]){
                            if(days[i]){
                                user.totalGoals++;
                            }
                        }
                    }
                }
            }

            user.day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            user.year = Calendar.getInstance().get(Calendar.YEAR);
        }

        if(user.dailyGoalsOn) {
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_WEEK);
            boolean[] days = user.daysOfPractice;
            int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                    Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
            for (int d = 0; d < days.length; d++) {
                if (days[d] && daysOfWeek[d] == day) {
                    findViewById(R.id.dismiss_goal).setVisibility(View.GONE);
                    if(user.completedToday >= user.goal){
                        findViewById(R.id.goal_completed).setVisibility(View.VISIBLE);
                    }else {
                        findViewById(R.id.goal_completed).setVisibility(View.GONE);
                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(user.getTodaysGoalCompletion());
                    }
                }
            }
        }

        if(user.hasCompletedTests()) {
            ((TextView) findViewById(R.id.high_score)).setText(user.bestScore+"");
        } else {
            ((TextView) findViewById(R.id.high_score)).setText("-");
        }

        ((TextView) findViewById(R.id.avg_score)).setText(user.getAverageScoreString());
        ((TextView) findViewById(R.id.goals_percent)).setText(user.getGoalsCompletionString());

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = new NotificationChannel("IV_REMINDERS_CHANNEL", "REMINDERS", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("A channel for the practice reminders");
        notificationManager.createNotificationChannel(notificationChannel);

        Intent i1 = new Intent(getApplicationContext(), NotifyReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, i1, PendingIntent.FLAG_IMMUTABLE);


        if (prefs.getBoolean("firstrun", true)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1907);
                }
            }

            prefs.edit().putBoolean("firstrun", false).apply();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if(!am.canScheduleExactAlarms()){
                    user.exactAlarmsAllowed = false;
                    am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                }else{
                    user.exactAlarmsAllowed = true;
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
                }
            }else{
                user.exactAlarmsAllowed = true;
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(!am.canScheduleExactAlarms() && user.exactAlarmsAllowed){
                user.exactAlarmsAllowed = false;
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            }else if(am.canScheduleExactAlarms() && !user.exactAlarmsAllowed){
                user.exactAlarmsAllowed = true;
                am.cancel(pendingIntent1);

                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefs = getSharedPreferences("com.example.irregularverbs", MODE_PRIVATE);

        if(user == null) {
            try {
                String json = "";
                @SuppressLint("SdCardPath") FileInputStream inputStream = openFileInput("user.json");
                Scanner in = new Scanner(inputStream);
                json = in.nextLine();
                user = (new Gson()).fromJson(json, User.class);
            } catch (Exception e) {
                user = new User();
            }
        }

        setUp();

        findViewById(R.id.settings_button).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        findViewById(R.id.practice_button).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TestActivity1.class);
            startActivity(i);
        });

        findViewById(R.id.dict_button).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, DictionaryActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user == null) {
            try {
                String json = "";
                @SuppressLint("SdCardPath") FileInputStream inputStream = openFileInput("user.json");
                Scanner in = new Scanner(inputStream);
                json = in.nextLine();
                user = (new Gson()).fromJson(json, User.class);
            } catch (Exception e) {
                user = new User();
            }
        }
        setUp();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        try {
            Gson gson = new Gson();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(gson.toJson(user));
            outputStreamWriter.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to save your data", Toast.LENGTH_SHORT).show();
        }
    }
}