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
    long v_letniCas;
    long v_kategorija;
    String compareValueKat;
    String compareValueLcas;

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

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            idGumb = bundle.getInt("idGumb");

            Log.w("+++++++++ LOG DodajKomb", "idGumb v bundle: "+idGumb);

            idVrhnje = bundle.getInt("idVrhnje");
            idTop = bundle.getInt("idTop");
            idBottom = bundle.getInt("idBottom");

            v_kategorija = bundle.getLong("vKat");
            v_letniCas = bundle.getLong("vLetCas");
            compareValueKat = bundle.getString("compareValueKat");
            compareValueLcas = bundle.getString("compareValueLcas");

        }

        if(compareValueKat != null || compareValueLcas!= null){
            sp_letniCas.setSelection((int)v_kategorija);
            sp_letniCas.setSelection((int)v_letniCas);
        }

        Log.w("+++++++++ LOG DodajKomb", "Vrhnje: "+ idVrhnje);
        Log.w("+++++++++ LOG DodajKomb", "Top: "+ idTop);
        Log.w("+++++++++ LOG DodajKomb", "Bottom: "+ idBottom);
        Log.w("+++++++++ LOG DodajKomb", "kategorija: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "letni cas: "+ v_letniCas);
        Log.w("+++++++++ LOG DodajKomb", "compareValueKat: "+ compareValueKat);
        Log.w("+++++++++ LOG DodajKomb", "compareValueLcas: "+ compareValueLcas);

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

                    vrednosti(v_kategorija, v_letniCas);

                    String nazivK = et_nazivKomb.getText().toString();
                    String katK = sk;
                    String letniCasK = sl;
                    int top = idTop;
                    int vrhnje = idVrhnje;
                    int bottom = idBottom;

                    Log.w("+++ LOG v shrani", "Top: "+ top);
                    Log.w("+++ LOG v shrani", "Vrhnje: "+ vrhnje);
                    Log.w("+++ LOG v shrani", "Bottom: "+ bottom);


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

    public void spinnerVrednosti() {
        v_kategorija = sp_kategorija.getSelectedItemId();
        v_letniCas = sp_letniCas.getSelectedItemId();

        Log.w("+++++++++ LOG DodajKomb", "long kat: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "long lcas: "+ v_letniCas);
    }

    public void gumb_izberiVrhnje(View view){
        spinnerVrednosti();
        Log.w("+++++++++ LOG DodajKomb", "kategorija: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "letni cas: "+ v_letniCas);
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 1);
        intent.putExtra("vTop", idTop);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        intent.putExtra("vVrhnje", idVrhnje);
        startActivity(intent);
        finish();
    }

    public void gumb_izberiTop(View view){
        spinnerVrednosti();
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 2);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        intent.putExtra("vTop", idTop);
        startActivity(intent);
        finish();

    }

    public void gumb_izberiBottom(View view){
        spinnerVrednosti();
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 3);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("vTop", idTop);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        intent.putExtra("vBottom", idBottom);
        startActivity(intent);
        finish();
    }

    String sk;
    String sl;
    public void vrednosti(long k, long l) {

        if(k == 0) {
            sk = "Šport";
        }
        if(k == 1) {
            sk = "Vsakdanje";
        }
        if(k == 2) {
            sk = "Zabava";
        }
        if(k == 3) {
            sk = "Obleka";
        }

        if(l == 0) {
            sl = "Zima";
        }
        if(l == 1) {
            sl = "Pomlad in jesen";
        }
        if(l == 2) {
            sl = "Poletje";
        }
    }

}
