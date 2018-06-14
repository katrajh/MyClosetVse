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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_kombinacija")
    private int id;

    private String letniCas;
    private String priloznost;
    private String naziv;
    private String slikaTop;
    private String slikaVrhnje;
    private String slikaBottom;

    @ColumnInfo(name = "tk_id_povrhnje")
    private int tk_povrhnje;

    @ColumnInfo(name = "tk_id_top")
    private int tk_top;

    @ColumnInfo(name = "tk_id_bottom")
    private int tk_bottom;

    public Kombinacija() {
    }

    public Kombinacija(String letniCas, String priloznost, String naziv, String slikaTop, String slikaVrhnje, String slikaBottom, int tk_povrhnje, int tk_top, int tk_bottom) {
        this.letniCas = letniCas;
        this.priloznost = priloznost;
        this.naziv = naziv;
        this.slikaTop = slikaTop;
        this.slikaVrhnje = slikaVrhnje;
        this.slikaBottom = slikaBottom;
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

    public String getPriloznost() {
        return priloznost;
    }

    public void setPriloznost(String priloznost) {
        this.priloznost = priloznost;
    }

    public String getLetniCas() {
        return letniCas;
    }

    public void setLetniCas(String letniCas) {
        this.letniCas = letniCas;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSlikaTop() {
        return slikaTop;
    }

    public void setSlikaTop(String slikaTop) {
        this.slikaTop = slikaTop;
    }

    public String getSlikaVrhnje() {
        return slikaVrhnje;
    }

    public void setSlikaVrhnje(String slikaVrhnje) {
        this.slikaVrhnje = slikaVrhnje;
    }

    public String getSlikaBottom() {
        return slikaBottom;
    }

    public void setSlikaBottom(String slikaBottom) {
        this.slikaBottom = slikaBottom;
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
