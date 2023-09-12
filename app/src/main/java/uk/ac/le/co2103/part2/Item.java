package uk.ac.le.co2103.part2;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="items")

public class Item {
    @PrimaryKey(autoGenerate = true)
    int id;


    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "quantity")
    public int quantity;

    @NonNull
    @ColumnInfo(name = "unit")
    public String unit;


    @ColumnInfo(name = "description")
    public String description;

    @ForeignKey(onDelete = CASCADE,entity = ShoppingList.class, parentColumns = "id",childColumns = "parentId")
    public Integer parentId;
    
    
    public Item(@NonNull Integer parentId,@NonNull String name,@NonNull int quantity,@NonNull String unit,String description) {
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
    }



    public String getItemName() {
        return name;
    }
    public Integer getItemId() {return id;}
    public String getItemDesc() {return description;}
    public Integer getParentId() { return parentId;}
    public Integer getQuantity() { return quantity;}
    public String getUnit() {return unit;}


    public void setItemName(String name) {this.name = name; }
    public void setItemQuantity(Integer quantity) {this.quantity = quantity; }
    public void setItemDesc(String newdesc) {this.description=newdesc;}
    public void setItemUnit(String unit) {this.unit = unit;}



}
