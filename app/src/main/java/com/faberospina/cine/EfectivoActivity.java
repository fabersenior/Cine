package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EfectivoActivity extends AppCompatActivity {

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    SharedPreferences prefs;
    private boolean flag=true;
    private Integer id=0;
    private String codigo;
    private String user;

    private String letter,sPos;
    private String kcant1,kcant2,kcant3,kcant4,ktotal;
    private  int pos, imag=0;

    public long p;
    long hat=1450656000000L;
    long hat2=2678400000L;//hat2=28908000000L; para los meses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efectivo);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);

        user=prefs.getString("kName","0");
        letter=prefs.getString("kLetra","0");
        sPos= prefs.getString("kPosicion","0");
        pos=Integer.valueOf(sPos);
        kcant1= prefs.getString("kCantidad1","0");
        kcant2= prefs.getString("kCantidad2","0");
        kcant3= prefs.getString("kCantidad3","0");
        kcant4= prefs.getString("kCantidad4","0");
        ktotal= prefs.getString("keyTotal","0");




    }
    public void btn_ComprarEfec(View view){
        Intent intent = new Intent(getApplicationContext(), CatalogoActivity.class);


        final long date = System.currentTimeMillis();//1800000 son 5 horas
        p=date-hat-hat2-18000000;//-hat2
        p=p/1000;

        Toast.makeText(getApplicationContext(),kcant1,Toast.LENGTH_SHORT).show();

        flag=true;
        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com
        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                while(flag) {

                    codigo = Integer.toString(id);
                    final String data = "Silla" + codigo;
                    if (dataSnapshot.child("Ubicacion").child(data).exists()) {
                        codigo = Integer.toString(id++);
                        flag = true;
                    } else {
                        final Firebase firebase;
                        final Firebase firebase1;
                        int k1,k2,k3,k4;
                        k1=Integer.parseInt(kcant1);
                        k2=Integer.parseInt(kcant2);
                        k3=Integer.parseInt(kcant3);
                        k4=Integer.parseInt(kcant4);
                        flag = false;
                        firebase = firebasedatos.child("Ubicacion" ); //creamos un hijo de firebasedatos
                        firebase1=firebase.child("Silla"+ id);
                        Silla silla = new Silla(letter,pos,Integer.parseInt(ktotal),p,user
                                ,k1
                                ,k2
                                ,k3
                                ,k4);
                        firebase1.setValue(silla);//agregamos a la base de datos

                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });
        startActivity(intent);
        finish();
    }
}
