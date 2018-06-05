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
public interface LetniCasiDao {


    @Query("SELECT * FROM letniCasi")
    List<LetniCasi> getAll();

    @Query("SELECT * FROM letniCasi WHERE id_letniCasi IN (:letnicasiId)")
    List<LetniCasi> loadAllByIds(int[] letnicasiId);

    @Insert
    void insert(LetniCasi letniCasi);

    @Update
    void update(LetniCasi letniCasi);

    @Delete
    void delete(LetniCasi letniCasi);

}
