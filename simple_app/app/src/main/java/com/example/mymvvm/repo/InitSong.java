package com.example.mymvvm.repo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mymvvm.model.Song;

import java.util.ArrayList;
import java.util.List;

public class InitSong {


    private static String[] project = new String[]{
//    private long id;
//    private String title;
//    private String artist;
//    private String singer;
//    private String thumbnail;
//    private String songLink;
            MediaStore.Audio.Media._ID,
//
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,

            MediaStore.Audio.Media.DURATION
            , MediaStore.Audio.Media.ALBUM_ID

    };

    public static List<Song> getAllSongs(Context context) {
        final List<Song> tempSong = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, project, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).contains(".mp3") &&cursor.getLong(5)>=5000) {
                    long id = Long.valueOf(cursor.getString(0));
                    String title = cursor.getString(1);
                    String singer = cursor.getString(2);
                    String songLink = cursor.getString(3);
                    String album = cursor.getString(4);
                    Song song = new Song();
                    song.setId(id);
                    song.setTitle(title);
                    song.setSinger(singer);
                    song.setSongLink(songLink);
                    song.setAlbum(album);
                    song.setDuration(Long.valueOf(cursor.getString(5)));
                    song.setThumbnail(cursor.getLong(6));
                    //  Bitmap bm = BitmapFactory.decodeFile(cursor.getString(5));
                    // song.setThumbnail(cursor.getString(5));
                    //  song.setThumbnail(Helper.getThumbnail(Uri.parse(song.getSongLink()),context));
                    //       Log.d("TAG", "getAllSongs: "+song.toString());
                    tempSong.add(song);
                }


            }
        }
        cursor.close();


        return tempSong;
    }
}

