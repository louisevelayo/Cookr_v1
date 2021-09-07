package be.kuleuven.cookr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.cookr.R;

public class LogIn extends AppCompatActivity {
    private EditText user;
    private EditText password;
    private CheckBox remember;
    private RequestQueue usernameChecker;
    private RequestQueue passwordChecker;
    private static final String usernameURL = "https://studev.groept.be/api/a20sd501/isValidUsername/" ;
    private static final String passwordUrl = "https://studev.groept.be/api/a20sd501/getLogInDetails/" ;
    public SharedPreferences userlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        user = findViewById(R.id.txtLogUser);
        password = findViewById(R.id.txtLogPass);
        remember=findViewById(R.id.checkbox);
        SharedPreferences userlog = getSharedPreferences("user",MODE_PRIVATE);
        String YEET= userlog.getString("Username","");
        if(YEET.equals("remember")){
            Intent intent1= new Intent(this,Recipes.class);
            startActivity(intent1);
        }
    }

    public void onbtnSign_Clicked(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void onbtnLog_Clicked(View view) {
        Intent intent = new Intent(this, Recipes.class);
        if (String.valueOf(user.getText()).equals("") || String.valueOf(password.getText()).equals("")) {
            if (String.valueOf(user.getText()).equals("")) {
                Toast.makeText(LogIn.this, "Please insert a username!", Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(password.getText()).equals("")) {
                Toast.makeText(LogIn.this, "Please insert a password!", Toast.LENGTH_LONG).show();
            }
        } else {
            String userTest = usernameURL + user.getText();
            usernameChecker = Volley.newRequestQueue(this);
            JsonArrayRequest usernamesRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                    response -> {
                        if (response.length() == 1) {
                            String passChecker = passwordUrl + user.getText();
                            passwordChecker = Volley.newRequestQueue(this);
                            JsonArrayRequest passCheck = new JsonArrayRequest(Request.Method.GET, passChecker, null,
                                    response1 -> {
                                        try {
                                            JSONObject j = response.getJSONObject(0);
                                            if (j.getString("password").equals(String.valueOf(password.getText()))) {
                                                Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_LONG).show();
                                                SharedPreferences userlog= getSharedPreferences("user",MODE_PRIVATE);
                                                SharedPreferences.Editor editor= userlog.edit();
                                                if(remember.isChecked()) {
                                                    editor.putString("Username", "remember");
                                                    editor.putString("userID", String.valueOf(j.getInt("userID")));
                                                    editor.apply();
                                                    startActivity(intent);
                                                    finish();
                                                }else{
                                                    editor.putString("Username","");
                                                    editor.putString("userID", String.valueOf(j.getInt("userID")));
                                                    editor.apply();
                                                    startActivity(intent);
                                                }
                                            }else{
                                                Toast.makeText(LogIn.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            Log.e("Database", e.getMessage(), e);
                                        }
                                    },
                                    error -> Toast.makeText(LogIn.this, "Connection Failed", Toast.LENGTH_LONG).show());
                            passwordChecker.add(passCheck);

                        } else{
                            Toast.makeText(LogIn.this, "Username does not exists", Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Log.d("Heck", "Error"));
            usernameChecker.add(usernamesRequest);
        }
    }
}