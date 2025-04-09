package pl.pollub.android.app_2.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDao phoneDao;
    private LiveData<List<Phone>> allPhones;

    public PhoneRepository(Application application) {
        PhoneRoomDatabase db = PhoneRoomDatabase.getDatabase(application);
        this.phoneDao = db.phoneDao();
        this.allPhones = this.phoneDao.findAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }
    public void deleteAllPhones(){
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> this.phoneDao.deleteAllPhones());
    }
}
