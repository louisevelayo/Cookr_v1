package be.kuleuven.cookr.module;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ShoppingListItem.class , version = 2, exportSchema = false)
public abstract class CookrRoomDB extends RoomDatabase {
    //adding list of DAO's
    public abstract ShoppingListItemsDAO shoppingListDAO();
    //database class should be singleton
    private static volatile CookrRoomDB cookrRoomDBInstance;

    //lazy instantiation of singleton pattern
    public static CookrRoomDB getDatabase(final Context context){
        if(cookrRoomDBInstance==null){
            synchronized (CookrRoomDB.class){                                                    //Instance of class is created only when it is required
                if(cookrRoomDBInstance == null){
                    cookrRoomDBInstance = Room.databaseBuilder(context.getApplicationContext(),    //If the instance of the ShoppingListRoomDB is null then it will create a new dB
                            CookrRoomDB.class, "ShoppingListDB.db")
                            .build();
                }
            }
        }
        return cookrRoomDBInstance;                                                                //If the instance is not null then it just returns the existing ShoppingListRoomDB
    }
}
