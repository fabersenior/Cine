package com.faberospina.cine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;

public class FormularioActivity extends NavegacionActivity {

    SharedPreferences prefs;
    private Integer id=0;
    SimpleDateFormat sdf;
    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    String codigo;
    private Firebase firebasedatos;
    private boolean flag=true;
    String letter="";
    int pos=-1;
    public long p;

    long hat=1450656000000L;
    long hat2=2678400000L;//hat2=28908000000L; para los meses


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_formulario);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_formulario, contentFrameLayout);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences",Context.MODE_PRIVATE);

        letter=prefs.getString("kLetra","07");
        pos=Integer.parseInt(prefs.getString("kPosicion","07"));

        sdf = new SimpleDateFormat("HH:mm:ss");



    }


    public void btn_Comprar(View view){
        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
        SavePreferences("kCompra","ok");

        final long date = System.currentTimeMillis();//1800000 son 5 horas
        p=date-hat-hat2-18000000;//-hat2
        p=p/1000;
        String dateString = sdf.format(p);//new Date()
        Toast.makeText(getApplicationContext(),dateString,Toast.LENGTH_SHORT).show();

        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        final String finalLetter = letter;
        final int finalPos = pos;
        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                codigo = Integer.toString(id);
                while(flag) {
                    final String data = "Silla" + codigo;
                    if (dataSnapshot.child("Ubicacion").child(data).exists()) {
                        if (dataSnapshot.child("Ubicacion").child(data).child("letter").exists()
                                && dataSnapshot.child("Ubicacion").child(data).child("pos").exists()) {
                            final String l1 = dataSnapshot.child("Ubicacion").child(data).child("letter").getValue().toString();
                            final String p1 = dataSnapshot.child("Ubicacion").child(data).child("pos").getValue().toString();
                            final int p1c = Integer.parseInt(p1);
                            if (l1.equals(finalLetter) && p1c == finalPos) {
                                Firebase firebase;
                                Firebase firebase1;
                                firebase = firebasedatos.child("Ubicacion");//Nombre de la tabla
                                firebase1 = firebase.child(data);//item de la tabla catalogo
                                Silla silla = new Silla(finalLetter, finalPos, 0, p);
                                firebase1.setValue(silla);//agregamos a la base de datos
                                flag = false;
                            } else{
                                flag=true;
                                codigo = Integer.toString(id++);
                            }
                        }else{
                            flag=false;
                        }
                    } else {
                        codigo = Integer.toString(id++);

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });





        startActivity(intent);
        finish();
    }


    private void SavePreferences(String key, String value){
        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
