package pl.pollub.android.app_4;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.pollub.android.app_4.databinding.ActivityMainBinding;

// glowna aktywnosc aplikacji, zarzadza interfejsem uzytkownika i funkcjonalnoscia rysowania
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;

    // inicjalizacja interfejsu uzytkownika i ustawienie nasluchiwaczy przyciskow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnRed.setOnClickListener(v -> binding.drawingSurface.setColor(Color.RED));
        binding.btnYellow.setOnClickListener(v -> binding.drawingSurface.setColor(Color.YELLOW));
        binding.btnGreen.setOnClickListener(v -> binding.drawingSurface.setColor(Color.GREEN));
        binding.btnBlue.setOnClickListener(v -> binding.drawingSurface.setColor(Color.BLUE));
        binding.btnClear.setOnClickListener(v -> binding.drawingSurface.clearScreen());
    }

    // tworzenie menu opcji aplikacji
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // obsluga wyboru opcji z menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveImage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // metoda zapisujaca rysunek do galerii urzadzenia
    private void saveImage() {
        Log.d(TAG, "save image");
        ContentResolver resolver = getApplicationContext().getContentResolver(); // pobranie resolvera do zarzadzania zawartoscia urzadzenia
        Uri imageCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // sprawdzenie wersji androida i wybor odpowiedniego uri dla galerii
            imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues imageDetails = new ContentValues(); // przygotowanie szczegolow zapisywanego obrazu
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // utworzenie unikalnej nazwy pliku z aktualna data i czasem
        String fileName = "IMG_" + timeStamp + ".png";
        imageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, fileName); // ustawienie nazwy pliku w szczegolach
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // oznaczenie pliku jako oczekujacego na zapis
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri imageUri = resolver.insert(imageCollection, imageDetails); // utworzenie nowego wpisu w galerii i pobranie jego uri

        try (ParcelFileDescriptor pfd = resolver.openFileDescriptor(imageUri, "w", null);
             FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor())) {
            binding.drawingSurface.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fos); // kompresja i zapisanie bitmapy do pliku
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // oznaczenie pliku jako gotowego
            imageDetails.clear();
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0);
            resolver.update(imageUri, imageDetails, null, null);
        }
    }
}