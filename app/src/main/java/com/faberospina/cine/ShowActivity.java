package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ShowActivity extends AppCompatActivity {

    SharedPreferences prefs;
    ImageView imageShow;
    private  int pos;
    int conteo=0;
    int total=0;
    int valor=0;
    TextView title,precio,des1,des2;

    EditText cantidad;
    String cantidades;

    private  String codigo;
    private String text1,text2,text3,text4;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;

    String posicion_lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show);

        //Para el combo que se escogió
       // posicion_lista=getIntent().getExtras().getString("posicion");
         prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        posicion_lista=prefs.getString("kPos","07");
        pos=Integer.parseInt(posicion_lista);
       // Toast.makeText(getApplicationContext(),posicion_lista, Toast.LENGTH_SHORT).show();

        imageShow = (ImageView) findViewById(R.id.imageShow);
        title= (TextView) findViewById(R.id.tCombo);
        precio= (TextView) findViewById(R.id.price);
        des1= (TextView) findViewById(R.id.des1);
        des2= (TextView) findViewById(R.id.des2);
        cantidad=(EditText) findViewById(R.id.eCantidad);

        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        final String data = "producto"+pos;

        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("catalogo").child(data).exists()){
                    //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                    text1= dataSnapshot.child("catalogo").child(data).child("descripcion1").getValue().toString();
                    text2=dataSnapshot.child("catalogo").child(data).child("descripcion2").getValue().toString();
                    text3 =dataSnapshot.child("catalogo").child(data).child("nombre").getValue().toString();
                    text4 = dataSnapshot.child("catalogo").child(data).child("precio").getValue().toString();

/*
                    SavePreferences("k_Nombre",text3);//enviar por preferencias compartidas el texto
                    SavePreferences("k_precio",text4); // estas prefs enviarlas a resumenActivity
                    SavePreferences("k_descripcion1",text1);
                    SavePreferences("k_descripcion2",text2);
*/

                    title.setText(text3);
                    precio.setText(text4);
                    des1.setText(text1);
                    des2.setText(text2);


                }else{
                    Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });

        switch (pos){
            case 0://obtenerlas de la base de datos!
                imageShow.setImageResource(R.drawable.combo1);
                break;
            case 1:
                imageShow.setImageResource(R.drawable.combo2);
                break;
            case 2:
                imageShow.setImageResource(R.drawable.combo3);
                break;
            case 3:
                imageShow.setImageResource(R.drawable.combo_grande_1);
                break;
        }
    }
    public  void btn_Añadir_Carrito(View view){

        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
        cantidades=cantidad.getText().toString();
        conteo = Integer.parseInt(prefs.getString("kConteo","07"));
        conteo++;
        SavePreferences("kConteo",String.valueOf(conteo));
        switch (pos){
            case 0://obtenerlas de la base de datos!
              // imageShow.setImageResource(R.drawable.combo1);
                SavePreferences("combo1","ok");
                if(cantidad.length()==0){
                    Toast.makeText(getApplicationContext(),"Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                }else {
                    total = (Integer.parseInt(text4)) * (Integer.parseInt(cantidades));
                    SavePreferences("kCantidad1", Integer.toString(total));
                    startActivity(intent);
                    finish();
                }
                break;
            case 1:
                //imageShow.setImageResource(R.drawable.combo2);
                SavePreferences("combo2","ok");
                if(cantidad.length()==0){
                    Toast.makeText(getApplicationContext(),"Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                }else {
                    total = (Integer.parseInt(text4)) * (Integer.parseInt(cantidades));
                    SavePreferences("kCantidad2", Integer.toString(total));
                    startActivity(intent);
                    finish();
                }
                break;
            case 2:
                //imageShow.setImageResource(R.drawable.combo3);
                SavePreferences("combo3","ok");
                if(cantidad.length()==0){
                    Toast.makeText(getApplicationContext(),"Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                }else {
                    total = (Integer.parseInt(text4)) * (Integer.parseInt(cantidades));
                    SavePreferences("kCantidad3", Integer.toString(total));
                    startActivity(intent);
                    finish();
                }
                break;
            case 3:
                //imageShow.setImageResource(R.drawable.combo_grande_1);
                SavePreferences("combo4","ok");
                if(cantidad.length()==0){
                    Toast.makeText(getApplicationContext(),"Ingrese la cantidad", Toast.LENGTH_SHORT).show();
                }else {
                    total = (Integer.parseInt(text4)) * (Integer.parseInt(cantidades));
                    SavePreferences("kCantidad4", Integer.toString(total));
                    startActivity(intent);
                    finish();
                }
                break;
        }


    }

    private void SavePreferences(String key, String value){
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        //Toast.makeText(getApplicationContext(),prefs.getString("kName","08:00"),Toast.LENGTH_SHORT).show();
        editor.commit();
/*        Intent sd=new Intent(this,Secongtess.class);
        startActivity(sd);*/
    }
}
