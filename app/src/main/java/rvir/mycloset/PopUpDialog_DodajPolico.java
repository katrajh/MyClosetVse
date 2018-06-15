package rvir.mycloset;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class PopUpDialog_DodajPolico extends AppCompatDialogFragment{

    private EditText et_naziv;
    private EditText et_kapac;

    private DialogListener dialogListener;

    int idO;

    AppDB db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        if(bundle != null) {
            idO = bundle.getInt("idO");
        }

        db = Room.databaseBuilder(getContext(), AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_dodaj_polico, null);

        et_naziv = view.findViewById(R.id.et_nazivPolice);
        et_kapac = view.findViewById(R.id.et_kapaciteta);

        builder.setView(view)
                .setTitle("Dodajanje nove omare")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String naziv = et_naziv.getText().toString();
                        int kapac = Integer.parseInt(et_kapac.getText().toString());
                        db.policaDao().insert(new Polica(naziv, kapac, idO));

                        dialogListener.applyInput(naziv, kapac, idO);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new  ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void applyInput(String naziv, int kap, int tkIdO);
    }
}
