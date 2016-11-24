package com.faberospina.cine;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MiPerfilActivity extends NavegacionActivity {

    String usuario, contrasena,correo;
    TextView user,pass,mail;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_mi_perfil, contentFrameLayout);

        user=(TextView)findViewById(R.id.tUsuario);
        pass=(TextView)findViewById(R.id.tContrasena);
        mail=(TextView)findViewById(R.id.tCorreo);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        correo = prefs.getString("kEmail1","Sin_permisos");
        usuario = prefs.getString("kName","Sin_Permisos");
        contrasena = prefs.getString("kPass","07");

        user.setText(usuario);
        pass.setText(contrasena);
        mail.setText(correo);
    }
}
