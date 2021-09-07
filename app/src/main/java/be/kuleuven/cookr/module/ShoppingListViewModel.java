package be.kuleuven.cookr.module;

//Research: wrapper function

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import be.kuleuven.cookr.activities.ShoppingList;

//Android view model allows us to pass the application context as a parameter
public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListItemsDAO shoppingListItemsDAO;
    private CookrRoomDB cookrRoomDB;
    private String TAG = this.getClass().getSimpleName();
    private LiveData<List<ShoppingListItem>> allShoppingListItems;                                  //using live data means that it is already done in the background thread. No need to use an async task

    public ShoppingListViewModel (Application application) {
        super(application);

        cookrRoomDB = CookrRoomDB.getDatabase(application);
        shoppingListItemsDAO = cookrRoomDB.shoppingListDAO();
        allShoppingListItems = shoppingListItemsDAO.getAllItems();
    }

    public void insert(ShoppingListItem shoppingListItem){
        new InsertAsyncTask(shoppingListItemsDAO).execute(shoppingListItem);
    }

    public LiveData<List<ShoppingListItem>> getAllShoppingListItems(){
        return allShoppingListItems;
    }

    public LiveData<ShoppingListItem> getShoppingListItem(int id){
        return shoppingListItemsDAO.shoppingListItem(id);
    }


    public void clearShoppingList(CookrRoomDB cookrRoomDB){
        new ClearAsyncTask().execute(cookrRoomDB);
    }



    @Override
    protected void onCleared(){
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    //Research: AsyncTask
    private class InsertAsyncTask extends AsyncTask<ShoppingListItem, Void, Void>{                      //the following code allows us to perform insert functions in the background
        ShoppingListItemsDAO mShoppingListItemsDao;

        public InsertAsyncTask(ShoppingListItemsDAO mShoppingListItemsDao){
            this.mShoppingListItemsDao = mShoppingListItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListItem... shoppingListItems) {
            mShoppingListItemsDao.insert(shoppingListItems[0]);
            return null;
        }
    }



    private class ClearAsyncTask extends AsyncTask<CookrRoomDB, Void, Void> {

        public ClearAsyncTask() {
        }


        @Override
        protected Void doInBackground(CookrRoomDB... cookrRoomDBS) {
            cookrRoomDBS[0].clearAllTables();
            return null;
        }
    }

}
