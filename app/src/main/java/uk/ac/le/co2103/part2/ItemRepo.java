package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepo {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    ItemRepo(Application application) {
        ShoppingListDB db = ShoppingListDB.getDatabase(application);
        itemDao = db.itemDao();
        allItems = itemDao.getItems();
    }

    LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    void deleteListById(Integer Id) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.deleteListById(Id);

        });
    }


    void updateNameById(int id,String newName) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.updateNameById(id,newName);
        });
    }

    void updateDescById(int id,String newDesc) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.updateDescById(id,newDesc);
        });
    }


    void updateUnitById(int id,String newUnit) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.updateUnitById(id,newUnit);
        });
    }

    void updateQuantityById(int id,int newQuantity) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.updateQuantityById(id,newQuantity);
        });
    }

    void insert(Item list) {
        ShoppingListDB.databaseWriteExecutor.execute(() -> {
            itemDao.insert(list);
        });
    }


}
