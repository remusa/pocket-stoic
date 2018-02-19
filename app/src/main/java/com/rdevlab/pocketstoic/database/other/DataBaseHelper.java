package com.rdevlab.pocketstoic.database.other;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rms on 22/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "databases/quotes.db";
    private static String DB_PATH = "/data/data/com.rdevlab.pocketstoic/databases/";

    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        return this.mContext.getDatabasePath(DB_NAME).exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = this.mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(this.mContext.getDatabasePath(DB_NAME).getPath());

        byte[] buffer = new byte[1024];
        while (true) {
            int length = mInput.read(buffer);
            if (length > 0) {
                mOutput.write(buffer, 0, length);
            } else {
                mOutput.flush();
                mOutput.close();
                mInput.close();
                return;
            }
        }
    }

    private void openDataBase() throws SQLException {
        this.mDataBase = SQLiteDatabase.openDatabase(this.mContext.getDatabasePath(DB_NAME).getPath(), null, 1);
    }

    @Override
    public synchronized void close() {
        if (this.mDataBase != null) {
            this.mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
