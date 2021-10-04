package com.example.mymvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;

import java.util.List;

public class ContainAlbumViewModel  extends ViewModel {
    MutableLiveData<List<Song>> song = new MutableLiveData<>();

    public MutableLiveData<List<Song>> getSong() {
        return song;
    }
    public void getMusic(String album){
        song.setValue(Helper.songFromAlbum(album));
    }
}
