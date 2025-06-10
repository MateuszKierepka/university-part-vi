package pl.pollub.android.app_3;

import android.os.Parcel;
import android.os.Parcelable;

// klasa reprezentujaca zdarzenie postepu pobierania pliku
public class ProgressEvent implements Parcelable {
    public static final int DOWNLOAD_IN_PROGRESS = 1;
    public static final int DOWNLOAD_OK = 2;
    public static final int DOWNLOAD_ERROR = 3;
    private int downloadedBytes;
    private int downloadProgress;
    private int downloadResult;

    // konstruktor tworzacy nowe zdarzenie postepu
    public ProgressEvent(int downloadedBytes, int downloadProgress, int downloadResult) {
        this.downloadedBytes = downloadedBytes;
        this.downloadProgress = downloadProgress;
        this.downloadResult = downloadResult;
    }

    // konstruktor do odtwarzania obiektu z parcel
    protected ProgressEvent(Parcel in) {
        downloadedBytes = in.readInt();
        downloadProgress = in.readInt();
        downloadResult = in.readInt();
    }

    // zapisuje stan obiektu do parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(downloadedBytes);
        dest.writeInt(downloadProgress);
        dest.writeInt(downloadResult);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // kreator do tworzenia obiektow z parcel
    public static final Creator<ProgressEvent> CREATOR = new Creator<ProgressEvent>() {
        @Override
        public ProgressEvent createFromParcel(Parcel in) {
            return new ProgressEvent(in);
        }

        @Override
        public ProgressEvent[] newArray(int size) {
            return new ProgressEvent[size];
        }
    };

    public int getDownloadedBytes() {
        return downloadedBytes;
    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public int getDownloadResult() {
        return downloadResult;
    }
}
