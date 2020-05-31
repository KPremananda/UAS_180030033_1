package com.aa183.karsanapremananda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import static com.aa183.karsanapremananda.InputActivity.loadImagefromInternalStorage;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgSongTint, imgSongMini;
    private TextView txTitle, txArtist, txAlbum, txDate, txLyrics;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgSongTint = findViewById(R.id.img_song_tint);
        txTitle = findViewById(R.id.tx_title);
        txArtist = findViewById(R.id.tx_artist);
        imgSongMini = findViewById(R.id.img_song_mini);
        txAlbum = findViewById(R.id.tx_album);
        txDate = findViewById(R.id.tx_date);
        txLyrics = findViewById(R.id.tx_lyrics);

        Intent songData = getIntent();
        txTitle.setText(songData.getStringExtra("SONG_TITLE"));
        txArtist.setText(songData.getStringExtra("SONG_ARTIST"));
        txAlbum.setText(songData.getStringExtra("SONG_ALBUM"));
        txDate.setText(songData.getStringExtra("SONG_DATE"));
        txLyrics.setText(songData.getStringExtra("SONG_LYRICS"));
        String imgLoc = songData.getStringExtra("SONG_IMAGE");
        loadImagefromInternalStorage(imgLoc, getApplicationContext(), imgSongTint);
        loadImagefromInternalStorage(imgLoc, getApplicationContext(), imgSongMini);

        int ori = getResources().getConfiguration().orientation;
        if(ori == Configuration.ORIENTATION_LANDSCAPE){
            imgSongTint.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imgSongTint.setScaleType(ImageView.ScaleType.FIT_START);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent songData = getIntent();
        if(id == R.id.mn_detail_edit){
            Intent dpEdit = new Intent(getApplicationContext(), InputActivity.class);
            dpEdit.putExtra("INPUT_STATE", "UPDATE");
            dpEdit.putExtra("SONG_ID", songData.getIntExtra("SONG_ID",0));
            dpEdit.putExtra("SONG_TITLE", songData.getStringExtra("SONG_TITLE"));
            dpEdit.putExtra("SONG_ARTIST", songData.getStringExtra("SONG_ARTIST"));
            dpEdit.putExtra("SONG_ALBUM", songData.getStringExtra("SONG_ALBUM"));
            dpEdit.putExtra("SONG_DATE", songData.getStringExtra("SONG_DATE"));
            dpEdit.putExtra("SONG_IMAGE", songData.getStringExtra("SONG_IMAGE"));
            dpEdit.putExtra("SONG_LYRICS", songData.getStringExtra("SONG_LYRICS"));
            startActivity(dpEdit);
        } else if(id == R.id.mn_detail_del){
            removeSong(songData.getIntExtra("SONG_ID",0));
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeSong(int id){
        dbHandler = new DBHandler(this);
        dbHandler.delSong(id);
        Toast.makeText(this, "Berhasil menghapus data lagu", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            imgSongTint.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imgSongTint.setScaleType(ImageView.ScaleType.FIT_START);
        }
    }
}
