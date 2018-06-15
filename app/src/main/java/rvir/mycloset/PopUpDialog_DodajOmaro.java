package rvir.mycloset;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PopUpDialog_DodajOmaro extends AppCompatDialogFragment {

    private EditText et_naziv;

    private DialogListener dialogListener;

    AppDB db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        db = Room.databaseBuilder(getContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_dodaj_omaro, null);

        et_naziv = view.findViewById(R.id.et_nazivOmare);

        builder.setView(view)
                .setTitle("Dodajanje nove omare")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        db.omaraDao().insert(new Omara(et_naziv.getText().toString()));

                        dialogListener.applyInput(et_naziv.getText().toString());
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
        void applyInput(String naziv);
    }
}
