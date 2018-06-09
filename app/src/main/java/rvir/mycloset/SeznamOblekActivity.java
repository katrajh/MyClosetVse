package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamOblekActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    ListView listViewObleke;

    AppDB db;

    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_oblek);
        listViewObleke = (ListView) findViewById(R.id.listViewOmare);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        List<Oblacilo> list_obleka = db.oblaciloDao().getAll();

        list = new ArrayList<>();

        if(list_obleka.size() == 0) {
            Toast.makeText(SeznamOblekActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_obleka.size(); i++) {
                String nazivO = list_obleka.get(i).getNaziv();

                list.add(nazivO);

                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                listViewObleke.setAdapter(listAdapter);
            }
        }
    }


}
