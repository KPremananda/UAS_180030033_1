package com.aa183.karsanapremananda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "db_mylyrics";
    private static final String TABLE_SONGS = "tb_songs";
    private static final String COLUMN_SONG_ID = "song_id";
    private static final String COLUMN_SONG_TITLE = "song_title";
    private static final String COLUMN_SONG_ARTIST = "song_artist";
    private static final String COLUMN_SONG_ALBUM = "song_album";
    private static final String COLUMN_RELEASE_DATE = "song_date";
    private static final String COLUMN_ALBUM_COVER = "song_image";
    private static final String COLUMN_SONG_LYRICS = "song_lyrics";
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context ctx;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SONG_TITLE + " TEXT, "
                + COLUMN_SONG_ARTIST + " TEXT, "
                + COLUMN_SONG_ALBUM + " TEXT, "
                + COLUMN_RELEASE_DATE + " DATE, "
                + COLUMN_ALBUM_COVER + " TEXT, "
                + COLUMN_SONG_LYRICS + " TEXT);";
        db.execSQL(CREATE_TABLE_SONGS);
        initSongs(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_SONGS = "DROP TABLE IF EXISTS " + TABLE_SONGS;
        db.execSQL(DROP_TABLE_SONGS);
        onCreate(db);
    }

    public void addSong(Songs sg){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SONG_TITLE, sg.getSongTitle());
        cv.put(COLUMN_SONG_ARTIST, sg.getSongArtist());
        cv.put(COLUMN_SONG_ALBUM, sg.getSongAlbum());
        cv.put(COLUMN_RELEASE_DATE, df.format(sg.getSongDate()));
        cv.put(COLUMN_ALBUM_COVER, sg.getSongImage());
        cv.put(COLUMN_SONG_LYRICS, sg.getSongLyrics());

        db.insert(TABLE_SONGS, null, cv);
        db.close();
    }

    public void addSong(Songs sg, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SONG_TITLE, sg.getSongTitle());
        cv.put(COLUMN_SONG_ARTIST, sg.getSongArtist());
        cv.put(COLUMN_SONG_ALBUM, sg.getSongAlbum());
        cv.put(COLUMN_RELEASE_DATE, df.format(sg.getSongDate()));
        cv.put(COLUMN_ALBUM_COVER, sg.getSongImage());
        cv.put(COLUMN_SONG_LYRICS, sg.getSongLyrics());

        db.insert(TABLE_SONGS, null, cv);
    }

    public void updSong(Songs sg){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SONG_TITLE, sg.getSongTitle());
        cv.put(COLUMN_SONG_ARTIST, sg.getSongArtist());
        cv.put(COLUMN_SONG_ALBUM, sg.getSongAlbum());
        cv.put(COLUMN_RELEASE_DATE, df.format(sg.getSongDate()));
        cv.put(COLUMN_ALBUM_COVER, sg.getSongImage());
        cv.put(COLUMN_SONG_LYRICS, sg.getSongLyrics());

        db.update(TABLE_SONGS, cv, COLUMN_SONG_ID + "=?", new String[]{String.valueOf(sg.getSongID())});
        db.close();
    }

    public void delSong(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SONGS, COLUMN_SONG_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Songs> fetchSongs(){
        ArrayList<Songs> al =  new ArrayList<>();
        String q = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.rawQuery(q, null);
        if(cr.moveToFirst()){
            do {
                Date td = new Date();
                try {
                    td = df.parse(cr.getString(4));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Songs tmpSong = new Songs(
                        cr.getInt(0),
                        cr.getString(1),
                        cr.getString(2),
                        cr.getString(3),
                        td,
                        cr.getString(5),
                        cr.getString(6)
                );

                al.add(tmpSong);
            } while (cr.moveToNext());
        }
        return al;
    }

    private String storeInitImageFile(int id){
        String loc;
        Bitmap image = BitmapFactory.decodeResource(ctx.getResources(), id);
        loc = InputActivity.saveImagetoInternalStorage(image, ctx);
        return loc;
    }

    private void initSongs(SQLiteDatabase db){
        int id = 1;
        Date td = new Date();

        try {
            td = df.parse("25/03/2020");
        } catch (ParseException e){
            e.printStackTrace();
        }

        Songs song1 = new Songs(
                id,
                "Ano Hoshi no Mukou ni",
                "Takayanagi Tomoyo",
                "Koisuru Asteroid Sound Collection (Original Soundtrack)",
                td,
                storeInitImageFile(R.drawable.song1),
                "osanai koro miteta" +
                        "\n" +
                        "tōi hoshizora omou yo" +
                        "\n" +
                        "shimai konda yume wa" +
                        "\n" +
                        "dare ni mo akasenai mamani" +
                        "\n" +
                        "\n" +
                        "fumidasou kowagarazu ni" +
                        "\n" +
                        "hirogaru sekai kagirinai ano sora e" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "kirameku yume kakureteru no" +
                        "\n" +
                        "watashitachi no daijina negaigoto ano hoshi no mukō ni" +
                        "\n" +
                        "\n" +
                        "tomoshibi no yōni mune o terasu" +
                        "\n" +
                        "sasayaku kimi no koe fureru" +
                        "\n" +
                        "itsuka todoku yo" +
                        "mayowazu ni yukou" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "yozora ni toketeyuku" +
                        "\n" +
                        "sukitōru yōna ikizukai" +
                        "\n" +
                        "urumu hoshiakari ni" +
                        "\n" +
                        "terasareta kimi no yokogao" +
                        "\n" +
                        "\n" +
                        "nakanaide\n" +
                        "hō hō o nuguu te o\n" +
                        "\n" +
                        "kasanaru kimochi sotto chikazuku kokoro" +
                        "\n" +
                        "\n" +
                        "matataku hoshi miageteru no" +
                        "\n" +
                        "watashitachi no himeta omoi nobashita kono te no mukō ni" +
                        "\n" +
                        "\n" +
                        "kono sora no yōni sumikitteru" +
                        "\n" +
                        "kimi no miteiru michi no saki" +
                        "itsuka todoku yo " +
                        "mayowazu ni yukou" +
                        "\n" +
                        "\n" +
                        "kimi ga kureta kotoba" +
                        "\n" +
                        "kimi ga shimeshitekureta michi" +
                        "\n" +
                        "fuan ni natte mo kabe niatatte mo" +
                        "\n" +
                        "norikoeru tachiagaru akiramezu aruiteyuku kimi to" +
                        "\n" +
                        "\n" +
                        "kirameku yume kakushiteru no" +
                        "\n" +
                        "watashitachi no daijina negaigoto ano hoshi no mukō ni" +
                        "\n" +
                        "\n" +
                        "kagirinai hoshi ga futtekuru no" +
                        "\n" +
                        "me o tojita futari tsutsu mikomu" +
                        "\n" +
                        "itsuka todoku yo" +
                        "mayowazu ni yukou" +
                        "\n" +
                        "\n" +
                        "ano aoi hoshi no umi e"
        );
        addSong(song1, db);
        id++;

        try {
            td = df.parse("27/11/2019");
        } catch (ParseException e){
            e.printStackTrace();
        }

        Songs song2 = new Songs(
                id,
                "Ijintachi no Jikan",
                "Kusunoki Tomori",
                "Ijintachi no Jikan (Single) ASSASSINS PRIDE ED",
                td,
                storeInitImageFile(R.drawable.song2),
                "nee mama tte donna koi shita no?\n" +
                        "donna ai mitsuketa no?\n" +
                        "kikitai kedo kiicha ikenai\n" +
                        "sonna kanji ga shiteiru yo\n" +
                        "\n" +
                        "nee tenshi no you na hitotachi wa saisho kara kimatteru\n" +
                        "doushite? tte omou to kanashii ne\n" +
                        "ima made sou janakute\n" +
                        "\n" +
                        "futto kizuku tomodachi no naka de tokidoki uiteiru to\n" +
                        "tooku ni aru shiranai mori de potsun to tachitsukusu mitai\n" +
                        "minna ga suki demo ugokenakute\n" +
                        "damarikonde shimatta\n" +
                        "hoka no daremo mienai sonzai ni\n" +
                        "toikaketari shite miru\n" +
                        "kaiwa no you ni\n" +
                        "\n" +
                        "ijintachi to sugosu jikan\n" +
                        "itsuka wa habatakeru no ka na?\n" +
                        "mamorareteiru dake de tsuyoku wa narenai\n" +
                        "watashi mo mawari kara mitara ijin ka na?\n" +
                        "nee mama tte samishii toki dou hohoendeita no?\n" +
                        "\n" +
                        "tsukamitoru koto to ikinuite iku koto wo yamenai\n" +
                        "jibun to tatakau soko kara hajimeru to oshiete kureta koe\n" +
                        "hitori janai\n" +
                        "\n" +
                        "ijintachi to sugosu jikan\n" +
                        "itsuka wa habatakeru no ka na?\n" +
                        "ichiban tashikametai\n" +
                        "uso demo ii no to negatta\n" +
                        "yasashii kotae wa kaze no naka\n" +
                        "\n" +
                        "nee mama tte koishii toki dou tsutaeteita no?"
        );
        addSong(song2, db);
        id++;
    }
}
