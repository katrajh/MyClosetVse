package rvir.mycloset;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Eva on 26.3.2018.
 */


@Database(entities = {Omara.class, Polica.class, Oblacilo.class, Kombinacija.class, LetniCasi.class},version=7)
public abstract class AppDB extends RoomDatabase {

    public abstract OmaraDao omaraDao();
    public abstract PolicaDao policaDao();

    public abstract OblaciloDao oblaciloDao();
    public abstract KombinacijaDao kombinacijaDao();
    public abstract LetniCasiDao letniCasiDao();

/*
    private static final String DB_NAME = "MyClosetBaza.db";
    private static volatile AppDB instance;

    static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDB.class,
                DB_NAME).build();
    }
*/


}
