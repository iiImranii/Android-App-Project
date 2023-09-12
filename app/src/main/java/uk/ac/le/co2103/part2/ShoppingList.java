package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;

@Entity(tableName="shoppinglists")
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @NonNull
    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="image")
    public String image;


    public ShoppingList(@NonNull String name, String image) {
        this.name = name;
        this.image = image;

    }

    public String getListName() {
        return name;
    }

    public String getImageURL() {
        return image;
    }

    public Integer getListId() {
        return id;
    }

}
