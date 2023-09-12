package uk.ac.le.co2103.part2;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListRepo repo;

    private final LiveData<List<ShoppingList>> allLists;
    public ShoppingListViewModel(Application application) {
        super(application);
        repo = new ShoppingListRepo(application);
        allLists = repo.getAllItems();
    }

    LiveData<List<ShoppingList>> getAllLists() {
        return allLists;
    }


    void deleteListById(Integer Id) {
        repo.deleteListById(Id);
    }


    public void insert(ShoppingList shoppingList) {

        System.out.println(shoppingList.name);
        repo.insert(shoppingList);


    }



}
