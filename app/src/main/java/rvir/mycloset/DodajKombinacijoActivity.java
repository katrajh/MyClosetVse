package rvir.mycloset;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public class DodajKombinacijoActivity extends AppCompatActivity {

    AppDB db;
    Kombinacija kombinacija = new Kombinacija();

    Spinner sp_kategorija;
    Spinner sp_letniCas;

    ImageView imgViewVrhnje;
    ImageView imgViewTop;
    ImageView imgViewBottom;

    EditText et_nazivKomb;

    Button btnIzberiTop;
    Button btnIzberiVrhnje;
    Button btnIzberiBottom;

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

    String imgVrhnjeUrl;
    String imgTopUrl;
    String imgBottomUrl;

    String vrstaOblacila;

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

        imgViewVrhnje = (ImageView) findViewById(R.id.img_top);
        imgViewTop = (ImageView) findViewById(R.id.img_vrhnje);
        imgViewBottom = (ImageView) findViewById(R.id.img_bottom);

        btnIzberiTop = (Button) findViewById(R.id.btn_kombVrhnja);
        btnIzberiVrhnje = (Button) findViewById(R.id.btn_kombTop);
        btnIzberiBottom = (Button) findViewById(R.id.btn_kombBottom);

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

            imgVrhnjeUrl = bundle.getString("imgVrhnjeUrl");
            imgTopUrl = bundle.getString("imgTopUrl");
            imgBottomUrl = bundle.getString("imgBottomUrl");

            vrstaOblacila = bundle.getString("vrstaOblacila");

        }

        List<Oblacilo> oblaciloList = db.oblaciloDao().getAll();

            if(vrstaOblacila == "obleka") {
                btnIzberiBottom.setEnabled(false);
                btnIzberiBottom.setClickable(false);
        }

        if(compareValueKat != null || compareValueLcas!= null){
            sp_kategorija.setSelection((int)v_kategorija);
            sp_letniCas.setSelection((int)v_letniCas);
        }

        imgViewTop= (ImageView) findViewById(R.id.img_top);
        imgViewVrhnje= (ImageView) findViewById(R.id.img_vrhnje);
        imgViewBottom= (ImageView) findViewById(R.id.img_bottom);

        if(imgTopUrl != null) {
            imgViewTop.setImageURI(Uri.parse(imgTopUrl));
        }
        if(imgVrhnjeUrl != null) {
            imgViewVrhnje.setImageURI(Uri.parse(imgVrhnjeUrl));
        }
        if(imgBottomUrl != null) {
            imgViewBottom.setImageURI(Uri.parse(imgBottomUrl));
        }


        Log.w("+++++++++ LOG DodajKomb", "Top: "+ idTop);
        Log.w("+++++++++ LOG DodajKomb", "Vrhnje: "+ idVrhnje);
        Log.w("+++++++++ LOG DodajKomb", "Bottom: "+ idBottom);
        Log.w("+++++++++ LOG DodajKomb", "kategorija: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "letni cas: "+ v_letniCas);
        Log.w("+++++++++ LOG DodajKomb", "compareValueKat: "+ compareValueKat);
        Log.w("+++++++++ LOG DodajKomb", "compareValueLcas: "+ compareValueLcas);
        Log.w("+++++++++ LOG DodajKomb", "imgTop: "+ imgTopUrl);
        Log.w("+++++++++ LOG DodajKomb", "imgVrhnje: "+ imgVrhnjeUrl);
        Log.w("+++++++++ LOG DodajKomb", "imgBottom: "+ imgBottomUrl);

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

                    et_nazivKomb = (EditText) findViewById(R.id.et_kombNaziv);

                    kombinacija = new Kombinacija();

                    vrednosti(v_kategorija, v_letniCas);

                    String nazivK = et_nazivKomb.getText().toString();
                    String katK = sk;
                    String letniCasK = sl;

                    if(idBottom == 0) {
                        idBottom = 10000;
                    }
                    if(idVrhnje== 0) {
                        idVrhnje = 10000;
                    }

                    Log.w("+++ LOG v shrani", "Top: "+ idTop);
                    Log.w("+++ LOG v shrani", "Vrhnje: "+ idVrhnje);
                    Log.w("+++ LOG v shrani", "Bottom: "+ idBottom);


                    kombinacija.setNaziv(nazivK);
                    kombinacija.setPriloznost(katK);
                    kombinacija.setLetniCas(letniCasK);
                    //kombinacija.setSlikaTop(imgTopUrl);
                    //kombinacija.setSlikaVrhnje(imgVrhnjeUrl);
                    //kombinacija.setSlikaBottom(imgBottomUrl);
                    kombinacija.setTk_top(idTop);
                    kombinacija.setTk_povrhnje(idVrhnje);
                    kombinacija.setTk_bottom(idBottom);

                    if(kombinacija.getNaziv() != null) {
                        db.kombinacijaDao().insertAll(kombinacija);

                        Intent intent = new Intent(getApplicationContext(), SeznamKombinacijActivity.class);

                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(DodajKombinacijoActivity.this, "Niste vnesli vseh podatkov", Toast.LENGTH_LONG).show();
                    }

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

    }

    public void gumb_izberiTop(View view){
        spinnerVrednosti();
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 1);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        intent.putExtra("vTop", idTop);
        intent.putExtra("imgTopUrl", imgTopUrl);
        intent.putExtra("imgVrhnjeUrl", imgVrhnjeUrl);
        intent.putExtra("imgBottomUrl", imgBottomUrl);
        startActivity(intent);
        finish();

    }

    public void gumb_izberiVrhnje(View view){
        spinnerVrednosti();
        Log.w("+++++++++ LOG DodajKomb", "kategorija: "+ v_kategorija);
        Log.w("+++++++++ LOG DodajKomb", "letni cas: "+ v_letniCas);
        Intent intent = new Intent(this, SeznamOblekZaKombinacijeActivity.class);
        intent.putExtra("vGumb", 2);
        intent.putExtra("vTop", idTop);
        intent.putExtra("vBottom", idBottom);
        intent.putExtra("vKat", v_kategorija);
        intent.putExtra("vLetCas", v_letniCas);
        intent.putExtra("vVrhnje", idVrhnje);
        intent.putExtra("imgTopUrl", imgTopUrl);
        intent.putExtra("imgVrhnjeUrl", imgVrhnjeUrl);
        intent.putExtra("imgBottomUrl", imgBottomUrl);
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
        intent.putExtra("imgTopUrl", imgTopUrl);
        intent.putExtra("imgVrhnjeUrl", imgVrhnjeUrl);
        intent.putExtra("imgBottomUrl", imgBottomUrl);
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
            sl = "Poletje";
        }
        if(l== 1) {
            sl = "Pomlad in jesen";
        }
        if(l == 2) {
            sl = "Zima";
        }
    }


//
//    private void setupImageLoader() {
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheOnDisk(true).cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(200)).build();
//
//        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
//                .diskCacheSize(100 * 1024 * 1024).build();
//
//        ImageLoader.getInstance().init(configuration);
//    }

}
