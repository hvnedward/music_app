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
import com.example.mymvvm.Interface.ISingerClick;
import com.example.mymvvm.R;
import com.example.mymvvm.model.Album;
import com.example.mymvvm.model.Singer;

import java.util.List;

public class SingerAdapter  extends RecyclerView.Adapter<SingerAdapter.SingerAdapterViewHolder>{
    List<Singer> singer;
    private ISingerClick iSingerClick;

    public SingerAdapter(ISingerClick listener) {
        this.iSingerClick = listener;
    }

    public void setSinger(List<Singer> data){
        this.singer = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SingerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);

        return new SingerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerAdapterViewHolder holder, int position) {
        Singer s = singer.get(position);
        if(singer!=null){
            holder.tvAlbumName.setText(s.getName());
            holder.tvNumber.setText(String.valueOf(s.getAmount()));
            holder.imageView.setImageResource(R.drawable.singer);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iSingerClick.onClick(s);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(singer!=null){
            return singer.size();
        }
        return 0;
    }

    public class SingerAdapterViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvAlbumName;
        private TextView tvNumber;
        private CardView cardView;
        public SingerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_song);
            tvAlbumName =itemView.findViewById(R.id.title_song);
            tvNumber = itemView.findViewById(R.id.singer_song);
            cardView = itemView.findViewById(R.id.card_item);



        }
    }
}
