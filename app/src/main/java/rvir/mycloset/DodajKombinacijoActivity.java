package rvir.mycloset;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class DodajKombinacijoActivity extends AppCompatActivity {

    AppDB db;
    Kombinacija kombinacija = new Kombinacija();

    Spinner sp_kategorija;
    Spinner sp_letniCas;

    EditText et_nazivKomb;

    // za izbor oblek (vrhnje, top, bottom)
    int idGumb;
    int idVrhnje;
    int idTop;
    int idBottom;

    // za prenos v inent.putExtra
    String v_letniCas;
    String v_kategorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_kombinacijo);

        // SPINNER za kategorijo
        sp_kategorija = (Spinner) findViewById(R.id.spinner_kategorija);

        ArrayAdapter<CharSequence> adapterKategorija = ArrayAdapter.createFromResource(this, R.array.arrPriloznost, android.R.layout.simple_spinner_item);
        adapterKategorija.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_kategorija.setAdapter(adapterKategorija);



        //SPINNER za letniCas
        sp_letniCas = (Spinner) findViewById(R.id.spinner_kombLetniCas);

        ArrayAdapter<CharSequence> adapterLetniCas = ArrayAdapter.createFromResource(this, R.array.arr_letniCasiKomb, android.R.layout.simple_spinner_item);
        adapterLetniCas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_letniCas.setAdapter(adapterLetniCas);


        et_nazivKomb = (EditText) findViewById(R.id.et_kombNaziv);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

// testne obleke
//        db.oblaciloDao().insert(new Oblacilo("picture", "pulover", "top", "vsakdanje", 0, 1, 1, 1, 3));
//        db.oblaciloDao().insert(new Oblacilo("picture2", "majica rdeca", "top", "vsakdanje", 1, 1, 0, 1, 2));
//        db.oblaciloDao().insert(new Oblacilo("picture3", "švic majca", "top", "Šport", 1, 1, 0, 1, 1));
//        db.oblaciloDao().insert(new Oblacilo("picture4", "jeans črne", "bottom", "vsakdanje", 0, 1, 1, 1, 2));
//        db.oblaciloDao().insert(new Oblacilo("picture5", "usnjena jakna črna", "vrhnje", "vsakdanje", 0, 1, 1, 1, 1));
//
//

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            idGumb = bundle.getInt("idGumb");

            Log.w("+++++++++ LOG DodajKomb", "idGumb v bundle: "+idGumb);

            idVrhnje = bundle.getInt("idVrhnje");
            idTop = bundle.getInt("idTop");
            idBottom = bundle.getInt("idBottom");

            //v_kategorija = bundle.getString("vKat");
            //v_letniCas = bundle.getString("vLetCas");

        }

        v_kategorija = sp_kategorija.getSelectedItem().toString();
        v_letniCas = sp_letniCas.getSelectedItem().toString();

        Log.w("+++++++++ LOG DodajKomb", "Vrhnje: "+ idVrhnje);
        Log.w("+++++++++ LOG DodajKomb", "Top: "+ idTop);
        Log.w("+++++++++ LOG DodajKomb", "Bottom: "+ idBottom);
        Log.w("+++++++++ LOG DodajKomb", "kategorija: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "letni cas: "+ v_letniCas);

    }

    public void gumb_izberiVrhnje(View view){
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 1);
        intent.putExtra("vTop", idTop);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        startActivity(intent);
    }

    public void gumb_izberiTop(View view){
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 2);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        startActivity(intent);

    }

    public void gumb_izberiBottom(View view){
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 3);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("vTop", idTop);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        startActivity(intent);
    }


    // gumb shrani kombinacijo

    public void gumb_shraniKombinacijo(View view) {
        showDialogShrani(this, "Potrditev", "Ali želite shraniti takšno kombinacijo?");
    }

    public void showDialogShrani(Activity activity, String naslov, CharSequence msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if(naslov != null) {
            builder.setTitle(naslov);
        }

        builder.setMessage(msg);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(idGumb != 0) {

                    String nazivK = et_nazivKomb.getText().toString();
                    String katK = sp_kategorija.getSelectedItem().toString();
                    String letniCasK = sp_letniCas.getSelectedItem().toString();
                    int top = idTop;
                    int vrhnje = idVrhnje;
                    int bottom = idBottom;

                    kombinacija.setNaziv(nazivK);
                    kombinacija.setPriloznost(katK);
                    kombinacija.setLetniCas(letniCasK);
                    kombinacija.setTk_top(top);
                    kombinacija.setTk_povrhnje(vrhnje);
                    kombinacija.setTk_bottom(bottom);

                    db.kombinacijaDao().insertAll(kombinacija);

                    Intent intent = new Intent(getApplicationContext(), SeznamKombinacijActivity.class);

                    startActivity(intent);

                    finish();

                }
            }
        });

        builder.setNegativeButton("Ne", null);
        builder.show();

    }

}
