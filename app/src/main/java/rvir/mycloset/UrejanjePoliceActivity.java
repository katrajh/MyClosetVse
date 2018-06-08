package rvir.mycloset;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UrejanjePoliceActivity extends AppCompatActivity {

    AppDB db;

    EditText et_nazivP;
    EditText et_kapacitetaP;
    ListView listViewObleke;
    Button btn_shrani;

    int v_idP;
    String v_nazivP;
    int v_kapacP;
    int v_tkIdO;

    ArrayList<String> arrayList_oblacila;
    List<Polica> policaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urejanje_police);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            v_idP = bundle.getInt("idP");
            v_nazivP = bundle.getString("nazivP");
            v_kapacP = bundle.getInt("kapacP");
            v_tkIdO = bundle.getInt("idO");
        }

        et_nazivP = (EditText) findViewById(R.id.et_uNazivP);
        et_kapacitetaP = (EditText) findViewById(R.id.et_uKapacitetaP);
        btn_shrani = (Button) findViewById(R.id.btn_shraniSpr);

        listViewObleke = (ListView) findViewById(R.id.lv_seznamOblacil);

        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        policaList = db.policaDao().getAllIstaOmara(v_tkIdO);

        Log.w("LOG ", "Polica id: "+v_idP);
        Log.w("LOG ", "Polica naziv: "+v_nazivP);
        Log.w("LOG ", "Polica kapaciteta: "+v_kapacP);
        Log.w("LOG ", "Polica tk_omara: "+v_tkIdO);


        et_nazivP.setText(v_nazivP);
        et_kapacitetaP.setText(Integer.toString(v_kapacP));

        // SEZNAM OBLACIL

        final List<Oblacilo> list_oblacila = db.oblaciloDao().getAllIstaPolica(v_idP);

        arrayList_oblacila = new ArrayList<>();

        if (list_oblacila.size() == 0) {
            Toast.makeText(UrejanjePoliceActivity.this, "V omari še nimate oblačil.", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_oblacila.size(); i++) {
                String nazivO = list_oblacila.get(i).getNaziv();

                arrayList_oblacila.add(nazivO);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList_oblacila);
                listViewObleke.setAdapter(listAdapter);
            }
        }


        // za posamezne obleke ko kliknes na ene
        // prestavi te na activity urejanje oblacila

        listViewObleke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String idOb = ""+list_oblacila.get(i).getId();

                Log.w("LOG", "IdOblacilo: "+idOb);

                Intent intent = new Intent(view.getContext(), UrejanjeOblacilaActivity.class);
                intent.putExtra("idOb", idOb);

                startActivity(intent);

            }
        });

        // gumb DELETE ONE (brisanje trenutne omare)

        final FloatingActionButton fab_deleteOne = (FloatingActionButton) findViewById(R.id.fab_deleteP);
        fab_deleteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.policaDao().deleteOne(v_idP);

                startActivity(new Intent(getApplicationContext(), SeznamPolicActivity.class));
                finish();
            }
        });

    }

    public void gumb_shraniSpr(View view) {
        showDialogShrani(this, "Potrditev", "Ali je vnos podatkov pravilen?");
    }

    public void showDialogShrani(Activity activity, String naslov, CharSequence msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if(naslov != null) {
            builder.setTitle(naslov);
        }

        builder.setMessage(msg);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(et_nazivP.toString().length() != 0) {

                    String nazivP = et_nazivP.getText().toString();
                    int kapacitetaP = Integer.parseInt(et_kapacitetaP.getText().toString());

                    db.policaDao().updateOne(v_idP, nazivP, kapacitetaP, v_tkIdO);

                    Log.w("LOG", "nazivP: "+nazivP+", kapac: "+kapacitetaP+", tk_idOmare:"+v_tkIdO);

                    Toast.makeText(UrejanjePoliceActivity.this, "Omara uspešno urejena!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), SeznamPolicActivity.class);
                    intent.putExtra("idO", v_tkIdO);
                    startActivity(intent);
                    finish();
                }
            }
        });

        builder.setNegativeButton("Ne", null);
        builder.show();

    }


}
