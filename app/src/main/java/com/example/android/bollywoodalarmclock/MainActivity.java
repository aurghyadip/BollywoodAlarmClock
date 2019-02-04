package com.example.android.bollywoodalarmclock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    Context context;
    private static MainActivity inst;
    private PendingIntent pendingIntent;
    private TimePicker timePicker;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button alarm_on = findViewById(R.id.alarm_on);
        Button alarm_off = findViewById(R.id.alarm_off);

        final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        //get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialise calender instance
        final Calendar calendar = Calendar.getInstance();

        alarm_on.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                //get int values of hour and minute and convert them to string
                final int hour = timePicker.getHour();
                final int minute = timePicker.getMinute();

                Toast.makeText(MainActivity.this, "Alarm set to " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

                //tells the clock u pressed the alarm button
                intent.putExtra("extra", "alarm on");

                //create a pending intent which delays sending of intent until calender event
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Alarm Turned Off", Toast.LENGTH_SHORT).show();

                //tells the clock u pressed the alarm button
                intent.putExtra("extra", "alarm off");

                sendBroadcast(intent);
                alarmManager.cancel(pendingIntent);
            }
        });

    }
}
