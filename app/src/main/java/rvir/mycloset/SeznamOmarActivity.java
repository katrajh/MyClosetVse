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
import android.view.ContextMenu;
import android.view.MenuItem;
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

public class SeznamOmarActivity extends AppCompatActivity implements PopUpDialog_DodajOmaro.DialogListener {

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

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        List<Omara> list_omara = db.omaraDao().getAll();

        list = new ArrayList<>();

        if (list_omara.size() == 0) {
            Toast.makeText(SeznamOmarActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < list_omara.size(); i++) {
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

                Log.w("LOG", "IdO: " + idO);
                Log.w("LOG", "naziv: " + nazivO);

                finish();

                Intent intent = new Intent(getApplicationContext(), SeznamPolicActivity.class);
                intent.putExtra("idO", idO);
                //intent.putExtra("nazivO", nazivO);

                startActivity(intent);

            }
        });

        registerForContextMenu(listViewOmare);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.lv_omare) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Kaj želite storiti s to omaro?");
            menu.add("Izbriši omaro");
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Omara idO = db.omaraDao().getAll().get(info.position);

        db.omaraDao().delete(idO);

        startActivity(new Intent(getApplicationContext(), SeznamOmarActivity.class));
        finish();

        return true;
    }


    @Override
    public void applyInput(String naziv) {

        List<Omara> list_omara = db.omaraDao().getAll();

        list = new ArrayList<>();

        if (list_omara.size() == 0) {
            Toast.makeText(SeznamOmarActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < list_omara.size(); i++) {
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

}
