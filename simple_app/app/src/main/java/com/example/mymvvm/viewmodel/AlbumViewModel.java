package com.example.mymvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumViewModel extends ViewModel {
    MutableLiveData<List<Album>> albumName = new MutableLiveData<>();

    public MutableLiveData<List<Album>> getAlbumName() {
        return albumName;
    }
    public void filterAlbum(){
        albumName.setValue(Helper.albumName());
    }
}
