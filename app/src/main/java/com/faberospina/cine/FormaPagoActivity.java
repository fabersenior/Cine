package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FormaPagoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pago);
    }
    public void btn_Continuar(View view){
        Intent intent= new Intent(getApplicationContext(),FormularioActivity.class);
        startActivity(intent);
    }
}
