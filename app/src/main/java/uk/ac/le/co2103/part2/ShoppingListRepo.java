package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListRepo {
    private ShoppingListDao shoppingListDao;
    private LiveData<List<ShoppingList>> allLists;

    ShoppingListRepo(Application application) {
        ShoppingListDB db = ShoppingListDB.getDatabase(application);
        shoppingListDao = db.shoppingListDao();
        allLists = shoppingListDao.getShoppingLists();
    }

    LiveData<List<ShoppingList>> getAllItems() {
        return allLists;
    }

    void deleteListById(Integer Id) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            shoppingListDao.deleteListById(Id);

                });


    }


    void insert(ShoppingList list) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            shoppingListDao.insert(list);
        });
    }
}
