package videodownloader.privatebrowser.free.hd.statussaver.mainapp.socialTools.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import videodownloader.privatebrowser.free.hd.statussaver.DclassApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBHashTagsHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "local_hashtags_database.db";
    public static final String DATABASE_PATH;
    public static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    static {
        StringBuilder m = new StringBuilder("/data/data/");
        m.append(DclassApp.getInstance().getPackageName());
        m.append("/databases/");
        DATABASE_PATH = m.toString();
    }

    public DBHashTagsHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.myContext = context;
    }

    private boolean checkDataBase() {
        try {
            return new File(DATABASE_PATH + DATABASE_NAME).exists();
        } catch (SQLiteException unused) {
            return false;
        }
    }

    public static String m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append(str2);
        return sb.toString();
    }

    private void copyDataBase() throws IOException {
        InputStream open = this.myContext.getAssets().open(DATABASE_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(m(new StringBuilder(), DATABASE_PATH, DATABASE_NAME));
        byte[] bArr = new byte[2024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public synchronized void closeDataBase() throws SQLException {
        SQLiteDatabase sQLiteDatabase = this.myDataBase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        super.close();
    }

    public void createDatabase() throws IOException {
        checkDataBase();
        if (checkDataBase()) {
            return;
        }
        getReadableDatabase();
        try {
            close();
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void db_delete() {
        File file = new File(m(new StringBuilder(), DATABASE_PATH, DATABASE_NAME));
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db_delete();
        }
    }

    public SQLiteDatabase openDatabase() throws SQLException {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, 0);
        this.myDataBase = openDatabase;
        return openDatabase;
    }
}
