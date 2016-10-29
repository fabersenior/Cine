package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CatalogoActivity extends NavegacionActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_catalogo);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_catalogo, contentFrameLayout);
    }

    public void lyt_OnClick(View view){
        Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
        startActivity(intent);

    }

    public void btn_Carrito(View view){
        Intent intent = new Intent(getApplicationContext(),ResumenActivity.class);
        startActivity(intent);
    }
}
