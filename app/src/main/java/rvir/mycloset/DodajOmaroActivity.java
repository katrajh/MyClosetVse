package rvir.mycloset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DodajOmaroActivity extends AppCompatActivity {

    EditText naziv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_omaro);

        naziv = (EditText) findViewById(R.id.et_nazivOmare);

    }

    public void gumb_dodajOmaro(View view){
        showDialog(this, "Potrditev", "Ali je vnos podatkov pravilen?");
    }

    public void showDialog(Activity activity, String naslov, CharSequence sporocilo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (naslov != null) {
            builder.setTitle(naslov);
        }

        builder.setMessage(sporocilo);
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(naziv.getText().toString().length() !=0){
                    Toast.makeText(DodajOmaroActivity.this, "Podatki uspešno vnešeni!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DodajOmaroActivity.this, SeznamOmarActivity.class);

                    intent.putExtra("naziv", naziv.getText().toString());

                    DodajOmaroActivity.this.startActivity(intent);
                }else {
                    Toast.makeText(DodajOmaroActivity.this, "Vnesti morate podatke!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Ne", null);
        builder.show();

    }






}
