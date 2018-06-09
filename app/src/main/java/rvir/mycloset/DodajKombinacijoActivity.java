package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DodajKombinacijoActivity extends AppCompatActivity {

    AppDB db;

    Spinner sp_kategorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_kombinacijo);

        sp_kategorija = (Spinner) findViewById(R.id.spinner_kategorija);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrPriloznost, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_kategorija.setAdapter(adapter);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        /*

        db.oblaciloDao().insert(new Oblacilo("picture", "pulover", "top", "vsakdanje", 0, 1, 1, 1, 3));
        db.oblaciloDao().insert(new Oblacilo("picture2", "majica rdeca", "top", "vsakdanje", 1, 1, 0, 1, 2));
        db.oblaciloDao().insert(new Oblacilo("picture3", "švic majca", "top", "Šport", 1, 1, 0, 1, 1));
        db.oblaciloDao().insert(new Oblacilo("picture4", "jeans črne", "bottom", "vsakdanje", 0, 1, 1, 1, 2));
        db.oblaciloDao().insert(new Oblacilo("picture5", "usnjena jakna črna", "vrhnje", "vsakdanje", 0, 1, 1, 1, 1));

        */

        Bundle bundle = getIntent().getExtras();

        //Log.w("LOG", "Id omare: "+bundle.getInt("idOb"));

    }


    public void gumb_izberiVrhnje(View view){
        Intent intent = new Intent(this, SeznamOblekActivity.class);
        startActivity(intent);
    }

    public void gumb_izberiTop(View view){
        Intent intent = new Intent(this, SeznamOblekActivity.class);
        startActivity(intent);

    }public void gumb_izberiBottom(View view){
        Intent intent = new Intent(this, SeznamOblekActivity.class);
        startActivity(intent);
    }
}
