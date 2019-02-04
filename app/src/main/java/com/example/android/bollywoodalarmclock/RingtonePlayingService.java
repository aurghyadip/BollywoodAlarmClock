package com.example.android.bollywoodalarmclock;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class RingtonePlayingService extends Service {

    private NotificationManager notificationManager;
    private MediaPlayer mediaPlayer;
    private boolean isRunning;
    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //NOTIFICATION
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //set up intent that goes to MainActivity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        //set up pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

        //make notification parameters
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Alarm is Going OFFFFFFF.....")
                .setContentText("Click me!")
                .setContentIntent(pending_intent_main_activity)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build();


        //fetch extra strings from alarm receiver activity
        String state = intent.getExtras().getString("extra");

        assert state!= null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            int min = 1;
            int max = 7;

            Random r = new Random();
            int random_number = r.nextInt(max - min + 1) + min;
            Log.e("random number is ", String.valueOf(random_number));

            if (random_number == 1) {
                mediaPlayer = MediaPlayer.create(this, R.raw.bom_diggy_diggy);
            }
            else if (random_number == 2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.chittiyaan_kalaaiyaan);
            }
            else if (random_number == 3) {
                mediaPlayer = MediaPlayer.create(this, R.raw.kar_gayi_chull);
            }
            else if (random_number == 4) {
                mediaPlayer = MediaPlayer.create(this, R.raw.badri_ki_dulhaniya);
            }
            else if (random_number == 5) {
                mediaPlayer = MediaPlayer.create(this, R.raw.larki_aankh_maare);
            }
            else if (random_number == 6) {
                mediaPlayer = MediaPlayer.create(this, R.raw.saturday_saturday);
            }
            else if (random_number == 7) {
                mediaPlayer = MediaPlayer.create(this, R.raw.manma_emotion_jaage);
            }
            else {
                mediaPlayer = MediaPlayer.create(this, R.raw.bom_diggy_diggy);
            }

            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;

            notificationManager.notify(0, notification);
        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.isRunning = false;
    }
}
