package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdminActivity extends AppCompatActivity {

    private String USERADMIN="admin";
    private String PASSADMIN="master";
    String nombre,password,codigo;
    Integer id=0;
    EditText editText1, editText2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_admin);
        editText1=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);

        SavePreferences("letra","0");
        SavePreferences("posillo","0");
        SavePreferences("borrar","-1");

    }

    public  void btnIngreso_admin(View view){

        int ok=0;
        id=0;
        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);

        if(editText1.length()==0 ){
            Toast.makeText(getApplicationContext(),"Ingrese Usuario",Toast.LENGTH_SHORT).show();
        }else{    //
            ok++;
            nombre=editText1.getText().toString();
        }
        if (editText2.length()==0){
            Toast.makeText(getApplicationContext(),"Ingrese contraseña",Toast.LENGTH_SHORT).show();
        }else {
            ok++;
            password=editText2.getText().toString();
        }
        //Log.d("Noload",String.valueOf(flag));
        if (ok>=2){
            final Intent te=intent;
            Log.d("Ok",String.valueOf(id));
            if (nombre.equals(USERADMIN)){
                if(password.equals(PASSADMIN)) {
                    String uu = "Bienvenido";
                    intent= new Intent(getApplicationContext(),pedidosAdminActivity.class);
                    startActivity(intent);
                    finish();
                    //cancel();
                }else{
                    Toast.makeText(LoginAdminActivity.this,"La contraseña ingresada es invalida", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginAdminActivity.this,"La usuario ingresado es invalido", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void SavePreferences(String key, String value){

        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
}
