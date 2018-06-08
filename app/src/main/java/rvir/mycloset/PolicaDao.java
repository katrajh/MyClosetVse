package rvir.mycloset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by katrajh on 27/05/2018.
 */

@Dao
public interface PolicaDao {

    @Query("SELECT * FROM polica")
    List<Polica> getAll();

    @Query("SELECT * FROM polica WHERE id_polica =:policaId AND tk_id_omara =:tk_idO")
    List<Polica> getPolicaById(int policaId, int tk_idO);

    @Query("SELECT * FROM polica WHERE tk_id_omara=:omaraId")
    List<Polica> getAllIstaOmara(final int omaraId);

    @Insert
    void insert(Polica polica);

    @Update
    void update(Polica polica);

    @Delete
    void delete(Polica polica);

    @Query("DELETE FROM polica")
    void deleteAll();

    @Query("DELETE FROM polica WHERE id_polica =:policaId")
    void deleteOne(final int policaId);

    @Query("UPDATE polica SET polica_naziv=:nazivP, polica_kapaciteta=:kapacP WHERE id_polica=:policaId AND tk_id_omara=:tk_idOmara")
    void updateOne(int policaId, String nazivP, int kapacP, int tk_idOmara);
}
