package com.example.mymvvm.view.fragment.view;

import static com.example.mymvvm.Constants.SONG_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymvvm.Interface.ISongClick;
import com.example.mymvvm.MainActivity;
import com.example.mymvvm.R;
import com.example.mymvvm.adapter.SongAdapter;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Singer;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;
import com.example.mymvvm.service.MusicService;
import com.example.mymvvm.viewmodel.ContainAlbumViewModel;
import com.example.mymvvm.viewmodel.ContainSingerViewModel;

import java.util.List;

public class ContainActivity extends AppCompatActivity implements ISongClick {
    SongAdapter adapter;
    RecyclerView recyclerView;
    ContainAlbumViewModel containAlbumViewModel;
    ContainSingerViewModel containSingerViewModel;
    ImageView imageView;
    TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain);
        imageView = findViewById(R.id.img);
        recyclerView = findViewById(R.id.lv);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Album album = null;
        Singer singer = null;
        long bit=0;
        String title= null;
        if(bundle.get("album") ==null){
             singer = (Singer) bundle.get("singer");
             bit = singer.getThumb();
             title = singer.getName();
             containSingerViewModel = new ViewModelProvider(this).get(ContainSingerViewModel.class);
             containSingerViewModel.getMusic(singer.getName());
             containSingerViewModel.getSong().observe(this, new Observer<List<Song>>() {
                 @Override
                 public void onChanged(List<Song> songs) {
                     adapter.setData(songs);
                     MainActivity.songData.clear();
                     MainActivity.songData.addAll(songs);
                 }
             });



        }
        else{
            album = (Album) bundle.get("album");
            bit = album.getThumb();
            title = album.getName();
            containAlbumViewModel = new ViewModelProvider(this).get(ContainAlbumViewModel.class);
            containAlbumViewModel.getMusic(album.getName());

            containAlbumViewModel.getSong().observe(this, new Observer<List<Song>>() {
                @Override
                public void onChanged(List<Song> songs) {
                    adapter.setData(songs);

                    MainActivity.songData.addAll(songs);
                }
            });
        }



        adapter = new SongAdapter(this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);



        titleView = findViewById(R.id.title);
        titleView.setText(title);

        Bitmap bitmap = Helper.getAlbumart(bit, this);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
        else{
            imageView.setImageResource(R.drawable.ic_baseline_music_note_24);
        }



        recyclerView.setAdapter(adapter);

    }

    @Override
    public void clickASong(Song song) {
        Intent intent = new Intent(this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SONG_KEY, song);
        intent.putExtra(SONG_KEY, bundle);
        // Toast.makeText(getActivity(), "Ddax nhan vap song", Toast.LENGTH_SHORT).show();
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MainActivity.songData.clear();
//
//        MainActivity.songData.addAll(MainActivity.storageSong);
        Log.d("TAG", "onDestroy: contain ");
    }
}