package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity{

    public String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebase;

    ArrayList<Contacto> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_registro);

        Firebase.setAndroidContext(getApplicationContext());
        firebase = new Firebase(FIREBASE_URL);

        info = new ArrayList<Contacto>();

    }
    public void btn_Registro(View view){
        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}
