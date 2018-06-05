package rvir.mycloset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Eva on 19.3.2018.
 */

@Entity(tableName = "polica", foreignKeys = @ForeignKey(entity = Omara.class,
                                                        parentColumns = "id_omara",
                                                        childColumns = "tk_id_omara", onDelete = ForeignKey.CASCADE))
public class Polica {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_polica")
    private int id;

    @ColumnInfo(name = "polica_naziv")
    private String naziv;

    @ColumnInfo(name = "polica_kapaciteta")
    private int kapaciteta;

    @ColumnInfo(name = "tk_id_omara")
    public int tk_omara;

    public Polica(String naziv, int kapaciteta, int tk_omara) {
        this.naziv = naziv;
        this.kapaciteta = kapaciteta;
        this.tk_omara = tk_omara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKapaciteta() {
        return kapaciteta;
    }

    public void setKapaciteta(int kapaciteta) {
        this.kapaciteta = kapaciteta;
    }

    public int getTk_omara() {
        return tk_omara;
    }

    public void setTk_omara(int tk_omara) {
        this.tk_omara = tk_omara;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
