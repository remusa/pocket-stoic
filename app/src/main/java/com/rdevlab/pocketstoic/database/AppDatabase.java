package com.rdevlab.pocketstoic.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

/**
 * Created by rms on 18/02/2018.
 */

@Database(entities = {Quote.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "quotes.db";

    private static AppDatabase INSTANCE;

    public abstract QuotesDao quotesModel();

    public static AppDatabase getQuotesDatabase(Context context) {
        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                    AppDatabase.class,
//                    DATABASE_NAME)
//                    .allowMainThreadQueries() //remove
//                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_2_3)
//                    .build();
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    // TODO: remove allowMainThreadQueries()
                    .allowMainThreadQueries() //remove
                    .openHelperFactory(new AssetSQLiteOpenHelperFactory())
//                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

//    static final Migration MIGRATION_2_3 = new Migration(2,3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase mDatabase) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
