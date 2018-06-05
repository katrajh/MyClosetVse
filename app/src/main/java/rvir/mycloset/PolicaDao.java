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

    @Query("SELECT * FROM polica WHERE id_polica IN (:policaId)")
    List<Polica> loadAllByIds(int[] policaId);

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
}
