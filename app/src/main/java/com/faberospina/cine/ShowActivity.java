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
    TextView title,precio,des1,des2;

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

                    title.setText(text3);
                    precio.setText(text4);
                    des1.setText(text1);
                    des2.setText(text2);
                    //Toast.makeText(getApplicationContext(),"DATOS",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),text3,Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });


/*    DELAY DE 1 segundo
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

            }
        }, 1000);*/


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
        startActivity(intent);
        finish();
    }
}
