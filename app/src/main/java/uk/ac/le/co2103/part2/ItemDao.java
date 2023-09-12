package uk.ac.le.co2103.part2;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);

    @Query("DELETE FROM items")
    void deleteAll();

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<Item>> getItems();


    @Query("DELETE FROM items WHERE id = :lId")
    void deleteListById(int lId);


    @Query("UPDATE items SET name = :newName WHERE id = :id")
    void updateNameById(int id,String newName);


    @Query("UPDATE items SET description = :newDesc WHERE id = :id")
    void updateDescById(int id,String newDesc);


    @Query("UPDATE items SET unit = :newUnit WHERE id = :id")
    void updateUnitById(int id,String newUnit);

    @Query("UPDATE items SET quantity = :newQuantity WHERE id = :id")
    void updateQuantityById(int id,int newQuantity);

}
