package rvir.mycloset;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamOmarActivity extends AppCompatActivity implements PopUpDialog_DodajOmaro.DialogListener{

    Dialog dialog_dodajOmaro;
    FloatingActionButton btn_dodaj;

    ListView listViewOmare;

    AppDB db;

    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_omar);

        dialog_dodajOmaro = new Dialog(SeznamOmarActivity.this);

        btn_dodaj = (FloatingActionButton) findViewById(R.id.fabtn_dodajO);

        btn_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        listViewOmare = (ListView) findViewById(R.id.lv_omare);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        List<Omara> list_omara = db.omaraDao().getAll();

        list = new ArrayList<>();

        if(list_omara.size() == 0) {
            Toast.makeText(SeznamOmarActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_omara.size(); i++) {
                String nazivO = list_omara.get(i).getNaziv();

                list.add(nazivO);

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewOmare.setAdapter(listAdapter);
            }
        }

        // za posamezno omaro ko kliknes

        listViewOmare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String selected = (String) listViewOmare.getItemAtPosition(i);

                List<Omara> listo = db.omaraDao().getAll();

                int idO = listo.get(i).getId();
                String nazivO = listo.get(i).getNaziv();

                Log.w("LOG", "IdO: "+idO);
                Log.w("LOG", "naziv: "+nazivO);

                Intent intent = new Intent(getApplicationContext(), SeznamPolicActivity.class);
                intent.putExtra("idO", idO);
                //intent.putExtra("nazivO", nazivO);

                startActivity(intent);

            }
        });


        // gumb DELETE

        final FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.fab_deleteO);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.omaraDao().deleteAll();

                startActivity(new Intent(getApplicationContext(), SeznamOmarActivity.class));
            }
        });

    }


    @Override
    public void applyInput(String naziv) {

        List<Omara> list_omara = db.omaraDao().getAll();

        list = new ArrayList<>();

        if(list_omara.size() == 0) {
            Toast.makeText(SeznamOmarActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_omara.size(); i++) {
                String nazivO = list_omara.get(i).getNaziv();

                list.add(nazivO);

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewOmare.setAdapter(listAdapter);
            }
        }
    }



    // TA DELA ZA POP UP DIALOG !
    public void openDialog() {
        PopUpDialog_DodajOmaro pop = new PopUpDialog_DodajOmaro();
        pop.show(getSupportFragmentManager(), "dodaj omaro dialog");
    }

    /*
        to ne dela !!!! ugotovi zakaj !

    public void showDialog_dodajO (View view) {
        TextView tv_naslov;
        EditText et_nazivOmare;
        Button btn_dodajOmaro;
        dialog_dodajOmaro.setContentView(R.layout.popup_dodaj_omaro);
        tv_naslov = (TextView) dialog_dodajOmaro.findViewById(R.id.tv_naslovDo);
        btn_dodajOmaro = (Button) dialog_dodajOmaro.findViewById(R.id.btn_dodajOmaro);
        et_nazivOmare = (EditText) dialog_dodajOmaro.findViewById(R.id.et_nazivOmare);

        btn_dodajOmaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_dodajOmaro.dismiss();
            }
        });

        dialog_dodajOmaro.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_dodajOmaro.show();
    }

    */
}
