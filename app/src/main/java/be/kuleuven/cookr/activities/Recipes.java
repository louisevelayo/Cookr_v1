package be.kuleuven.cookr.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Base64;
import be.kuleuven.cookr.R;


public class Recipes extends AppCompatActivity {
    private TableLayout recipesTable;
    private RequestQueue allRecipes;
    private SharedPreferences userlog;
    private final static String allRecipesURL="https://studev.groept.be/api/a20sd501/allRecipes";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences userlog = getSharedPreferences("user",MODE_PRIVATE);
        String userID= userlog.getString("userID","");
        setContentView(R.layout.activity_recipes);
        Bundle extra=getIntent().getExtras();
        recipesTable =findViewById(R.id.RecRecipesTable);
        allRecipes = Volley.newRequestQueue( this );
        JsonArrayRequest recipesRequest = new JsonArrayRequest(Request.Method.GET, allRecipesURL, null,
                response -> {
                    try {
                        for(int i=0;i<response.length();i++) {
                            final float scale=getApplicationContext().getResources().getDisplayMetrics().density;
                            JSONObject j= response.getJSONObject(i);
                            TableRow tableRow= new TableRow(this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams();
                            lp.height=(int) (100*scale+0.5f);
                            tableRow.setLayoutParams(lp);
                            tableRow.setClickable(true);
                            byte[] img= Base64.getDecoder().decode(j.getString("image"));
                            Bitmap test= BitmapFactory.decodeByteArray(img,0,img.length);
                            Intent intent=new Intent(this,viewRecipe.class);
                            intent.putExtra("Picture",img);
                            intent.putExtra("Name",j.getString("recipeName"));
                            intent.putExtra("ID",j.getInt("recipeID"));
                            intent.putExtra("Instructions",j.getString("instructions"));
                            tableRow.setOnClickListener(v -> startActivity(intent));
                            ImageView le= new ImageView(this);
                            tableRow.addView(le,0);
                            le.setImageBitmap(test);
                            le.setPadding(0,(int)(1*scale+0.5f),(int)(5*scale+0.5f),(int)(1*scale+0.5f));
                            TextView tv = new TextView(this);
                            tableRow.addView(tv,1);
                            tv.setText(String.valueOf(j.get("recipeName")));
                            tv.setTextSize(15.0f);
                            tv.setTypeface(null,Typeface.BOLD_ITALIC);
                            LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) tv.getLayoutParams();
                            lay.height=(int) (60*scale+0.5f);
                            tv.setLayoutParams(lay);
                            recipesTable.addView(tableRow,i);
                        }
                    }
                    catch( JSONException e ) {
                        Log.e( "Database", e.getMessage(), e );
                    }
                },
                error -> Toast.makeText(Recipes.this,"Connection Failed",Toast.LENGTH_LONG).show());
        allRecipes.add(recipesRequest);
    }

    public void onbtnList_Clicked(View view) {
        Intent intent =new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    public void onbtnFav_Clicked(View view) {
        Intent intent =new Intent(this,Favourites.class);
        startActivity(intent);
    }
}