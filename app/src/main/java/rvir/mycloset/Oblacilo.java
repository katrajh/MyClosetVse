package rvir.mycloset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Eva on 19.3.2018.
 */
/*vse spremenljivke lahko zasedejo vrednosti:
    0-oblačilo se ne nosi v določenem letnem času
    1-oblačilo se nosi v določenem letnem času
    2-oblačilo se nosi v določenem letnem času v kombinaciji
    z drugim oblačilom
 */

@Entity(tableName = "oblacilo", foreignKeys ={
                                            @ForeignKey
                                                    (entity = Polica.class, parentColumns = "id_polica", childColumns = "tk_id_polica",onDelete = ForeignKey.CASCADE),
                                            @ForeignKey (entity = Omara.class, parentColumns = "id_omara", childColumns = "tk_id_omara",onDelete = ForeignKey.CASCADE)})
public class Oblacilo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_oblacilo")
    private int id;

    private String slika;
    private String naziv;
    private String vrsta;
    private String priloznost;
    private int poletje;
    private int pomladJesen;
    private int zima;

    @ColumnInfo(name = "tk_id_polica")
    private int tk_polica;
    @ColumnInfo(name = "tk_id_omara")
    private int tk_omara;

    public Oblacilo() {
    }

    public Oblacilo(String slika, String naziv, String vrsta, String priloznost, int poletje, int pomladJesen, int zima, int tk_polica, int tk_omara) {
        this.slika = slika;
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.priloznost = priloznost;
        this.poletje = poletje;
        this.pomladJesen = pomladJesen;
        this.zima = zima;
        this.tk_polica = tk_polica;
        this.tk_omara=tk_omara;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getPriloznost() {
        return priloznost;
    }

    public void setPriloznost(String priloznost) {
        this.priloznost = priloznost;
    }

    public int getTk_polica() {
        return tk_polica;
    }

    public void setTk_polica(int tk_polica) {
        this.tk_polica = tk_polica;
    }

    public int getPoletje() {
        return poletje;
    }

    public void setPoletje(int poletje) {
        this.poletje = poletje;
    }

    public int getPomladJesen() {
        return pomladJesen;
    }


    public void setPomladJesen(int pomladJesen) {
        this.pomladJesen=pomladJesen;
    }

    public int getZima() {
        return zima;
    }

    public void setZima(int zima) {
        this.zima = zima;
    }

    public int getTk_omara() {
        return tk_omara;
    }

    public void setTk_omara(int tk_omara) {
        this.tk_omara = tk_omara;
    }
}

