package com.example.mymvvm.repo;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.example.mymvvm.MainActivity;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Singer;
import com.example.mymvvm.model.Song;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Helper {
    //return position a song in a list
    public static int findSong(Song song) {
        for (int i = 0; i < MainActivity.songData.size(); i++) {
            if (MainActivity.storageSong.get(i).getId() == song.getId()) {
                return i;
            }

        }
        return -1;
    }
    public static int findSongInAList(Song song, List<Song> songs) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getId() == song.getId()) {
                return i;
            }

        }
        return -1;
    }

    public static int existAlbum(String name, List<Album> albums) {
        if (albums.size() == 0) {
            return -1;
        }
        for (int i = 0; i < albums.size(); i++) {
            if (name.trim().equals(albums.get(i).getName().trim())) {
                return i;
            }
        }
        return -1;
    }

    public static int existASinger(String name, List<Singer> singer) {
        if (singer.size() == 0) {
            return -1;
        }
        for (int i = 0; i < singer.size(); i++) {
            if (name.trim().equals(singer.get(i).getName().trim())) {
                return i;
            }
        }
        return -1;
    }

    public static List<Album> albumName() {
        List<Album> album = new ArrayList<>();
        for (Song song : MainActivity.storageSong
        ) {
            int index = existAlbum(song.getAlbum(), album);
            if (index == -1) {
                album.add(new Album(song.getAlbum(), 1, song.getThumbnail()));
            } else {

                album.get(index).setAmount(album.get(index).getAmount() + 1);
            }
        }
        return album;
    }

    public static List<Singer> singerName() {
        List<Singer> singer = new ArrayList<>();
        for (Song song : MainActivity.storageSong
        ) {
            int index = existASinger(song.getSinger(), singer);
            if (index == -1) {
               singer.add(new Singer(song.getSinger(), 1, song.getThumbnail()));
            } else {

                singer.get(index).setAmount(singer.get(index).getAmount() + 1);
            }
        }
        return singer;
    }


    public static List<Song> songFromAlbum(String albumName) {
        List<Song> songs = new ArrayList<>();
        for (Song s : MainActivity.storageSong
        ) {
            if (s.getAlbum().equals(albumName)) {
                songs.add(s);
            }
        }
        return songs;
    }

    public static List<Song> songFromSinger(String singerName) {
        List<Song> songs = new ArrayList<>();
        for (Song s : MainActivity.storageSong
        ) {
            if (s.getSinger().equals(singerName)) {
                songs.add(s);
            }
        }
        return songs;
    }

//    public static Bitmap getThumbnail(Uri uri, Context context) {
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        byte[] imgbyte;
//        Bitmap bitmp = null;
//        BitmapFactory.Options option = new BitmapFactory.Options();
//
//        mmr.setDataSource(context, uri);
//        imgbyte = mmr.getEmbeddedPicture();
//
//
//        if (imgbyte != null) {
//            bitmp = BitmapFactory.decodeByteArray(imgbyte, 0, imgbyte.length, option);
//        }
//        return bitmp;
//    }
//    public static Bitmap imageFromPath(String path){
//        return BitmapFactory.decodeFile(path);
//    }
    public static String timeFormat(Long duration){
        return (duration/1000)/60 +":"+ (duration/1000)%60;


    }

    public static Bitmap getAlbumart(Long album_id, Context context)
    {
        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
        }
        return bm;
    }

    public static List<Song> searchSong(String word){

        List<Song> songs = new ArrayList<>();
        int flag;
        if(word.trim().equals("") || MainActivity.storageSong.size()==0){
            return songs;
        }
        for (Song song: MainActivity.storageSong
             ) {
            flag=0;
            if(song.getSinger().toLowerCase(Locale.ROOT).contains(word.toLowerCase())){
                flag=1;
            }
            if(song.getAlbum().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))){
                flag=1;
            }
            if(song.getSinger().toLowerCase(Locale.ROOT).contains(word.toLowerCase(Locale.ROOT))){
                flag=1;
            }
            if(flag==1){
                songs.add(song);
            }

        }
        return songs;
    }


}
