package pl.pollub.android.app_2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "manufacturer")
    @NonNull
    private String manufacturer;
    @ColumnInfo(name = "model")
    @NonNull
    private String model;
    @ColumnInfo(name = "android_version")
    @NonNull
    private String androidVersion;
    @ColumnInfo(name = "web_site")
    @NonNull
    private String webSite;

    @Ignore
    public Phone(@NonNull String androidVersion, int id, @NonNull String manufacturer, @NonNull String model, @NonNull String webSite) {
        this.androidVersion = androidVersion;
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.webSite = webSite;
    }

    public Phone(@NonNull String androidVersion, @NonNull String manufacturer, @NonNull String model, @NonNull String webSite) {
        this.androidVersion = androidVersion;
        this.manufacturer = manufacturer;
        this.model = model;
        this.webSite = webSite;
    }

    @NonNull
    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(@NonNull String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @NonNull
    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(@NonNull String webSite) {
        this.webSite = webSite;
    }
}
