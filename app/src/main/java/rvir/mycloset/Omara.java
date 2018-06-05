package rvir.mycloset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Eva on 19.3.2018.
 */

@Entity(tableName = "omara")
public class Omara {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_omara")
    private int id;

    @ColumnInfo(name = "naziv_omare")
    private String naziv;

    public Omara(String naziv) {
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
