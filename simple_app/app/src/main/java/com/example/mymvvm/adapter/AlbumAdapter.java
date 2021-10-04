package com.example.mymvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymvvm.Interface.IAlbumClick;
import com.example.mymvvm.R;
import com.example.mymvvm.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumAdapterViewHolder>{
    List<Album> albums;
    private IAlbumClick iAlbumClick;

    public AlbumAdapter(IAlbumClick listener) {
        this.iAlbumClick = listener;
    }

    public void setAlbums(List<Album> data){
        this.albums = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);

        return new AlbumAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapterViewHolder holder, int position) {
        Album album = albums.get(position);
        if(album!=null){
            holder.tvAlbumName.setText(album.getName());
            holder.tvNumber.setText(String.valueOf(album.getAmount()));
            //holder.imageView.setImageBitmap(album.getThumb());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iAlbumClick.IClick(album);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(albums!=null){
            return albums.size();
        }
        return 0;
    }

    public class AlbumAdapterViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvAlbumName;
        private TextView tvNumber;
        private CardView cardView;
        public AlbumAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_song);
            tvAlbumName =itemView.findViewById(R.id.title_song);
            tvNumber = itemView.findViewById(R.id.singer_song);
            cardView = itemView.findViewById(R.id.card_item);



        }
    }
}
