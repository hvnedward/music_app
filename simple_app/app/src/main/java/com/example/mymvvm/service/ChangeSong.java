package com.example.mymvvm.service;

import static com.example.mymvvm.Constants.MY_ACTION_KEY;
import static com.example.mymvvm.Constants.MY_SONG_KEY;
import static com.example.mymvvm.Constants.SONG_KEY;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymvvm.MainActivity;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;
import com.example.mymvvm.ultis.MAction;

public class ChangeSong extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //nhan duoc thong tin bai hat hien tai, va action, tim kiem xem baif hat nay dang nam o vi tri nao cua danh sach, truyen motj bai hat tiep theo bai hat hien tai hoac truoc do, phu thuoc vao MAction
        //get presentsong.
        Song song = (Song) intent.getBundleExtra(MY_SONG_KEY).get(MY_SONG_KEY);
        //get action next or prev
        MAction myAction= (MAction) intent.getBundleExtra(MY_SONG_KEY).get(MY_ACTION_KEY);
        Intent newIntent = new Intent(context, MusicService.class);
        int presentPosition = Helper.findSong(song);
        Bundle bundle = new Bundle();
        Song tempSong =null;
        if(myAction ==MAction.NEXT){
            if(presentPosition+1 <MainActivity.songData.size()){
                tempSong = MainActivity.songData.get(presentPosition+1);

            }
            else{
                tempSong = song;
            }

        }
        else if (myAction ==MAction.PREV){
            if(presentPosition-1 >=0){
                tempSong = MainActivity.songData.get(presentPosition-1);

            }
        }
        bundle.putSerializable(SONG_KEY, tempSong);
        newIntent.putExtra(SONG_KEY, bundle );
        context.startService(newIntent);

    }
}
