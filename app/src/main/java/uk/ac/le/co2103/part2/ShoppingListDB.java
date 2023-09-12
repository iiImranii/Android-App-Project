package uk.ac.le.co2103.part2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShoppingList.class,Item.class}, version = 4, exportSchema = false)
public abstract class ShoppingListDB extends RoomDatabase {
    public abstract ShoppingListDao shoppingListDao();
    public abstract ItemDao itemDao();

    private static volatile ShoppingListDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ShoppingListDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingListDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoppingListDB.class, "shoppinglist_db")
                           .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;

    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ShoppingListDao dao = INSTANCE.shoppingListDao();
                ItemDao iDao = INSTANCE.itemDao();
                dao.deleteAll();
                iDao.deleteAll();

                ShoppingList list = new ShoppingList("List1",null);
                dao.insert(list);
                Item item = new Item(1,"Yam",1,"kg","Buy yam above 1kg");
                iDao.insert(item);
            });
        }
    };





}
