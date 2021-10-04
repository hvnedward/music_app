package com.example.mymvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymvvm.model.Singer;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;

import java.util.ArrayList;
import java.util.List;

public class SingerViewModel  extends ViewModel {
    private MutableLiveData<List<Singer>> singers = new MutableLiveData<>();



    public  MutableLiveData<List<Singer>> getSingers() {
        return singers;
    }
    public void filterSinger(){
        singers.setValue(Helper.singerName());
    }
}
