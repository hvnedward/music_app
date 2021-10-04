package com.example.mymvvm.view.fragment;

import static com.example.mymvvm.Constants.SONG_KEY;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymvvm.Interface.ISongClick;
import com.example.mymvvm.MainActivity;
import com.example.mymvvm.R;
import com.example.mymvvm.adapter.SongAdapter;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.service.MusicService;
import com.example.mymvvm.viewmodel.SongViewModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ISongClick {
    private EditText edSearch;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edSearch = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongAdapter(this, getActivity());
        SongViewModel songViewModel = new ViewModelProvider(getActivity()).get(SongViewModel.class);
        songViewModel.searchSOng("");
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                songViewModel.searchSOng(editable.toString().trim());
                songViewModel.getSongListMutableLiveData().observe(getActivity(), new Observer<List<Song>>() {
                    @Override
                    public void onChanged(List<Song> songs) {
                        Log.d("TAG", "onChanged: search " + songs.toString());
                        songAdapter.setData(songs);
                    }
                });
            }
        });


        recyclerView.setAdapter(songAdapter);
        return view;
    }

    SongAdapter tempSongAdapter;

    @Override
    public void clickASong(Song song) {
        Intent intent = new Intent(getActivity(), MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SONG_KEY, song);
        intent.putExtra(SONG_KEY, bundle);
        MainActivity.songData.clear();
        MainActivity.songData.addAll(MainActivity.storageSong);
        getActivity().startService(intent);

    }
}