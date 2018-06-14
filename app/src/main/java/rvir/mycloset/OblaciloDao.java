package rvir.mycloset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import rvir.mycloset.Oblacilo;

/**
 * Created by Eva on 26.3.2018.
 */


@Dao
public interface OblaciloDao {
    @Query("SELECT * FROM oblacilo")
    List<Oblacilo> getAll();

    @Query("SELECT * FROM oblacilo WHERE id_oblacilo IN (:oblaciloId) LIMIT 1")
    Oblacilo findByID(int oblaciloId);

    @Query("SELECT * FROM oblacilo WHERE id_oblacilo IN (:oblaciloId)")
    List<Oblacilo> loadAllByIds(int[] oblaciloId);

    @Query("SELECT slika FROM oblacilo WHERE id_oblacilo IN (:oblaciloId)")
    String loadSrcById(int[] oblaciloId);

    @Query("SELECT * FROM oblacilo WHERE tk_id_polica =:policaId")
    List<Oblacilo> getAllIstaPolica(final int policaId);

    @Query("SELECT * FROM oblacilo WHERE naziv LIKE :naziv LIMIT 1")
    List<Oblacilo> findOblaciloByName(String naziv);


    // queriji za kombinacije za letne čase in priloznost

    @Query("SELECT * FROM oblacilo WHERE poletje IN (1,2) AND priloznost =:pril AND (vrsta = 'top' OR vrsta='obleka')")
    List<Oblacilo> getAllPoletjeTopObleka(String pril);

    @Query("SELECT * FROM oblacilo WHERE zima IN (1,2) AND priloznost =:pril AND (vrsta = 'top' OR vrsta='obleka')")
    List<Oblacilo> getAllZimaTopObleka(String pril);

    @Query("SELECT * FROM oblacilo WHERE pomladJesen IN (1,2) AND priloznost =:pril AND (vrsta = 'top' OR vrsta='obleka')")
    List<Oblacilo> getAllPomladInJesenTopObleka(String pril);

    @Query("SELECT * FROM oblacilo WHERE poletje IN (1,2) AND priloznost =:pril AND vrsta=:vrstaObl")
    List<Oblacilo> getAllPoletjeOstalo(String pril, String vrstaObl);

    @Query("SELECT * FROM oblacilo WHERE zima IN (1,2) AND priloznost =:pril AND vrsta=:vrstaObl")
    List<Oblacilo> getAllZimaOstalo(String pril, String vrstaObl);

    @Query("SELECT * FROM oblacilo WHERE pomladJesen IN (1,2) AND priloznost =:pril AND vrsta=:vrstaObl")
    List<Oblacilo> getAllPomladInJesenOstalo(String pril, String vrstaObl);


    @Insert
    void insert(Oblacilo oblacilo);

    @Update
    void update(Oblacilo oblacilo);

    @Delete
    void delete(Oblacilo oblacilo);
}
