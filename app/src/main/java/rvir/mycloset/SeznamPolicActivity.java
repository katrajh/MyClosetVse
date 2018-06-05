package rvir.mycloset;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamPolicActivity extends AppCompatActivity implements PopUpDialog_DodajPolico.DialogListener{

    Dialog dialog_dodajPolico;
    FloatingActionButton btn_dodaj;

    ListView listViewPolice;

    AppDB db;

    ArrayList<String> list;
    int v_idO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_polic);

        v_idO = Integer.parseInt(getIntent().getStringExtra("idO"));

        dialog_dodajPolico = new Dialog(SeznamPolicActivity.this);

        btn_dodaj = (FloatingActionButton) findViewById(R.id.fabtn_dodajPolico);

        btn_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        listViewPolice = (ListView) findViewById(R.id.lv_police);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();


        // SEZNAM POLIC

        List<Polica> list_polica = db.policaDao().getAllIstaOmara(v_idO);

        list = new ArrayList<>();

        if(list_polica.size() == 0) {
            Toast.makeText(SeznamPolicActivity.this, "Tabela je prazna", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_polica.size(); i++) {
                String nazivP = list_polica.get(i).getNaziv().toString();
                int kap = list_polica.get(i).getKapaciteta();

                list.add(nazivP+"\n"+kap);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewPolice.setAdapter(listAdapter);
            }
        }


        // za posamezno polico ko kliknes
        /*

        listViewPolice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Intent intent = new Intent(view.getContext(), SeznamPolicActivity.class);
                intent.putExtra("nazivP", )

            }

        });

        */

    }


    @Override
    public void applyInput(String naziv, int kap) {

        // SEZNAM POLIC

        List<Polica> list_polica = db.policaDao().getAllIstaOmara(v_idO);

        list = new ArrayList<>();

        if(list_polica.size() == 0) {
            Toast.makeText(SeznamPolicActivity.this, "Tabela je prazna", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_polica.size(); i++) {
                String nazivP = list_polica.get(i).getNaziv().toString();
                int kapac = list_polica.get(i).getKapaciteta();

                list.add(nazivP+"\n"+kapac);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewPolice.setAdapter(listAdapter);
            }
        }

    }


    public void openDialog() {
        PopUpDialog_DodajPolico pop = new PopUpDialog_DodajPolico();
        Bundle bundle = new Bundle();
        bundle.putInt("idO", v_idO);
        pop.setArguments(bundle);
        pop.show(getSupportFragmentManager(), "dodaj pollico dialog");
    }


}
