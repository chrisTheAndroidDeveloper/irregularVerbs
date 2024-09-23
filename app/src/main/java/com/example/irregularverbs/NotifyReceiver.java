package com.example.irregularverbs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.impl.utils.ForceStopRunnable;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("RestrictedApi")
public class NotifyReceiver extends ForceStopRunnable.BroadcastReceiver {

    @Override
    public void onReceive(@NonNull Context context, Intent intent) {

        context.stopService(new Intent(context, NotificationService.class));

        intent = new Intent(context, NotificationService.class);
        context.startForegroundService(intent);
    }
}
