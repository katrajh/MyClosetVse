package rvir.mycloset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

/**
 * Created by katrajh on 27/05/2018.
 */

@Dao
public interface OmaraDao {

    @Query("SELECT * FROM omara")
    List<Omara> getAll();

    @Query("SELECT * FROM omara")
    Cursor getOmaraCursor();

    @Query("SELECT * FROM omara WHERE id_omara IN (:omaraId)")
    Omara getOmaraById(int omaraId);

    @Query("SELECT * FROM omara WHERE naziv_omare LIKE :naziv LIMIT 1")
    List<Omara> findOmaraByName(String naziv);

    @Insert
    void insert(Omara omara);

    @Update
    void update(Omara omara);

    @Delete
    void delete(Omara omara);

    @Query("DELETE FROM omara")
    void deleteAll();

}
