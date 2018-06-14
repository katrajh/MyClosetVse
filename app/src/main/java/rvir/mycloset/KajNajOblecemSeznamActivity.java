package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class KajNajOblecemSeznamActivity extends AppCompatActivity {
    AppDB db;
    ArrayList<Kombinacija> list_kombinacij;
    ArrayList<Oblacilo> oblekeKombinacije;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaj_naj_oblecem_seznam);
        RecyclerView recyclerView =findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(false);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        Bundle data = getIntent().getExtras();


        if (data != null) list_kombinacij = data.getParcelableArrayList("kombinacije");


        if(list_kombinacij.size() == 0)
            Toast.makeText(KajNajOblecemSeznamActivity.this, "Najprej dodajte kombinacije!", Toast.LENGTH_LONG).show();
        else {
            for (int i=0; i<list_kombinacij.size(); i++) {
                oblekeKombinacije= new ArrayList<>();
                int idTop=list_kombinacij.get(i).getTk_top();
                int idBottom=list_kombinacij.get(i).getTk_bottom();
                int idPovrhnje=list_kombinacij.get(i).getTk_povrhnje();
                oblekeKombinacije.add(db.oblaciloDao().findByID(idTop));
                if(idPovrhnje!=-1){
                    oblekeKombinacije.add(db.oblaciloDao().findByID(idPovrhnje));
                }if(idBottom!=-1){
                    oblekeKombinacije.add(db.oblaciloDao().findByID(idBottom));
                }


                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);

                recyclerView.setLayoutManager(layoutManager);

                ArrayList<CreateList> createLists = prepareData();

                GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), createLists);
                recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);

            }
        }



    }
    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< oblekeKombinacije.size(); i++){
            CreateList createList = new CreateList();
            Omara omara =db.omaraDao().getOmaraById(oblekeKombinacije.get(i).getTk_omara());
            Polica polica =db.policaDao().getPolicaById(oblekeKombinacije.get(i).getTk_polica(),oblekeKombinacije.get(i).getTk_omara());

            createList.setImage_title("Omara: "+omara.getNaziv()+"\nPolica: "+polica.getNaziv());
            createList.setImage_ID(oblekeKombinacije.get(i).getSlika());
            theimage.add(createList);
        }
        return theimage;
    }
}
