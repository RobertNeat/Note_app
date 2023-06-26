package com.example.note_app;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Element.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ElementRoomDatabase extends RoomDatabase {

    public abstract ElementDao elementDao();

    private static volatile ElementRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ElementRoomDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (ElementRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ElementRoomDatabase.class,"note_database")//PhoneRoomDatabase
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                ElementDao dao = INSTANCE.elementDao();
                //tworzenie elementów (obiektów klasy Element) i dodawanie ich do bazy
                //za pomocą dao.insert() wprowadza się dane w czasie "onCreate"

                Element[] elements = {
                        new Element("Notatka_1", new Date(), "Opis notatki jeden."),
                        new Element("Notatka_2", new Date(), "Opis notatki dwa."),
                        new Element("Notatka_3", new Date(), "Opis notatki trzy."),
                        new Element("Notatka_4", new Date(), "Opis notatki cztery.")
                };

                for(Element e:elements)
                    dao.insert(e);
            });
        }
    };

}
