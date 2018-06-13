package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SeznamKombinacijActivity extends AppCompatActivity {

    AppDB db;

    ArrayList<String> arrayList;

    ListView listViewKomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_kombinacij);

        listViewKomb = (ListView) findViewById(R.id.lv_kombinacije);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        List<Kombinacija> list_komb = db.kombinacijaDao().getAll();
        List<Oblacilo> list_oblacilo = db.oblaciloDao().getAll();

        arrayList = new ArrayList<>();

        if(list_komb.size() == 0) {
            Toast.makeText(SeznamKombinacijActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_komb.size(); i++) {
                String nazivK = list_komb.get(i).getNaziv();
                String prilK = list_komb.get(i).getPriloznost();
                String letniCasK = list_komb.get(i).getLetniCas();
                String topK = list_oblacilo.get(list_komb.get(i).getTk_top()).getNaziv();
                String vrhnjeK = list_oblacilo.get(list_komb.get(i).getTk_povrhnje()).getNaziv();
                String bottomK = list_oblacilo.get(list_komb.get(i).getTk_bottom()).getNaziv();

                arrayList.add("Naziv: "+nazivK+"\nPriloznost: "+prilK+"\nVrhnje: "+vrhnjeK+"\nTop: "+topK+"\nBottom: "+bottomK);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                listViewKomb.setAdapter(listAdapter);
            }
        }



    }
}
