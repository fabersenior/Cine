package com.faberospina.cine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static com.faberospina.cine.R.color.white;

public class RegistroActivity extends AppCompatActivity{

    public String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    Integer id=0;

    private Calendar calendar;

    private int year,month, day;
    boolean flag=true;
    boolean fecha;
    private TextView dateView;

    String nombre, correo,pass,codigo,sexo;
    EditText eId, eName, ePass,ePass2, eEmail;

    ArrayList<Contacto> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_registro);

        Firebase.setAndroidContext(getApplicationContext());
        firebasedatos = new Firebase(FIREBASE_URL);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        eName = (EditText) findViewById(R.id.eLogin);
        ePass = (EditText) findViewById(R.id.ePass1);
        ePass2 = (EditText) findViewById(R.id.ePass2);
        eEmail = (EditText) findViewById(R.id.eCorreo);
        dateView = (TextView) findViewById(R.id.tFecha);

        info = new ArrayList<Contacto>();

    }
    public void btn_Registro(View view){

        final Intent intent = new Intent();
        final int[] ok = {0};
        flag=true;
      
       // Toast.makeText(getApplicationContext(),String.valueOf(year),Toast.LENGTH_SHORT).show();

        nombre = eName.getText().toString();
        correo = eEmail.getText().toString();
        pass = ePass.getText().toString();

        if(eName.length()==0){
            Toast.makeText(getApplicationContext(),"Nombre Vacio",Toast.LENGTH_SHORT).show();
        }else{
            intent.putExtra("kName",eName.getText().toString());
            SavePreferences("kName",eName.getText().toString());

            ok[0]++;
        }

        if(ePass.length()==0){
            Toast.makeText(getApplicationContext(), "Ingrese Password", Toast.LENGTH_SHORT)
                    .show();

        }else{
            pass=ePass.getText().toString();
            intent.putExtra("kPass",pass);
            //editor.putString("kPass",pass);

            ok[0]++;
        }
        if(ePass2.length()==0){
            Toast.makeText(getApplicationContext(), "Ingrese Password", Toast.LENGTH_SHORT)
                    .show();
            //info="Informacion: ";

        }else{
            if(pass.equals(ePass2.getText().toString()) ){
                Toast.makeText(getApplicationContext(), "Password correct", Toast.LENGTH_SHORT)
                        .show();
                intent.putExtra("kPass",pass);
                //editor.putString("kPass",pass);
                SavePreferences("kPass",pass);
                ok[0]++;//3

            }else{
                Toast.makeText(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (fecha){
            intent.putExtra("año",year);
            //editor.putString("kPass",pass);
            firebasedatos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    while(flag) {
                        codigo = Integer.toString(id);
                        final String data = "Usuario" + codigo;
                        if(dataSnapshot.child("Contactos").child(data).child("mail").exists()){
                            String mail= dataSnapshot.child("Contactos").child(data).child("mail").getValue().toString();

                            if (mail.equals(correo)){
                                String uu="el usuario ya existe";
                                Toast.makeText(RegistroActivity.this,uu, Toast.LENGTH_SHORT).show();
                                // codigo = Integer.toString(id++);
                                flag=false;
                                cancel();
                            }else {
                                codigo = Integer.toString(id++);
                            }
                        }else{
                            if (dataSnapshot.child("Contactos").child(data).exists()) {
                                // Log.d("datos",dataSnapshot.child(data).getValue().toString());
                                String uu="el usuario ya existe";
                                //Toast.makeText(RegistroActivity.this,uu, Toast.LENGTH_SHORT).show();
                                codigo = Integer.toString(id++);
                                // Toast.makeText(RegistroActivity.this,codigo, Toast.LENGTH_SHORT).show();
                                flag = true;
                            } else {
                                final Firebase firebase;
                                final Firebase firebase1;
                                flag = false;
                                ok[0]++;//5
                                firebase = firebasedatos.child("Contactos"); //creamos un hijo de firebasedatos
                                firebase1= firebase.child("Usuario" + id);
                                Contacto contacto = new Contacto(nombre, pass, correo, String.valueOf(id), year);
                                firebase1.setValue(contacto);
                                Toast.makeText(getApplicationContext(), "Usuario Agregado", Toast.LENGTH_SHORT).show();
                                Log.d("valor k:",Integer.toString(ok[0]));
                                if (ok[0] >=5){
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {        }
            });

        }else{
            Toast.makeText(getApplicationContext(), "Ingrese Fecha", Toast.LENGTH_SHORT)
                    .show();
        }
        if (eEmail.length()==0){
            Toast.makeText(getApplicationContext(), "Ingrese Correo", Toast.LENGTH_SHORT)
                    .show();

        }else{
            correo=eEmail.getText().toString();
            intent.putExtra("kEmail",correo);
            SavePreferences("kEmail",correo);
            ok[0]++;//4



          //  Toast.makeText(RegistroActivity.this,Boolean.toString(flag), Toast.LENGTH_SHORT).show();


        }

/*        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);*/
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

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Defina la Fecha de nacimiento", Toast.LENGTH_SHORT)
                .show();
    }

    protected Dialog onCreateDialog(int idp) {//id del dialogo

        if (idp == 999) {//pregunto si el id es igual al dialogo del suceso
            //ok++;
            return new DatePickerDialog(this, myDateListener, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
            year=arg1;
        }
    };

    private void showDate(int year, int month, int day) {

        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dateView.setTextColor(getResources().getColor(white));
        fecha=true;
/*
        Toast.makeText(getApplicationContext(),new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year),Toast.LENGTH_SHORT).show();
*/

    }

    public  void  btn_Cancelar(View view){
            cancel();
    }

    public void cancel(){

        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

}
