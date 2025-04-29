package pl.pollub.android.app_2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pl.pollub.android.app_2.model.Phone;
import pl.pollub.android.app_2.model.PhoneRepository;

public class PhoneViewModel extends AndroidViewModel {
    private LiveData<List<Phone>> allPhones;
    private final PhoneRepository repository;
    public PhoneViewModel(@NonNull Application application) {
        super(application);
        this.repository = new PhoneRepository(application);
        this.allPhones = repository.getAllPhones();
    }
    public void deleteAllPhones(){
        this.repository.deleteAllPhones();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return this.allPhones;
    }
    public void addNewPhone(Phone phone){
        this.repository.addNewPhone(phone);
    }

    public void updatePhone(Phone phone) {
        this.repository.updatePhone(phone);
    }

    public void deletePhone(Phone phone) {
        this.repository.deletePhone(phone);
    }
}
