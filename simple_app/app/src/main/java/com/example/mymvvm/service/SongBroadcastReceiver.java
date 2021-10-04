package com.example.mymvvm.service;

import static com.example.mymvvm.Constants.MY_ACTION_KEY;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymvvm.ultis.MAction;

public class SongBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MAction mAction = (MAction) intent.getBundleExtra(MY_ACTION_KEY).get(MY_ACTION_KEY);
        Intent tempIntent = new Intent(context, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        tempIntent.putExtra(MY_ACTION_KEY, bundle);
        context.startService(tempIntent);
    }
}
