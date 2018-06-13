package rvir.mycloset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UrejanjeOblacilaActivity extends AppCompatActivity {

    int v_idOblacila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urejanje_oblacila);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            v_idOblacila = bundle.getInt("idOb");
        }

    }
}
