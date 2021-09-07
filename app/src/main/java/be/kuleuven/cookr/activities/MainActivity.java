package be.kuleuven.cookr.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import be.kuleuven.cookr.module.CookrRoomDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookrRoomDB cookrRoomDB = CookrRoomDB.getDatabase(getApplicationContext());
        Intent intent=new Intent(this,LogIn.class);
        startActivity(intent);
        finish();
    }
}