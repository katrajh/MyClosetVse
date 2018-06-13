package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamOblekZaKombinacijeActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    ListView listViewObleke;

    AppDB db;

    ArrayList<String> list;
    List<Oblacilo> list_obleka;

    // vrednost za gumbe ki jih pritisnes na activity dodajKombinacijo
    int val;
    int id_vrhnje;
    int id_top;
    int id_bottom;
    long v_kategorija;
    long v_letniCas;
    String compareValueKat;
    String compareValueLcas;

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
            val = bundle.getInt("vGumb");
            id_vrhnje = bundle.getInt("vVrhnje");
            id_top = bundle.getInt("vTop");
            id_bottom = bundle.getInt("vBottom");
            v_kategorija = bundle.getLong("vKat");
            v_letniCas = bundle.getLong("vLetCas");
        }

        // da se naredi list oblačil s temi pogoji

        vrednosti(v_kategorija, v_letniCas);

        if(v_letniCas == 0) {
            list_obleka = db.oblaciloDao().getAllZima(sk.toLowerCase());
        }
        if(v_letniCas == 1) {
            list_obleka = db.oblaciloDao().getAllPomladInJesen(sk.toLowerCase());
        }
        if(v_letniCas == 2) {
            list_obleka = db.oblaciloDao().getAllPoletje(sk.toLowerCase());
        }

//        list_obleka = db.oblaciloDao().getAll();

        list = new ArrayList<>();

        if(list_obleka.size() == 0) {
            Toast.makeText(SeznamOblekZaKombinacijeActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_obleka.size(); i++) {
                int idO = list_obleka.get(i).getId();
                String nazivO = list_obleka.get(i).getNaziv();
                String prilO = list_obleka.get(i).getPriloznost();
                String vrstaO = list_obleka.get(i).getVrsta();

                list.add("Id: "+idO+"\nNaziv: "+nazivO+"\nPriloznost: "+prilO+"\nVrsta: "+vrstaO);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewObleke.setAdapter(listAdapter);
            }
        }

        // seznam oblacil
        // ko kliknes na eno oblacilo
        listViewObleke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int idOb = list_obleka.get(i).getId();

                Log.w("LOG", "IdOblacilo: "+idOb);

                compareValueKat = sk;
                compareValueLcas = sl;

                Intent intent = new Intent(view.getContext(), DodajKombinacijoActivity.class);

                if(val == 1) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idVrhnje", idOb);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                }
                if(val == 2) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idTop", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                }
                if(val == 3) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idBottom", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                    intent.putExtra("compareValueKat", compareValueKat);
                    intent.putExtra("compareValueLcas", compareValueLcas);
                }

                startActivity(intent);
                finish();

            }
        });

    }

    String sk;      //string kategorija
    String sl;      //string letni cas

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
        if(l== 1) {
            sl = "Pomlad in jesen";
        }
        if(l == 2) {
            sl = "Poletje";
        }
    }

}
