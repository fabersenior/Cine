package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FormaPagoActivity extends NavegacionActivity {

    RadioGroup formas_pago;
    Button comprar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forma_pago);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_forma_pago, contentFrameLayout);
        formas_pago=(RadioGroup)findViewById(R.id.formas);
        comprar=(Button)findViewById(R.id.btn_Continuar);
    }
    public void RadioClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.bTarjeta:
                if (checked) {
                    comprar.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), FormularioActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.bPSE:
                if (checked) {
                    comprar.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), FormularioActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.bEfectivo:
                if (checked) {
                    comprar.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), EfectivoActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                break;
        }
    }

}
