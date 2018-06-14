package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamOblekZaKombinacijeActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    ListView listViewObleke;

    AppDB db;

    ArrayList<Oblacilo> list;
    List<Oblacilo> list_obleka;

    // vrednost za gumbe ki jih pritisnes na activity dodajKombinacijo
    int id_gumb;
    int id_vrhnje;
    int id_top;
    int id_bottom;
    long v_kategorija;
    long v_letniCas;
    String compareValueKat;
    String compareValueLcas;

    String imgVrhnjeUrl;
    String imgTopUrl;
    String imgBottomUrl;

    String vrstaOblacila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_oblek_za_kombinacije);
        listViewObleke = (ListView) findViewById(R.id.lv_oblekeZaKomb);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        // bundle za pridobivanje vrednosti gumbov
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id_gumb = bundle.getInt("vGumb");
            id_vrhnje = bundle.getInt("vVrhnje");
            id_top = bundle.getInt("vTop");
            id_bottom = bundle.getInt("vBottom");
            v_kategorija = bundle.getLong("vKat");
            v_letniCas = bundle.getLong("vLetCas");

            imgVrhnjeUrl = bundle.getString("imgVrhnjeUrl");
            imgTopUrl = bundle.getString("imgTopUrl");
            imgBottomUrl = bundle.getString("imgBottomUrl");

        }

        // da se naredi list oblačil s temi pogoji

        vrednosti(v_kategorija, v_letniCas, id_gumb);

        if(id_gumb == 1){

            Log.w("Log v if", "log v if");

            if(v_letniCas == 0) {
                list_obleka = db.oblaciloDao().getAllPoletjeTopObleka(sk.toLowerCase());
            }
            if(v_letniCas == 1) {
                list_obleka = db.oblaciloDao().getAllPomladInJesenTopObleka(sk.toLowerCase());
            }
            if(v_letniCas == 2) {
                list_obleka = db.oblaciloDao().getAllZimaTopObleka(sk.toLowerCase());
            }
        }
        else {
            Log.w("Log v else", "log v else");
            if(v_letniCas == 0) {
                list_obleka = db.oblaciloDao().getAllPoletjeOstalo(sk.toLowerCase(), sVrsta);
            }
            if(v_letniCas == 1) {
                list_obleka = db.oblaciloDao().getAllPomladInJesenOstalo(sk.toLowerCase(), sVrsta);
            }
            if(v_letniCas == 2) {
                list_obleka = db.oblaciloDao().getAllZimaOstalo(sk.toLowerCase(), sVrsta);
            }
        }

//      list_obleka = db.oblaciloDao().getAll();


        Log.w("+++++++++ LOG Seznam", "idGumb: "+ id_gumb);
        Log.w("+++++++++ LOG Seznam", "listObleka.size: "+ list_obleka.size());
        Log.w("+++++++++ LOG Seznam", "v_letnicas: "+ v_letniCas);
        Log.w("+++++++++ LOG Seznam", "sk: "+ sk);
        Log.w("+++++++++ LOG Seznam", "sVrsta: "+ sVrsta);

        list = new ArrayList<>();

        if(list_obleka.size() == 0) {
            Toast.makeText(SeznamOblekZaKombinacijeActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_obleka.size(); i++) {

                list.add(list_obleka.get(i));
                OblaciloListAdapter listAdapter = new OblaciloListAdapter(this, R.layout.single_list_item, list);
                listViewObleke.setAdapter(listAdapter);
            }
        }

        // seznam oblacil
        // ko kliknes na eno oblacilo
        listViewObleke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int idOb = list_obleka.get(i).getId();
                String img = list_obleka.get(i).getSlika();
                vrstaOblacila = list_obleka.get(i).getVrsta();

                Log.w("LOG", "IdOblacilo: "+idOb);

                compareValueKat = sk;
                compareValueLcas = sl;


                Intent intent = new Intent(view.getContext(), DodajKombinacijoActivity.class);

                if(id_gumb == 1) {
                    intent.putExtra("idGumb", id_gumb);
                    intent.putExtra("idTop", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                    intent.putExtra("imgTopUrl", img);
                    intent.putExtra("imgVrhnjeUrl", imgVrhnjeUrl);
                    intent.putExtra("imgBottomUrl", imgBottomUrl);
                    intent.putExtra("vrstaOblacila", vrstaOblacila);
                }
                if(id_gumb == 2) {
                    intent.putExtra("idGumb", id_gumb);
                    intent.putExtra("idVrhnje", idOb);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                    intent.putExtra("imgVrhnjeUrl", img);
                    intent.putExtra("imgTopUrl", imgTopUrl);
                    intent.putExtra("imgBottomUrl", imgBottomUrl);
                }
                if(id_gumb == 3) {
                    intent.putExtra("idGumb", id_gumb);
                    intent.putExtra("idBottom", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                    intent.putExtra("imgVrhnjeUrl", imgVrhnjeUrl);
                    intent.putExtra("imgTopUrl", imgTopUrl);
                    intent.putExtra("imgBottomUrl", img);
                }

                startActivity(intent);

            }
        });

    }

    String sk;      //string kategorija
    String sl;      //string letni cas
    String sVrsta;

    public void vrednosti(long k, long l, int gumb) {

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

        if (gumb == 1){
            sVrsta = "top";
        }
        if (gumb == 2){
            sVrsta = "vrhnje";
        }
        if (gumb == 3){
            sVrsta = "bottom";
        }
    }

}
