package pl.pollub.android.app_2.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone p);

    @Query("delete from phones")
    void deleteAllPhones();

    @Query("select * from phones order by id")
    LiveData<List<Phone>> findAllPhones();

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);
}
