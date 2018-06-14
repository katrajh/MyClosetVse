package rvir.mycloset;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class KajNajOblecemActivity extends AppCompatActivity {
    Document doc;
    int intTemp;
    AppDB db;
    List<Kombinacija> kombinacijeList;
    ArrayList<Kombinacija> kombinacije;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaj_naj_oblecem);
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();



        TextView temperatura=findViewById(R.id.textTemparatura);

        //temperatura

        Element rootNode;

        final String url=("http://api.openweathermap.org/data/2.5/weather?q=Ljubljana&APPID=3b3da0e7b346d120501e92bef6099673&mode=xml&units=metric");
        final SAXBuilder builder = new SAXBuilder();
        try {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        doc = (Document) builder.build(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread.join();

            rootNode = doc.getRootElement();
            List<Element> list = rootNode.getChildren();
            String temp=rootNode.getChild("temperature").getAttributeValue("value");
            double dTemp=Double.parseDouble(temp);
            intTemp=Integer.valueOf((int) Math.round(dTemp));
            temperatura.setTextSize(30);
            temperatura.setText(intTemp+"°C");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //priloznost




    }
    public void isci(View view){
        Intent intent = new Intent(this, KajNajOblecemSeznamActivity.class);
        Spinner spinnerPriloznost = findViewById(R.id.spinnerPriloznost);
        String priloznost= spinnerPriloznost.getSelectedItem().toString();
        String letniCas;
        if(intTemp<8){
            letniCas="Zima";
        }else if(intTemp>20){
            letniCas="Poletje";
        }else{
            letniCas="Pomlad in jesen";
        }
        kombinacije=(ArrayList<Kombinacija>)db.kombinacijaDao().findbypriloznostLetniCas("Poletje",priloznost);
        intent.putParcelableArrayListExtra("kombinacije",kombinacije);
        startActivity(intent);
    }
}
