package be.kuleuven.cookr.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.cookr.R;
import be.kuleuven.cookr.activities.ShoppingList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<ShoppingListItem> shoppingListItems;


    public ShoppingListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.activity_shopping_list_item_layout,parent,false);
        ShoppingListViewHolder shoppingListViewHolder = new ShoppingListViewHolder(itemView);
        return shoppingListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        if (this.shoppingListItems!=null && this.shoppingListItems.size()>0){
            ShoppingListItem item = this.shoppingListItems.get(position);
            holder.setData(item.getIngredientName(), item.getQuantity());
            //may need to add the other fields here
        } else{
            Toast.makeText(
                    context.getApplicationContext(),
                    R.string.no_items,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        if (this.shoppingListItems!=null){
            return this.shoppingListItems.size();
        }
        else{
            return 0;
        }
    }

    //this function updates the shopping list
    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems){
        this.shoppingListItems = shoppingListItems;
        notifyDataSetChanged();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName_tv, quantity_tv;
        CheckBox inBasket_cb;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            ingredientName_tv = itemView.findViewById(R.id.ingredientName_tv);
            quantity_tv = itemView.findViewById(R.id.quantity_tv);
            inBasket_cb = itemView.findViewById(R.id.inBasket_cb);


        }

        public void setData (String ingredientName, int quantity){
            ingredientName_tv.setText(ingredientName);
            quantity_tv.setText(String.valueOf(quantity));
        }
    }
}
