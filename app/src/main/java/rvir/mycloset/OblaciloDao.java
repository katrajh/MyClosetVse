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

    @Query("SELECT * FROM oblacilo WHERE id_oblacilo IN (:oblaciloId)")
    List<Oblacilo> loadAllByIds(int[] oblaciloId);

    @Query("SELECT * FROM oblacilo WHERE naziv LIKE :naziv LIMIT 1")
    List<Oblacilo> findOblaciloByName(String naziv);

    @Insert
    void insertAll(Oblacilo... oblacilo);

    @Update
    void update(Oblacilo oblacilo);

    @Delete
    void delete(Oblacilo oblacilo);
}
