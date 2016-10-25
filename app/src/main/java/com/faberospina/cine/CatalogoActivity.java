package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CatalogoActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
    }

    public void lyt_OnClick(View view){
        Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
        startActivity(intent);

    }

    public void btn_Carrito(View view){

    }
}
