package com.example.android.bollywoodalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        //fetch extra string from main activity that tells whether button is pressed or not
        String state = intent.getExtras().getString("extra");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string from main activity to ringtone playing service
        service_intent.putExtra("extra", state);

        //start the ringtone service
        context.startService(service_intent);

    }
}
