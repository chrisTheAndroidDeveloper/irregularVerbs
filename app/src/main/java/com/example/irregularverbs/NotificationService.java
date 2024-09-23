package com.example.irregularverbs;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Scanner;

public class NotificationService extends Service {

    NotificationChannel channel;

    @Override
    public void onCreate() {
        super.onCreate();

        channel = new NotificationChannel("my_channel_01",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_MIN);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, intent2, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, "my_channel_01")
                .setContentTitle("")
                .setContentIntent(pendingIntent2)
                .setContentText("").build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int statId){
        super.onStartCommand(intent, flag, statId);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0, intent2, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, "my_channel_01")
                .setContentTitle("")
                .setContentIntent(pendingIntent2)
                .setContentText("").build();

        startForeground(1, notification);

        boolean send = false;
        User user;

        try {
            String json = "";
            @SuppressLint("SdCardPath") FileInputStream inputStream = getApplicationContext().openFileInput("user.json");
            Scanner in = new Scanner(inputStream);
            json = in.nextLine();
            user = (new Gson()).fromJson(json, User.class);
        } catch (Exception error) {
            user = new User();
        }

        if(user.dailyGoalsOn) {
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_WEEK);
            boolean[] days = user.daysOfPractice;
            int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                    Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
            for (int d = 0; d < days.length; d++) {
                if (days[d] && daysOfWeek[d] == day) {
                    send = true;
                    break;
                }
            }
        }

        if (send) {
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), "IV_REMINDERS_CHANNEL")
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setContentIntent(pendingIntent2)
                            .setContentTitle("Reminder")
                            .setContentText("Time to practice")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);


            notificationManager.notify(719, notificationBuilder.build());
        }

        /*Calendar neededTime = Calendar.getInstance();
        neededTime.setTimeInMillis(neededTime.getTimeInMillis() + 86400000);
        neededTime.set(Calendar.MILLISECOND, 0);
        neededTime.set(Calendar.SECOND, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(getApplicationContext(), NotifyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(am.canScheduleExactAlarms()) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, neededTime.getTimeInMillis(), pendingIntent);
            }else{
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, neededTime.getTimeInMillis(), pendingIntent);
            }
        }else{
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, neededTime.getTimeInMillis(), pendingIntent);
        }*/

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}