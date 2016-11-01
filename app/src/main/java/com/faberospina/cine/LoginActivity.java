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

public class LoginActivity extends AppCompatActivity {

    EditText eName,ePass;
    String user="",pass="",email="",var=" ";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);

        eName=(EditText) findViewById(R.id.editText);
        ePass= (EditText) findViewById(R.id.editText2);



        eName.setText("");
        ePass.setText("");
    }

    public  void btnIngresar(View view){

        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
        startActivity(intent);
    }
    public void btnRegistrarse(View view){
        Intent intent = new Intent(this,RegistroActivity.class);// crea un nuevo intent
        //Intent intent = new Intent();
        startActivityForResult(intent,1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        eName.setText("");
        ePass.setText("");

        if (requestCode==1234 && resultCode==RESULT_OK){
            //prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs =getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
            editor = prefs.edit();
            user = data.getExtras().getString("kName");//obtengo el extra de RegistroActivity con la clave
            pass = data.getExtras().getString("kPass");
            email = data.getExtras().getString("kEmail");
            Log.d("var",prefs.getString("kName", "07:00"));
            Log.d("User",user);//lo imprime en el log(consola) o terminal
            Log.d("password",pass);
            Log.d("email",email);

        }else if(requestCode==1234 && resultCode==RESULT_CANCELED){
            // startActivity(getParentActivityIntent());
            Log.d("Noload","NO hay datos");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
