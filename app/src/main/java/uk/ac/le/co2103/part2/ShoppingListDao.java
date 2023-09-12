package uk.ac.le.co2103.part2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingList shoppinglist);

    @Query("DELETE FROM shoppinglists")
    void deleteAll();

    @Query("SELECT * FROM shoppinglists ORDER BY name ASC")
    LiveData<List<ShoppingList>> getShoppingLists();


    @Query("DELETE FROM shoppinglists WHERE id = :lId")
    void deleteListById(int lId);
}
