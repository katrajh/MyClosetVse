package rvir.mycloset;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

public class SeznamPolicActivity extends AppCompatActivity implements PopUpDialog_DodajPolico.DialogListener{

    Dialog dialog_dodajPolico;
    FloatingActionButton btn_dodaj;

    ListView listViewPolice;

    AppDB db;

    ArrayList<String> arrayList;
    int v_idO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_polic);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            v_idO = bundle.getInt("idO");
        }

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

        final List<Polica> list_polica = db.policaDao().getAllIstaOmara(v_idO);

        arrayList = new ArrayList<>();

        if(list_polica.size() == 0) {
            Toast.makeText(SeznamPolicActivity.this, "Tabela je prazna", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_polica.size(); i++) {
                String nazivP = list_polica.get(i).getNaziv();
                int kapac = list_polica.get(i).getKapaciteta();

                arrayList.add(nazivP+"\nKapaciteta: "+kapac+" oblačil");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                listViewPolice.setAdapter(listAdapter);
            }
        }


        // za posamezno polico ko kliknes

        listViewPolice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                List<Polica> listp = null;
                if(tkOmara != 0) {
                    listp = db.policaDao().getAllIstaOmara(tkOmara);
                }
                else {
                    listp = db.policaDao().getAllIstaOmara(v_idO);
                }


                int idP = +listp.get(i).getId();
                String nazivP = listp.get(i).getNaziv();
                int kapacP = listp.get(i).getKapaciteta();
                int idO = listp.get(i).getTk_omara();

                Log.w("LOG", "IdPolice: "+idP);

                Intent intent = new Intent(getApplicationContext(), UrejanjePoliceActivity.class);
                intent.putExtra("idP", idP);
                intent.putExtra("nazivP", nazivP);
                intent.putExtra("kapacP", kapacP);
                intent.putExtra("idO", idO);

                startActivity(intent);
                finish();

            }

        });

    }


    int tkOmara=0;

    @Override
    public void applyInput(String naziv, int kap, int tkIdO) {

        // SEZNAM POLIC

        tkOmara = tkIdO;

        List<Polica> list_polica = db.policaDao().getAllIstaOmara(tkIdO);

        arrayList = new ArrayList<>();

        if(list_polica.size() == 0) {
            Toast.makeText(SeznamPolicActivity.this, "Tabela je prazna", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_polica.size(); i++) {
                String nazivP = list_polica.get(i).getNaziv();
                int kapac = list_polica.get(i).getKapaciteta();

                arrayList.add(nazivP+"\nKapaciteta: "+kapac+" oblačil");
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                listViewPolice.setAdapter(listAdapter);
            }
        }

    }


    public void openDialog() {
        PopUpDialog_DodajPolico pop = new PopUpDialog_DodajPolico();
        Bundle bundle = new Bundle();
        bundle.putInt("idO", v_idO);
        pop.setArguments(bundle);
        pop.show(getSupportFragmentManager(), "dodaj polico dialog");
    }


}
