package rvir.mycloset;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DodajOblekoActivity extends AppCompatActivity {
    private Button buttonDodajSliko;
    private ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    Oblacilo oblacilo=new Oblacilo();
    LetniCasi letniCasi=new LetniCasi();
    AppDB db;
    ArrayList<String> list;
    ArrayList<String> arrayList;
    Spinner policaSpinner;
    EditText naziv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_obleko);
        buttonDodajSliko=(Button)findViewById(R.id.gumbDodajSliko);
        imageView=(ImageView)findViewById(R.id.imageView);
        Spinner omaraSpinner=findViewById(R.id.spinnerOmara);
        policaSpinner=findViewById(R.id.spinnerPolica);
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "rvir")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        //polnjenje spinnerja omar


        List<Omara> list_omara = db.omaraDao().getAll();


        list = new ArrayList<>();

        if(list_omara.size() == 0) {
            Toast.makeText(DodajOblekoActivity.this, "Najprej dodajte omare!", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i=0; i<list_omara.size(); i++) {
                String nazivO = list_omara.get(i).getNaziv();

                list.add(nazivO);

                SpinnerAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);



                omaraSpinner.setAdapter(listAdapter);
            }
        }
        omaraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                oblacilo.setTk_omara((int)id+1);
                polnjenjeSpinnerja((int)id+1);
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(DodajOblekoActivity.this, "Izberite omaro", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void shraniOblacilo(View view) {
        Intent intent = new Intent(this, SeznamOblekActivity.class);
        naziv = (EditText)findViewById(R.id.gumbNaziv);
        oblacilo.setNaziv(DodajOblekoActivity.this.naziv.getText().toString());

        Spinner vrstaSpinner=(Spinner) findViewById(R.id.spinnerVrsta);
        String vrsta = vrstaSpinner.getSelectedItem().toString().toLowerCase();
        oblacilo.setVrsta(vrsta);

        Spinner priloznostSpinner=(Spinner) findViewById(R.id.spinnerPriloznost);
        String priloznost = priloznostSpinner.getSelectedItem().toString().toLowerCase();
        oblacilo.setPriloznost(priloznost);

        Spinner pomladJesenSpinner=(Spinner) findViewById(R.id.spinnerPomladJesen);
        long pomladJesen = pomladJesenSpinner.getSelectedItemId();
        oblacilo.setPomladJesen((int)pomladJesen);

        Spinner poletjeSpinner=(Spinner) findViewById(R.id.spinnerPoletje);
        long poletje = poletjeSpinner.getSelectedItemId();
        oblacilo.setPoletje((int)poletje);

        Spinner zimaSpinner=(Spinner) findViewById(R.id.spinnerZima);
        long zima = zimaSpinner.getSelectedItemId();
        oblacilo.setZima((int)zima);

        Spinner  policaSpinner=(Spinner) findViewById(R.id.spinnerPolica);
        long polica = policaSpinner.getSelectedItemId();
        oblacilo.setTk_polica((int)polica+1);

        if(oblacilo.getSlika()!=null&&oblacilo.getNaziv()!=null){
            Polica p = db.policaDao().getPolicaById(oblacilo.getTk_polica(), oblacilo.getTk_omara());
            int kapac = p.getKapaciteta();

            if (kapac == 0) {
                Toast.makeText(DodajOblekoActivity.this, "Polica je zapolnjena. Prosimo izberite drugo polico", Toast.LENGTH_LONG).show();
            }else {
                kapac = kapac - 1;
                p.setKapaciteta(kapac);
            }

            db.oblaciloDao().insert(oblacilo);
            db.policaDao().update(p);

            startActivity(intent);
        }else{
            Toast.makeText(DodajOblekoActivity.this, "Niste vnesli vseh podatkov", Toast.LENGTH_LONG).show();
        }


    }
    public void dodajSliko(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }



    }
    //polnjenje spinnerja polic
    public void polnjenjeSpinnerja(int idOmare) {
        final List<Polica> list_polica = db.policaDao().getAllIstaOmara(idOmare);

        arrayList = new ArrayList<>();

        if (list_polica.size() == 0) {
            Toast.makeText(DodajOblekoActivity.this, "Dodajte polico", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < list_polica.size(); i++) {
                String nazivP = list_polica.get(i).getNaziv();
                int kapac = list_polica.get(i).getKapaciteta();
                //kasneje št prostih
                arrayList.add(nazivP + "(" + kapac + ")");
                SpinnerAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                policaSpinner.setAdapter(listAdapter);


            }

        }
    }
    // iz Bitmap v Imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        setPic();
        galleryAddPic();

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        oblacilo.setSlika(mCurrentPhotoPath);
        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        System.out.println(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inSampleSize = 8;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        System.out.println(mCurrentPhotoPath);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println(mCurrentPhotoPath);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

}
