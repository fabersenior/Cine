package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
    }
    public void btn_Comprar(View view){
        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
        startActivity(intent);
    }
}
