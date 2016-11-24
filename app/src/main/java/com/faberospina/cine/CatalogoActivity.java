package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CatalogoActivity extends NavegacionActivity {

    ListView list;

    private Integer id=0;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;

    private  String KEY_FOR="";

    //Array list
    private Catalogo[] productos=
            new Catalogo[]{
                    new Catalogo(R.drawable.combo1,17500,"COMBO 1","1 Crispetas 170 Oz","2 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo2,14000,"COMBO 2","1 Crispetas 170 Oz","1 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo3,15300,"COMBO 3","1 Crispetas 130 Oz","1 Gaseosas 32 Oz y 1 Perro o Sandwich"),
                    new Catalogo(R.drawable.combo_grande_1,25500,"COMBO 4","1 Crispetas 170 Oz","2 Gaseosas 32 Oz"),
            };

    String posicion_lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //setContentView(R.layout.activity_catalogo);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_catalogo, contentFrameLayout);

        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);

        KEY_FOR= prefs.getString("kFormulario","01");



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//usuario actual
        if (user!=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();

            if(KEY_FOR=="ok"){
                //cambiar KNAME por el del formulario

                String name1 = prefs.getString("kName_R","0");
                String email1 = prefs.getString("kEmail_R","0");
                String uid1 = prefs.getString("kPass_R","0");

                SavePreferences("kName",name1);
                SavePreferences("kPass",uid1);
                SavePreferences("kEmail1",email1);

            }else {
                SavePreferences("kName",name);
                SavePreferences("kPass",uid);
                SavePreferences("kEmail1",email);
            }



        }else{

            if(KEY_FOR=="ok"){

                String name1 = prefs.getString("kName_R","0");
                String email1 = prefs.getString("kEmail_R","0");
                String uid1 = prefs.getString("kPass_R","0");

                SavePreferences("kName",name1);
                SavePreferences("kPass",uid1);
                SavePreferences("kEmail1",email1);

            }else {
                SavePreferences("kFormulario","01");
                logout();
            }

            //logout();
        }
        //Para el combo que se escogió
        //posicion_lista=getIntent().getExtras().getString("posicion");

        //Para la lista del catalogo
        Adapter adaptador=new Adapter(this,productos);
        list=(ListView)findViewById(R.id.Lista);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
                        SavePreferences("kPos",Integer.toString(position));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(),ShowActivity.class);
                        SavePreferences("kPos",Integer.toString(position));
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(),ShowActivity.class);
                        SavePreferences("kPos",Integer.toString(position));
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(),ShowActivity.class);
                        SavePreferences("kPos",Integer.toString(position));
                        startActivity(intent3);
                        break;
                }

                list.setItemChecked(position,true);
            }
        });

        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com


        Firebase firebase;
        Firebase firebase1;

        firebase = firebasedatos.child("catalogo");//Nombre de la tabla

        firebase1 = firebase.child("producto" + 0);//item de la tabla catalogo
        Catalogo setProductos = new Catalogo("COMBO 1", 17500, "1 Crispetas 170 Oz", "2 Gaseosas 32 Oz");//contenido del item
        firebase1.setValue(setProductos);

        firebase1 = firebase.child("producto" + 1);//item de la tabla catalogo
        Catalogo setProductos1 = new Catalogo("COMBO 2", 14000, "1 Crispetas 170 Oz", "1 Gaseosas 32 Oz");//contenido del item
        firebase1.setValue(setProductos1);

        firebase1 = firebase.child("producto" + 2);//item de la tabla catalogo
        Catalogo setProductos2 = new Catalogo("COMBO 3", 15300, "1 Crispetas 130 Oz", "1 Gaseosas 32 Oz y 1 Perro o Sandwich");//contenido del item
        firebase1.setValue(setProductos2);

        firebase1 = firebase.child("producto" + 3);//item de la tabla catalogo
        Catalogo setProductos3 = new Catalogo("COMBO Grande", 22500, "1 Crispetas 170 Oz", "2 Gaseosas 32 Oz");//contenido del item
        firebase1.setValue(setProductos3);

    }

    public void logout(){
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        goLoginActivity();
    }

    private void goLoginActivity() {

        Intent intent= new Intent(getApplicationContext(),PreLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       // finish();
    }


    public void btn_Carrito(View view){
        Intent intent = new Intent(getApplicationContext(),ResumenActivity.class);
        startActivity(intent);
    }
    class Adapter extends ArrayAdapter<Catalogo>{
        public Adapter(Context context, Catalogo[] productos){
            super(context,R.layout.layout_item1,productos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_item1,null);

            ImageView imagen = (ImageView)item.findViewById(R.id.imagen);
            imagen.setImageResource(productos[position].getIdImage());

            TextView tPrecio=(TextView)item.findViewById(R.id.tPrecio_list);
            tPrecio.setText(String.valueOf(productos[position].getPrecio()));

            TextView tCombos=(TextView)item.findViewById(R.id.tCombos);
            tCombos.setText(productos[position].getNombre());

            TextView tDescripcion1=(TextView)item.findViewById(R.id.tDescripcion1);
            tDescripcion1.setText(productos[position].getDescripcion1());

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tDescripcion2);
            tDescripcion2.setText(productos[position].getDescripcion2());

            return (item);
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
