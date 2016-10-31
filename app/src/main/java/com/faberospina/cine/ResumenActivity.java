package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ResumenActivity extends NavegacionActivity {

    Spinner spLetras,spNumeros;
    String Letra,Numero;
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
                        Letra="A";
                        break;
                    case 1:
                        Letra="B";
                        break;
                    case 2:
                        Letra="C";
                        break;
                    case 3:
                        Letra="D";
                        break;
                    case 4:
                        Letra="E";
                        break;
                    case 5:
                        Letra="F";
                        break;
                    case 6:
                        Letra="G";
                        break;
                    case 7:
                        Letra="H";
                        break;
                    case 8:
                        Letra="I";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Objeto de Letraes para Spinner
        spNumeros = (Spinner) findViewById(R.id.Numeros);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Arreglo_numeros, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNumeros.setAdapter(adapter1);
        spNumeros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Numero="1";
                        break;
                    case 1:
                        Numero="2";
                        break;
                    case 2:
                        Numero="3";
                        break;
                    case 3:
                        Numero="4";
                        break;
                    case 4:
                        Numero="5";
                        break;
                    case 5:
                        Numero="6";
                        break;
                    case 6:
                        Numero="7";
                        break;
                    case 7:
                        Numero="8";
                        break;
                    case 8:
                        Numero="9";
                        break;
                    case 9:
                        Numero="10";
                        break;
                    case 10:
                        Numero="11";
                        break;
                    case 11:
                        Numero="12";
                        break;
                    case 12:
                        Numero="13";
                        break;
                    case 13:
                        Numero="14";
                        break;
                    case 14:
                        Numero="15";
                        break;
                    case 15:
                        Numero="16";
                        break;
                    case 16:
                        Numero="17";
                        break;
                    case 17:
                        Numero="18";
                        break;
                    case 18:
                        Numero="19";
                        break;
                    case 19:
                        Numero="20";
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
        Toast.makeText(getApplicationContext(),Numero, Toast.LENGTH_SHORT).show();
    }
}
