package com.aa183.karsanapremananda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.aa183.karsanapremananda.InputActivity.loadImagefromInternalStorage;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context ctx;
    private ArrayList<Songs> sgArr;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public SongAdapter(Context ctx, ArrayList<Songs> sgArr) {
        this.ctx = ctx;
        this.sgArr = sgArr;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.list_songs, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Songs sg = sgArr.get(position);
        holder.songID = sg.getSongID();
        holder.tbTitle.setText(sg.getSongTitle());
        holder.tbArtist.setText(sg.getSongArtist());
        holder.songImage = sg.getSongImage();
        holder.songAlbum = sg.getSongAlbum();
        holder.songDate = df.format(sg.getSongDate());
        holder.songLyrics = sg.getSongLyrics();

        loadImagefromInternalStorage(holder.songImage, ctx, holder.tbImage);
    }

    @Override
    public int getItemCount() {
        return sgArr.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView tbImage;
        private TextView tbTitle, tbArtist;
        private int songID;
        private String songImage, songAlbum, songDate, songLyrics;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            tbImage = itemView.findViewById(R.id.tb_image);
            tbTitle = itemView.findViewById(R.id.tb_title);
            tbArtist = itemView.findViewById(R.id.tb_artist);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent fetchDetail = new Intent(ctx, DetailActivity.class);
            fetchDetail.putExtra("SONG_ID", songID);
            fetchDetail.putExtra("SONG_TITLE", tbTitle.getText().toString());
            fetchDetail.putExtra("SONG_ARTIST", tbArtist.getText().toString());
            fetchDetail.putExtra("SONG_IMAGE", songImage);
            fetchDetail.putExtra("SONG_ALBUM", songAlbum);
            fetchDetail.putExtra("SONG_DATE", songDate);
            fetchDetail.putExtra("SONG_LYRICS", songLyrics);
            ctx.startActivity(fetchDetail);
        }
    }
}
