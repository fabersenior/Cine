package com.faberospina.cine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity{

    public String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    Integer id=0;

    String nombre, correo,pass,codigo;
    EditText eId, eNombre, ePass, eCorreo;
    ArrayList<Contacto> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_registro);

        Firebase.setAndroidContext(getApplicationContext());
        firebasedatos = new Firebase(FIREBASE_URL);


        eNombre = (EditText) findViewById(R.id.eLogin);
        ePass = (EditText) findViewById(R.id.ePass1);
        eCorreo = (EditText) findViewById(R.id.eCorreo);

        info = new ArrayList<Contacto>();

    }
    public void btn_Registro(View view){

        Firebase firebase;


        nombre = eNombre.getText().toString();
        correo = eCorreo.getText().toString();
        pass = ePass.getText().toString();

        firebase=  firebasedatos.child("contacto"+id); //creamos un hijo de firebasedatos
        Contacto contacto= new Contacto(nombre,pass,correo,String.valueOf(id));
        firebase.setValue(contacto);
        id++;

/*        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);*/
    }

}
