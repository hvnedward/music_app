package com.example.mymvvm.view.fragment;

import static com.example.mymvvm.Constants.SONG_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymvvm.Interface.ISongClick;
import com.example.mymvvm.MainActivity;
import com.example.mymvvm.R;
import com.example.mymvvm.adapter.SongAdapter;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.service.MusicService;
import com.example.mymvvm.viewmodel.SongViewModel;

import java.util.ArrayList;
import java.util.List;


public class SongFragment extends Fragment implements ISongClick {
    private SongViewModel songViewModel;
    private SongAdapter songAdapter;
    private RecyclerView recyclerView;
    List<Song> tempSong = new ArrayList<>();


    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView = view.findViewById(R.id.song_lv);
        songAdapter = new SongAdapter(this, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(songAdapter);
        songViewModel = new ViewModelProvider(this).get(SongViewModel.class);
        songViewModel.scanAllSong();
        songViewModel.getSongListMutableLiveData().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                MainActivity.storageSong.clear();
                MainActivity.storageSong.addAll(songs);
                MainActivity.songData.clear();
                MainActivity.songData.addAll(songs);
                songAdapter.setData(songs);

                Log.d("TAG", "onChanged: "+ songs.toString());
            }
        });
        return view;
    }



    @Override
    public void clickASong(Song song) {
        Intent intent = new Intent(getActivity(), MusicService.class);
        MainActivity.songData.clear();
        MainActivity.songData.addAll(MainActivity.storageSong);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SONG_KEY, song);
        intent.putExtra(SONG_KEY, bundle);
       // Toast.makeText(getActivity(), "Ddax nhan vap song", Toast.LENGTH_SHORT).show();
        getActivity().startService(intent);
    }
}