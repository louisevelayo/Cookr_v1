package be.kuleuven.cookr.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import be.kuleuven.cookr.R;
import be.kuleuven.cookr.module.ShoppingListItem;
import be.kuleuven.cookr.module.ShoppingListViewModel;

public class viewRecipe extends AppCompatActivity {
    public static final String ITEMS_ADDED = "items_added";
    private String TAG = this.getClass().getSimpleName();
    private Integer recipeID;
    private RequestQueue requestQueue;
    private TableLayout ingridentTable;
    private ImageView img;
    private TextView name;
    private TextView description;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private ShoppingListViewModel shoppingListViewModel;
    private SharedPreferences userlog;
    private String userID;
    private final static String getIngredientsURL = "https://studev.groept.be/api/a20sd501/infoOfIngredientsOfRecipe/";
    private final static String submitFav="https://studev.groept.be/api/a20sd501/addToFavorites/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        SharedPreferences userlog = getSharedPreferences("user",MODE_PRIVATE);
        userID= userlog.getString("userID","");
        setContentView(R.layout.activity_view_recipe);
        ingridentTable = findViewById(R.id.IngredientTable);
        description = findViewById(R.id.txtviewdescription);
        img = findViewById(R.id.imageView6);
        byte[] t = b.getByteArray("Picture");
        Bitmap k = BitmapFactory.decodeByteArray(t, 0, t.length);
        img.setImageBitmap(k);
        name = findViewById(R.id.textView31);
        String n = b.getString("Name");
        name.setText(n);
        recipeID = b.getInt("ID");
        String instructions = b.getString("Instructions");
        description.setText(instructions);
        description.setTypeface(null,Typeface.BOLD_ITALIC);
        shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        shoppingListItems = new ArrayList<ShoppingListItem>();
        String requestURL = getIngredientsURL + String.valueOf(recipeID);
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                            JSONObject ingredientInfo = response.getJSONObject(i);
                            TableRow row = new TableRow(this);
                            TextView tv = new TextView(this);
                            row.addView(tv, 0);
                            tv.setText(ingredientInfo.getString("ingredientName"));
                            tv.setTextSize(15.0f);
                            tv.setTypeface(null, Typeface.BOLD_ITALIC);
                            LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) tv.getLayoutParams();
                            lay.height = (int) (60 * scale + 0.5f);
                            tv.setLayoutParams(lay);
                            ingridentTable.addView(row, i);
                            int ingredientID = ingredientInfo.getInt("ingredientID");
                            String ingredientName = ingredientInfo.getString("ingredientName");
                            int quantity = ingredientInfo.getInt("quantity");
                            ShoppingListItem shoppingListItem = new ShoppingListItem(ingredientID, ingredientName, quantity, false, 0.0f);
                            shoppingListItems.add(shoppingListItem);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(viewRecipe.this, "Connection Failed", Toast.LENGTH_LONG).show());
        requestQueue.add(submitRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onPlusCLicked(View v) {
        for (ShoppingListItem i : shoppingListItems) {
            shoppingListViewModel.insert(i);
        }
        
        Intent intent = new Intent(this, ShoppingList.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Ingredients", (Serializable) shoppingListItems);
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
    }

    public void onbtnRec(View v)
    {
        Intent intent = new Intent(this,Recipes.class);
        startActivity(intent);
    }

    public void onBtnFav(View v){
        Intent intent = new Intent(this,Favourites.class);
        String newfav=submitFav+userID+"/"+String.valueOf(recipeID);
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, newfav, null,
                response -> {
                    Toast.makeText(viewRecipe.this, "Added to favorites.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                },
                error -> Toast.makeText(viewRecipe.this, "Connection Failed", Toast.LENGTH_LONG).show());
        requestQueue.add(submitRequest);

    }
}


