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
    String v_kategorija;
    String v_letniCas;

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
            v_kategorija = bundle.getString("vKat");
            v_letniCas = bundle.getString("vLetCas");
        }

        // da se naredi list oblačil s temi pogoji

        /*
        if(v_letniCas == "Poletje") {
            list_obleka = db.oblaciloDao().getAllPoletje(v_kategorija);
        }
        if(v_letniCas == "Pomlad in jesen") {
            list_obleka = db.oblaciloDao().getAllPomladInJesen(v_kategorija);
        }
        if(v_letniCas == "Zima") {
            list_obleka = db.oblaciloDao().getAllZima(v_kategorija);
        }
        */

        list_obleka = db.oblaciloDao().getAll();

        list = new ArrayList<>();

        if(list_obleka.size() == 0) {
            Toast.makeText(SeznamOblekZaKombinacijeActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_obleka.size(); i++) {
                String nazivO = list_obleka.get(i).getNaziv();
                String prilO = list_obleka.get(i).getPriloznost();
                String vrstaO = list_obleka.get(i).getVrsta();

                list.add("Naziv: "+nazivO+"\nPriloznost: "+prilO+"\nVrsta: "+vrstaO);
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

                Intent intent = new Intent(view.getContext(), DodajKombinacijoActivity.class);

                if(val == 1) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idVrhnje", idOb);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                }
                if(val == 2) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idTop", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idBottom", id_bottom);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                }
                if(val == 3) {
                    intent.putExtra("idGumb", val);
                    intent.putExtra("idBottom", idOb);
                    intent.putExtra("idVrhnje", id_vrhnje);
                    intent.putExtra("idTop", id_top);
                    intent.putExtra("vKat", v_kategorija);
                    intent.putExtra("vLetCas", v_letniCas);
                }

                startActivity(intent);

            }
        });



    }
}
