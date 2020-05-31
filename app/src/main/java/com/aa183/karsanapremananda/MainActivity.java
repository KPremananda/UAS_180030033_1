package com.aa183.karsanapremananda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Songs> sgArr = new ArrayList<>();
    private RecyclerView rcSongs;
    private SongAdapter songAdapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcSongs = findViewById(R.id.rc_songs);
        dbHandler = new DBHandler(this);
        viewSongs();
    }

    private void viewSongs() {
        sgArr = dbHandler.fetchSongs();
        songAdapter = new SongAdapter(this, sgArr);
        RecyclerView.LayoutManager lm = new GridLayoutManager(MainActivity.this, 2);
        rcSongs.setLayoutManager(lm);
        rcSongs.setAdapter(songAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.mn_main_add){
            Intent dpInput = new Intent(getApplicationContext(), InputActivity.class);
            dpInput.putExtra("INPUT_STATE","INSERT");
            startActivity(dpInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewSongs();
    }
}
