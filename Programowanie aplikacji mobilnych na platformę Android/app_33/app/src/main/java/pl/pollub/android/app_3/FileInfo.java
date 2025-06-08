package pl.pollub.android.app_3;

// klasa przechowujaca informacje o pliku
public class FileInfo {
    private int fileSize;
    private String filetype;

    public FileInfo(int fileSize, String filetype) {
        this.fileSize = fileSize;
        this.filetype = filetype;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFiletype() {
        return filetype;
    }
}
