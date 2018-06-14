package rvir.mycloset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by katrajh on 27/05/2018.
 */


@Dao
public interface KombinacijaDao {


    @Query("SELECT * FROM kombinacija")
    List<Kombinacija> getAll();

    @Query("SELECT * FROM kombinacija WHERE id_kombinacija IN (:kombId)")
    List<Kombinacija> loadAllByIds(int[] kombId);

    @Query("SELECT * FROM kombinacija WHERE naziv LIKE :naziv LIMIT 1")
    List<Kombinacija> findKombByName(String naziv);

    @Query("SELECT * FROM kombinacija WHERE letniCas LIKE :letniCas AND priloznost LIKE :priloznost")
    List<Kombinacija> findbypriloznostLetniCas(String letniCas, String priloznost);

    @Insert
    void insertAll(Kombinacija kombinacija);

    @Update
    void update(Kombinacija kombinacija);

    @Delete
    void delete(Kombinacija kombinacija);
}
