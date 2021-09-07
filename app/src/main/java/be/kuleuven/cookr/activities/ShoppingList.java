package be.kuleuven.cookr.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cookr.R;
import be.kuleuven.cookr.module.CookrRoomDB;
import be.kuleuven.cookr.module.ShoppingListAdapter;
import be.kuleuven.cookr.module.ShoppingListItem;
import be.kuleuven.cookr.module.ShoppingListViewModel;

import static be.kuleuven.cookr.activities.viewRecipe.ITEMS_ADDED;


public class ShoppingList extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();                                                 //*
    private ShoppingListViewModel shoppingListViewModel;                                                 //*
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        shoppingListAdapter = new ShoppingListAdapter(this);
        recyclerView.setAdapter(shoppingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllShoppingListItems().observe(this, new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(List<ShoppingListItem> shoppingListItems) {
                shoppingListAdapter.setShoppingListItems(shoppingListItems);
            }
        });

    }

    public void onbtnRecipe_Clicked(View view) {
        Intent intent =new Intent(this,Recipes.class);
        startActivity(intent);
    }

    public void onbtnFav_Clicked(View view) {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    public void  onbtnClearShoppingList_Clicked(View view){
        shoppingListViewModel.clearShoppingList(CookrRoomDB.getDatabase(getApplicationContext()));
    }

}

