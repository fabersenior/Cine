package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PreLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
    }


    public void btnUser(View view){
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public  void btnAdmin(View view){
        Intent intent=new Intent(getApplicationContext(),LoginAdminActivity.class);
        startActivity(intent);
        finish();
    }
}
