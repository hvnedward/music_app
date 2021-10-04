package com.example.mymvvm.adapter;



import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.Interface.ISongClick;
import com.example.mymvvm.R;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{
    List<Song> songs;
    ISongClick iSongClick;
    Context context;
    public SongAdapter(ISongClick listener, Context t){
        iSongClick = listener;
        context = t;
    }
    public void setData(List<Song> data){
        this.songs = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
                Song song = songs.get(position);
   if(song!=null){
       holder.tvName.setText(song.getTitle());
       holder.tvSinger.setText(song.getSinger());
       Bitmap bt = Helper.getAlbumart(song.getThumbnail(), context);
       if(bt ==null){
           holder.imgSong.setImageResource(R.drawable.ic_baseline_music_note_24);
       }
       else{
           holder.imgSong.setImageBitmap(bt);
       }


       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               iSongClick.clickASong(song);
           }
       });
   }

    }

    @Override
    public int getItemCount() {
        if(songs!=null){
            return songs.size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSong;
        TextView tvName, tvSinger;
        CardView cardView;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item);
            imgSong = itemView.findViewById(R.id.img_song);
            tvName =itemView.findViewById(R.id.title_song);
            tvSinger = itemView.findViewById(R.id.singer_song);
        }
    }
}
