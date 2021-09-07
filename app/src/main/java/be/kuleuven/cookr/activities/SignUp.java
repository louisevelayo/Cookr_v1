package be.kuleuven.cookr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import be.kuleuven.cookr.R;

public class SignUp extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText password;
    private RequestQueue usernameChecker;
    private RequestQueue userCreator;
    private static final String userCreatorURL = "https://studev.groept.be/api/a20sd501/addUser/";
    private static final String usernameURL = "https://studev.groept.be/api/a20sd501/isValidUsername/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name= findViewById(R.id.txtSignName);
        username= findViewById(R.id.txtSignUser);
        password=findViewById(R.id.txtSignPass);
    }

    public void onbtnSign_Clicked(View view)
    {
        Intent intent = new Intent(this,LogIn.class);
        if(String.valueOf(username.getText()).equals("") || String.valueOf(password.getText()).equals("") || String.valueOf(name.getText()).equals("")) {
            if (String.valueOf(username.getText()).equals("")){
                Toast.makeText(SignUp.this,"Please insert a username!",Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(password.getText()).equals("")){
                Toast.makeText(SignUp.this,"Please insert a password!",Toast.LENGTH_LONG).show();
            }
            if (String.valueOf(name.getText()).equals("")){
                Toast.makeText(SignUp.this,"Please enter your name!",Toast.LENGTH_LONG).show();
            }
        }
        else {
            String userTest=usernameURL+username.getText();
            usernameChecker = Volley.newRequestQueue( this );
            JsonArrayRequest usernamesRequest = new JsonArrayRequest(Request.Method.GET, userTest, null,
                    response -> {
                        if(response.length()==0)
                        {
                            String newUser = userCreatorURL +username.getText()+"/"+password.getText()+"/"+name.getText()+"/"+name.getText();
                            RequestQueue userCreator = Volley.newRequestQueue( this );
                            JsonArrayRequest newUserRequest = new JsonArrayRequest(Request.Method.GET, newUser, null,
                                    response1 -> {
                                        Toast.makeText(SignUp.this,"Account created",Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                        finish();
                                    },
                                    error -> Toast.makeText(SignUp.this,"Connection Failed",Toast.LENGTH_LONG).show());
                            userCreator.add(newUserRequest);
                        }
                        else{
                            Toast.makeText(SignUp.this,"Username already exists",Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Log.d("Heck","Error"));
            usernameChecker.add(usernamesRequest);
        }
    }

    public void onbtnLog_Clicked(View view) {
        Intent intent = new Intent(this,LogIn.class);
        startActivity(intent);
    }


}