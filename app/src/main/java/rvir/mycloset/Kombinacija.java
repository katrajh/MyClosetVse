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

@Entity(tableName = "kombinacija",
        foreignKeys = {@ForeignKey(entity = Oblacilo.class, parentColumns = "id_oblacilo", childColumns = "tk_id_povrhnje", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Oblacilo.class, parentColumns = "id_oblacilo", childColumns = "tk_id_top", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Oblacilo.class, parentColumns = "id_oblacilo", childColumns = "tk_id_bottom", onDelete = ForeignKey.CASCADE)})
public class Kombinacija {
    @PrimaryKey
    @ColumnInfo(name = "id_kombinacija")
    private int id;

    private int poletje;
    private int pomlad;
    private int zima;
    private int jesen;
    private int priloznost;
    private int naziv;

    @ColumnInfo(name = "tk_id_povrhnje")
    private int tk_povrhnje;

    @ColumnInfo(name = "tk_id_top")
    private int tk_top;

    @ColumnInfo(name = "tk_id_bottom")
    private int tk_bottom;


    public Kombinacija(int poletje, int pomlad, int zima, int jesen, int priloznost, int naziv, int tk_povrhnje, int tk_top, int tk_bottom) {
        this.poletje = poletje;
        this.pomlad = pomlad;
        this.zima = zima;
        this.jesen = jesen;
        this.priloznost = priloznost;
        this.naziv = naziv;
        this.tk_povrhnje = tk_povrhnje;
        this.tk_top = tk_top;
        this.tk_bottom = tk_bottom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriloznost() {
        return priloznost;
    }

    public void setPriloznost(int priloznost) {
        this.priloznost = priloznost;
    }

    public int getPoletje() {
        return poletje;
    }

    public void setPoletje(int poletje) {
        this.poletje = poletje;
    }

    public int getPomlad() {
        return pomlad;
    }

    public void setPomlad(int pomlad) {
        this.pomlad = pomlad;
    }

    public int getZima() {
        return zima;
    }

    public void setZima(int zima) {
        this.zima = zima;
    }

    public int getJesen() {
        return jesen;
    }

    public void setJesen(int jesen) {
        this.jesen = jesen;
    }

    public int getNaziv() {
        return naziv;
    }

    public void setNaziv(int naziv) {
        this.naziv = naziv;
    }

    public int getTk_povrhnje() {
        return tk_povrhnje;
    }

    public void setTk_povrhnje(int tk_povrhnje) {
        this.tk_povrhnje = tk_povrhnje;
    }

    public int getTk_top() {
        return tk_top;
    }

    public void setTk_top(int tk_top) {
        this.tk_top = tk_top;
    }

    public int getTk_bottom() {
        return tk_bottom;
    }

    public void setTk_bottom(int tk_bottom) {
        this.tk_bottom = tk_bottom;
    }
}
