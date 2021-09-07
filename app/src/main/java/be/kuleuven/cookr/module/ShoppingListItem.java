package be.kuleuven.cookr.module;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "shoppingList")
public class ShoppingListItem implements Serializable {

    //Declaration of private variables which are also the fields of the shopping list table
    @PrimaryKey
    @NonNull
    private int ingredientID;

    @NonNull
    private String ingredientName;

    @NonNull
    private int quantity;

    private boolean inBasket;

    public void setIngredientID(@NonNull int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setIngredientName(@NonNull String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setQuantity(@NonNull int quantity) {
        this.quantity = quantity;
    }

    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private float price;

    public ShoppingListItem (int ingredientID, String ingredientName, int quantity, boolean inBasket, float price){
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.inBasket = inBasket;
        this.price = price;
    }

    @NonNull
    public int getIngredientID() {
        return ingredientID;
    }
    @NonNull
    public String getIngredientName() {
        return ingredientName;
    }
    @NonNull
    public int getQuantity() {
        return quantity;
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public float getPrice() {
        return price;
    }
}
