package be.kuleuven.cookr.module;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ShoppingListItemsDAO {
    //Insert the methods you want to use to query the SQL DB.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ShoppingListItem shoppingListItem);

    @Query("SELECT * FROM shoppingList")
    LiveData<java.util.List<ShoppingListItem>> getAllItems();                                                     //LiveData follows observer design pattern. All observer objects will change if this changes.

    @Query("SELECT * FROM shoppingList WHERE ingredientID = :iID")
    LiveData<ShoppingListItem> shoppingListItem(int iID);


}


