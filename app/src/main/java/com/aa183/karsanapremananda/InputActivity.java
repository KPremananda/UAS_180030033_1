package com.aa183.karsanapremananda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lgdState;
    private EditText iptTitle, iptArtist, iptAlbum, iptDate, iptLyrics;
    private ImageView iptImage;
    private DBHandler dbHandler;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private boolean isUpdate = false;
    private int songID = 0;
    private String songDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        lgdState = findViewById(R.id.lgd_state);
        iptTitle = findViewById(R.id.ipt_title);
        iptArtist = findViewById(R.id.ipt_artist);
        iptAlbum = findViewById(R.id.ipt_album);
        iptDate = findViewById(R.id.ipt_date);
        iptLyrics = findViewById(R.id.ipt_lyrics);
        iptImage = findViewById(R.id.ipt_image);

        dbHandler = new DBHandler(this);
        Intent getInputState = getIntent();
        Bundle songDat = getInputState.getExtras();
        if(songDat.getString("INPUT_STATE").equals("UPDATE")){
            isUpdate = true;
            lgdState.setText("Ubah Lagu");
            songID = songDat.getInt("SONG_ID");
            iptTitle.setText(songDat.getString("SONG_TITLE"));
            iptArtist.setText(songDat.getString("SONG_ARTIST"));
            iptAlbum.setText(songDat.getString("SONG_ALBUM"));
            iptDate.setText(songDat.getString("SONG_DATE"));
            iptLyrics.setText(songDat.getString("SONG_LYRICS"));
            loadImagefromInternalStorage(songDat.getString("SONG_IMAGE"), getApplicationContext(), iptImage);
        } else {
            isUpdate = false;
            lgdState.setText("Tambah Lagu");
        }
        iptDate.setOnClickListener(this);
        iptImage.setOnClickListener(this);
    }

    private void pickImage(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult actRslt = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                try {
                    Uri imgUri = actRslt.getUri();
                    InputStream imgStm = getContentResolver().openInputStream(imgUri);
                    Bitmap imgSelected = BitmapFactory.decodeStream(imgStm);
                    String loc = saveImagetoInternalStorage(imgSelected, getApplicationContext());
                    loadImagefromInternalStorage(loc, getApplicationContext(), iptImage);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Tidak dapat mengambil gambar", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Belum ada gambar yang dipilih", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImagetoInternalStorage(Bitmap bmp, Context ctx){
        ContextWrapper ctxWrap = new ContextWrapper(ctx);
        File file = ctxWrap.getDir("images", MODE_PRIVATE);
        String uid = UUID.randomUUID().toString();
        file = new File(file, "cover-" + uid + ".jpg");
        try {
            OutputStream outStm = null;
            outStm = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStm);
            outStm.flush();
            outStm.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        Uri storedImg = Uri.parse(file.getAbsolutePath());
        return storedImg.toString();
    }

    public static void loadImagefromInternalStorage(String imgLoc, Context ctx, ImageView img){
        try {
            File file = new File(imgLoc);
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(file));
            img.setImageBitmap(bmp);
            img.setContentDescription(imgLoc);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.mn_input_save){
            inputSong();
        }
        return super.onOptionsItemSelected(item);
    }

    private void inputSong(){
        String songTitle, songArtist, songAlbum, songImage, songLyrics;
        Date songDate = new Date();

        songTitle = iptTitle.getText().toString();
        songArtist = iptArtist.getText().toString();
        songAlbum = iptAlbum.getText().toString();
        songImage = iptImage.getContentDescription().toString();
        songLyrics = iptLyrics.getText().toString();

        try {
            songDate = df.parse(iptDate.getText().toString());
        } catch (ParseException e){
            e.printStackTrace();
        }

        Songs sg = new Songs(
                songID, songTitle, songArtist, songAlbum, songDate, songImage, songLyrics
        );

        if(isUpdate == true){
            dbHandler.updSong(sg);
            Toast.makeText(this, "Berhasil memperbaharui data lagu", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.addSong(sg);
            Toast.makeText(this, "Berhasil menambahkan data lagu", Toast.LENGTH_SHORT).show();
        }
        Intent reStart = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(reStart);

    }

    private void selectDate(){
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog datePick = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                songDate = dayOfMonth + "/" + (month+1) + "/" + year;
                iptDate.setText(songDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePick.show();
    }

    @Override
    public void onClick(View v) {
        int iv = v.getId();

        if(iv == R.id.ipt_image){
            pickImage();
        } else if(iv == R.id.ipt_date){
            selectDate();
        }
    }
}
