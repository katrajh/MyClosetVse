package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    List<Oblacilo> list_obleka;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam_oblek);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(false);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        list_obleka = db.oblaciloDao().getAll();

        list = new ArrayList<>();

        if(list_obleka.size() == 0) {
            Toast.makeText(SeznamOblekActivity.this, "Tabela je prazna!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_obleka.size(); i++) {
                String nazivO = list_obleka.get(i).getNaziv();

                list.add(nazivO);


                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);
                ArrayList<CreateList> createLists = prepareData();
                GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), createLists);
                recyclerView.setAdapter(adapter);
            }
        }



    }
    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< list_obleka.size(); i++){
            CreateList createList = new CreateList();
            createList.setImage_title(list_obleka.get(i).getNaziv());
            createList.setImage_ID(list_obleka.get(i).getSlika());
            theimage.add(createList);
        }
        return theimage;
    }
}



