package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class ResumenActivity extends NavegacionActivity {

    Spinner spLetras;
    String Letra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_resumen);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_resumen, contentFrameLayout);


        //Objeto de Letraes para Spinner
        spLetras = (Spinner) findViewById(R.id.Letras);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Arreglo_letras, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLetras.setAdapter(adapter);
        spLetras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Letra="Medellin";
                        break;
                    case 1:
                        Letra="Bogotá";
                        break;
                    case 2:
                        Letra="Bucaramanga";
                        break;
                    case 3:
                        Letra="Cartagena";
                        break;
                    case 4:
                        Letra="Barranquilla";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void btn_pagar(View view){
        Intent intent = new Intent(getApplicationContext(),FormaPagoActivity.class);
        startActivity(intent);
    }
}
