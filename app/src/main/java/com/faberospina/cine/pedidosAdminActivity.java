package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class pedidosAdminActivity extends AppCompatActivity {

    //Declaracion de la lista del catalogo
    ListView lista_catalogo;
    ArrayAdapter<String> adaptador;
    ArrayList<String> Lista_arreglos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pedidos_admin);

        //Para la lista de pedidos
        lista_catalogo =(ListView)findViewById(R.id.Lista);
        Lista_arreglos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Lista_arreglos);
        lista_catalogo.setAdapter(adaptador);
    }

     public  void image_OnClick(View view){
         Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
         startActivity(intent);

     }
}
