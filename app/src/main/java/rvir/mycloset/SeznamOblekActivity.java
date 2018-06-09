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

public class SeznamOblekActivity extends AppCompatActivity {

    AppDB db;

    ListView listViewObleke;

    ArrayList<String> arrayList_oblacila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_oblek);


        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        listViewObleke = (ListView) findViewById(R.id.lv_obleke);

        final List<Oblacilo> list_oblacila = db.oblaciloDao().getAll();

        arrayList_oblacila = new ArrayList<>();

        if (list_oblacila.size() == 0) {
            Toast.makeText(SeznamOblekActivity.this, "V nobeni omari nimate oblačil.", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_oblacila.size(); i++) {
                String nazivO = list_oblacila.get(i).getNaziv();
                String prilO = list_oblacila.get(i).getPriloznost();
                String vrstaO = list_oblacila.get(i).getVrsta();

                arrayList_oblacila.add("Naziv: "+nazivO+"\nPriloznost: "+prilO+"\nVrsta: "+vrstaO);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList_oblacila);
                listViewObleke.setAdapter(listAdapter);
            }
        }


        listViewObleke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int idOb = list_oblacila.get(i).getId();

                Log.w("LOG", "IdOblacilo: "+idOb);

                Intent intent = new Intent(view.getContext(), DodajKombinacijoActivity.class);
                intent.putExtra("idOb", idOb);

                startActivity(intent);

                finish();

            }
        });

    }


}
