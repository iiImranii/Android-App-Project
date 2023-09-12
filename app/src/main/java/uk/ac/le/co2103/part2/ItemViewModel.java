package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepo repo;

    private final LiveData<List<Item>> allItems;
    public ItemViewModel(Application application) {
        super(application);
        repo = new ItemRepo(application);
        allItems = repo.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {return allItems;}

    void deleteListById(Integer Id) {
        repo.deleteListById(Id);
    }


    void updateNameById(int id,String newName){repo.updateNameById(id,newName);}

    void updateDescById(int id,String newDesc){repo.updateDescById(id,newDesc);}

    void updateUnitById(int id,String newUnit){repo.updateUnitById(id,newUnit);}

    void updateQuantityById(int id,int newQuantity){repo.updateQuantityById(id,newQuantity);}


    public void insert(Item item) {
        repo.insert(item);


    }



}
