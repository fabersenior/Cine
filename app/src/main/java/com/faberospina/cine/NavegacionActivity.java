package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavegacionActivity extends AppCompatActivity {

    private String[] opciones = new String[] {"Catalogo", "Carrito de Compras","Mi perfil","Ubicanos","Cerrar"};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences prefs;
    private  String KEY_FOR="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        KEY_FOR= prefs.getString("kFormulario","01");


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//usuario actual
        if (user!=null){
            String name = user.getDisplayName();

            String email = user.getEmail();
            String uid = user.getUid();
            SavePreferences("kName",name);
            SavePreferences("kPass",uid);
            //SavePreferences("kEmail1",email);
/*            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();*/

        }else{
            if(KEY_FOR=="ok"){

            }else {
                SavePreferences("kFormulario","01");
                logout();
            }


        }

        drawerLayout = (DrawerLayout) findViewById(R.id.contenedorPrincipal);
        listView = (ListView) findViewById(R.id.menuIzq);

        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case(0): Intent intent = new Intent(NavegacionActivity.this, CatalogoActivity.class);
                        startActivity(intent);
                        finish();break;
                    case(1):  Intent intent2 = new Intent(NavegacionActivity.this, ResumenActivity.class);
                        startActivity(intent2);
                        finish();break;
                    case(2):  Intent intent3 = new Intent(NavegacionActivity.this, MiPerfilActivity.class);//PerfilActivty
                        startActivity(intent3);
                        finish();break;
                    case(3):  Intent intent4 = new Intent(NavegacionActivity.this, MapsActivity.class);
                        startActivity(intent4);
                        finish();break;
                    case(4):  logout();/*Intent intent1 = new Intent(NavegacionActivity.this, PreLoginActivity.class);
                        startActivity(intent1);*/
                        finish();break;


                }

                listView.setItemChecked(i,true);
                drawerLayout.closeDrawer(listView);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abierto, R.string.cerrado);

        drawerLayout.setDrawerListener(drawerToggle);



    }

    public void logout(){
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        goLoginActivity();
    }

    private void goLoginActivity() {

        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SavePreferences(String key, String value){
        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
