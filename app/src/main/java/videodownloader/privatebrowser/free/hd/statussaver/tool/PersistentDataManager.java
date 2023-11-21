package videodownloader.privatebrowser.free.hd.statussaver.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.Objects;

import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_history_ForFunc;

public class PersistentDataManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "applocalstore.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOKMARK = "fav_my_data";
    private static final String TABLE_HISTORY = "history_my_data";
    private static PersistentDataManager persistentDataManager;

    private PersistentDataManager(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

                    private int VerifyExistingFavorite(String url, int type) {
        Cursor cursor = null;
        int i = -1;
        try {
            try {
                cursor = persistentDataManager.getReadableDatabase().rawQuery(type == 2 ? "SELECT id FROM fav_my_data WHERE url= ?" : "SELECT id FROM fav_my_data WHERE title= ?", new String[]{url});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    i = cursor.getInt(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return i;
            }
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return i;
    }
                    private store_model_history_ForFunc VerifyExistingHistory(String url, int type) {
        Cursor cursor = null;
        int i = -1;
        String str = "";
        try {
            try {
                cursor = persistentDataManager.getReadableDatabase().rawQuery(type == 2 ? "SELECT id,url FROM history_my_data WHERE url= ?" : "SELECT id,url FROM history_my_data WHERE title= ?", new String[]{url});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    i = cursor.getInt(0);
                    str = cursor.getString(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new store_model_history_ForFunc(i, str);
            }
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return new store_model_history_ForFunc(i, str);
    }

    public static synchronized PersistentDataManager getSQInstance(Context context) {
        PersistentDataManager persistentDataManager2;
        synchronized (PersistentDataManager.class) {
            if (persistentDataManager == null) {
                persistentDataManager = new PersistentDataManager(context.getApplicationContext());
            }
            persistentDataManager2 = persistentDataManager;
        }
        return persistentDataManager2;
    }

    public Cursor GetAllHistory() {
        return persistentDataManager.getReadableDatabase().query(TABLE_HISTORY, new String[]{"id as _id", "title", "url", "visitedtime"}, null, null, null, null, "visitedtime DESC ");
    }

    public void RemoveHistory(int _id) {
        SQLiteDatabase writableDatabase = persistentDataManager.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            try {
                writableDatabase.delete(TABLE_HISTORY, "id=?", new String[]{String.valueOf(_id)});
                writableDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void RemoveHistoryTable() {
        SQLiteDatabase writableDatabase = persistentDataManager.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            try {
                writableDatabase.execSQL("DELETE FROM history_my_data");
                writableDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public int VerifyAndAddFavorite(String title, String url, Bitmap favicon, boolean isOnlyUpdate) {
        int i = 0;
        if (url.equals("about:blank")) {
            return 0;
        }
        int VerifyExistingFavorite = VerifyExistingFavorite(url, 2);
        SQLiteDatabase writableDatabase = persistentDataManager.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            try {
                if (VerifyExistingFavorite == -1) {
                    if ((VerifyExistingFavorite(title, 1) == -1 || !title.endsWith(" - Google Search")) && !isOnlyUpdate) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("title", title.replaceAll("'", "''"));
                        contentValues.put("url", url);
                        contentValues.put("visitedtime", System.currentTimeMillis());
                        writableDatabase.insert(TABLE_BOOKMARK, null, contentValues);
                        writableDatabase.setTransactionSuccessful();
                        i = 1;
                    }
                } else {
                    ContentValues contentValues2 = new ContentValues();
                    if (!isOnlyUpdate) {
                        if (!title.isEmpty()) {
                            contentValues2.put("title", title.replaceAll("'", "''"));
                        }
                        contentValues2.put("visitedtime", System.currentTimeMillis());
                    }
                    if (contentValues2.size() > 0) {
                        writableDatabase.update(TABLE_BOOKMARK, contentValues2, "id=?", new String[]{String.valueOf(VerifyExistingFavorite)});
                        writableDatabase.setTransactionSuccessful();
                        i = 2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return i;
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void VerifyAndAddHistory(String title, String url, Bitmap favicon) {
        store_model_history_ForFunc VerifyExistingHistory;
        int isFounded;
        int isFounded2 = Objects.requireNonNull(VerifyExistingHistory(url, 2)).getIsFounded();
        SQLiteDatabase writableDatabase = persistentDataManager.getWritableDatabase();
        writableDatabase.beginTransaction();
        boolean z = false;
        try {
            try {
                if (isFounded2 == -1) {
                    if (!title.isEmpty() && ((isFounded = (Objects.requireNonNull(VerifyExistingHistory = VerifyExistingHistory(title, 1))).getIsFounded()) == -1 || !title.endsWith(" - Google Search"))) {
                        if (isFounded == -1 || !UtilsForApp.getHost(VerifyExistingHistory.getUrlFounded()).equals(UtilsForApp.getHost(url))) {
                            z = true;
                        }
                        if (z) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("title", title.replaceAll("'", "''"));
                            contentValues.put("url", url);
                            contentValues.put("visitedtime", System.currentTimeMillis());
                            writableDatabase.insert(TABLE_HISTORY, null, contentValues);
                            writableDatabase.setTransactionSuccessful();
                        }
                    }
                } else {
                    ContentValues contentValues2 = new ContentValues();
                    if (!url.startsWith("https://www.google.com/search?q=")) {
                        if (!title.isEmpty()) {
                            contentValues2.put("title", title.replaceAll("'", "''"));
                        }
                        contentValues2.put("visitedtime", System.currentTimeMillis());
                    }
                    if (contentValues2.size() > 0) {
                        writableDatabase.update(TABLE_HISTORY, contentValues2, "id=?", new String[]{String.valueOf(isFounded2)});
                        writableDatabase.setTransactionSuccessful();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table fav_my_data(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,url string not null,visitedtime INTEGER not null)");
        sqLiteDatabase.execSQL("create table history_my_data(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,url string not null,visitedtime INTEGER not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists fav_my_data");
        db.execSQL("drop table if exists history_my_data");
        onCreate(db);
    }
}
