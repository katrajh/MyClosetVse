package rvir.mycloset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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

@Entity(tableName = "letniCasi")
public class LetniCasi {
    @PrimaryKey
    @ColumnInfo(name = "id_letniCasi")
    private int id;
    private int pomladJesen;
    private int poletje;
    private int zima;

    public LetniCasi() {
    }

    public LetniCasi(int id, int pomladJesen, int poletje, int zima) {
        this.id = id;
        this.pomladJesen = pomladJesen;
        this.poletje = poletje;
        this.zima = zima;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPomladJesen() {
        return pomladJesen;
    }


    public void setPomladJesen(int pomladJesen) {
        this.pomladJesen=pomladJesen;
    }

    public int getPoletje() {
        return poletje;
    }

    public void setPoletje(int poletje) {
        this.poletje = poletje;
    }

    public int getZima() {
        return zima;
    }

    public void setZima(int zima) {
        this.zima = zima;
    }
}

