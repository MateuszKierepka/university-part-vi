package pl.pollub.android.app_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.pollub.android.app_2.databinding.ActivityMainBinding;
import pl.pollub.android.app_2.model.Phone;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private PhoneViewModel phoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(this.binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        PhoneListAdapter adapter = new PhoneListAdapter(this.getLayoutInflater());
        adapter.setListener(position -> editPhone(position));
        this.binding.rvPhonesList.setAdapter(adapter);
        this.binding.rvPhonesList.setLayoutManager(new LinearLayoutManager(this));
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        this.phoneViewModel.getAllPhones().observe(this,phones -> adapter.setPhoneList(phones));
        this.launcher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),o -> saveOrAddPhone(o));
        this.binding.fabMain.setOnClickListener(view -> addNewPhone());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(getCallback());
        itemTouchHelper.attachToRecyclerView(this.binding.rvPhonesList);
    }



    private void editPhone(int position) {
        Phone phone = this.phoneViewModel.getAllPhones().getValue().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(PhoneActivity.PHONE_ID_KEY,phone.getId());
        bundle.putString(PhoneActivity.MANUFACTURER_KEY,phone.getManufacturer());
        bundle.putString(PhoneActivity.MODEL_KEY,phone.getModel());
        bundle.putString(PhoneActivity.ANDROID_VERSION_KEY,phone.getAndroidVersion());
        bundle.putString(PhoneActivity.WEB_SITE_KEY,phone.getWebSite());
        Intent intent = new Intent(this, PhoneActivity.class);
        intent.putExtras(bundle);
        this.launcher.launch(intent);
    }

    private void saveOrAddPhone(ActivityResult o) {
        if(o.getResultCode() == RESULT_OK){
            Bundle bundle = o.getData().getExtras();
            int phoneId = bundle.getInt(PhoneActivity.PHONE_ID_KEY);
            String manufacturer = bundle.getString(PhoneActivity.MANUFACTURER_KEY);
            String model = bundle.getString(PhoneActivity.MODEL_KEY);
            String androidVersion = bundle.getString(PhoneActivity.ANDROID_VERSION_KEY);
            String webSite = bundle.getString(PhoneActivity.WEB_SITE_KEY);
            if(phoneId > 0) {
                Phone phone = new Phone(androidVersion,phoneId,manufacturer,model,webSite);
                this.phoneViewModel.updatePhone(phone);
            }else{
                Phone phone = new Phone(androidVersion, manufacturer, model, webSite);
                this.phoneViewModel.addNewPhone(phone);
            }
        }
    }

    private void addNewPhone() {
        Intent addNewPhoneIntent = new Intent(this, PhoneActivity.class);
        this.launcher.launch(addNewPhoneIntent);
    }

    private ItemTouchHelper.SimpleCallback getCallback() {
        return new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Phone phone = MainActivity.this.phoneViewModel.getAllPhones().getValue().get(position);
                MainActivity.this.phoneViewModel.deletePhone(phone);
            }
        };
    }
}