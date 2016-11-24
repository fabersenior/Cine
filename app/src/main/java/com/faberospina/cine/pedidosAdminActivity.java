package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.security.AccessController.getContext;

public class pedidosAdminActivity extends AppCompatActivity {

    //Declaracion de la lista del catalogo
    ListView lista_catalogo;
    ArrayAdapter<String> adaptador;
    ArrayList<String> Lista_arreglos;

    private Integer id=0;
    private String codigo;
    private String hora,idImagen,letter,posi;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    ArrayList<Integer> s= new ArrayList<Integer>();
    ArrayList<Silla> list_silla = new ArrayList<Silla>();
    private boolean flag=true;
    private int HLONG=0,konteo=0;

    SharedPreferences prefs;

   private ArrayList<Lista_entrada> datos= new ArrayList<Lista_entrada>();

    Adapter adapterData;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pedidos_admin);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences",Context.MODE_PRIVATE);

        firebasedatos.setAndroidContext(getApplicationContext());//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        MyAsinc myAsincTask= new MyAsinc();
        myAsincTask.execute();

        adapterData = new Adapter(getApplicationContext(),datos);
        lst = (ListView) findViewById(R.id.lstf);
        lst.setAdapter(adapterData);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);// crea un nuevo intent
                //intent.putExtra("kPos",C[i]);
                startActivity(intent);
            }
        });

   
    }

    public void PUSH_ADAPTADOR(){

        adapterData = new Adapter(getApplicationContext(),datos);
        lst = (ListView) findViewById(R.id.lstf);
        lst.setAdapter(adapterData);
        //Toast.makeText(getApplicationContext(),String.valueOf(s),Toast.LENGTH_SHORT).show();
    }

    public  class  MyAsinc extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

/*            pdialog = new ProgressDialog(getApplicationContext());//DialogoActivity.class

            pdialog.setTitle("Downloading Image ...");
           pdialog.setMessage("Download in progress ...");
            //pdialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
            pdialog.setProgress(0);
            pdialog.setMax(20);
            pdialog.show();*/
            //para hacer el dialog de la burbuja de carga
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //Se busca en la base de datos Firebase
            id=0;
            firebasedatos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    codigo = Integer.toString(id);
                    while (flag){
                        final String data = "Silla"+codigo;
                        if (dataSnapshot.child("Ubicacion").child(data).exists()){
                            hora=dataSnapshot.child("Ubicacion").child(data).child("hora").getValue().toString();
                            letter=dataSnapshot.child("Ubicacion").child(data).child("letter").getValue().toString();
                            posi=dataSnapshot.child("Ubicacion").child(data).child("pos").getValue().toString() ;
                            idImagen =dataSnapshot.child("Ubicacion").child(data).child("idImagen").getValue().toString();
                            HLONG=Integer.parseInt(hora);

                            Silla sp1=new Silla(letter,Integer.parseInt(posi),Integer.parseInt(idImagen),Integer.parseInt(hora));

                            list_silla.add(sp1);
                            s.add(HLONG);
                            codigo = Integer.toString(++id);

                        }else {
                            flag=false;
                        }
                    }

                    Collections.sort(list_silla, new Comparator<Silla>() {
                        @Override
                        public int compare(Silla p1, Silla p2) {
                            return new Long(p1.getHora()).compareTo(new Long(p2.getHora()));
                        }
                    });


                    //Collections.sort(list_silla);

                    Lista_entrada l1= new Lista_entrada(R.drawable.textura_fondo,"",0,"","");
                    datos.add(l1);

                    Collections.sort(s);
                    PUSH_ADAPTADOR();

                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {        }
            });

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //carga el adapter a la list view
//            pdialog.dismiss();

 /*           MainActivity.Adapter adapter = new MainActivity.Adapter(getApplicationContext());
            list.setAdapter(adapter);*/
           // Collections.sort(s);
            Toast.makeText(getApplicationContext(),String.valueOf(s),Toast.LENGTH_SHORT).show();

            PUSH_ADAPTADOR();
            super.onPostExecute(aVoid);
           // Collections;
        }
    }


    class Adapter extends ArrayAdapter<Lista_entrada> {
        public Adapter(Context context, ArrayList<Lista_entrada> datos) {
            super(context,R.layout.layout_item1,datos);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String flag="";
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_item1,null);

            TextView nombre = (TextView)item.findViewById(R.id.tCombos);
            nombre.setText((datos.get(position)).getNombre());

            TextView descripcion = (TextView)item.findViewById(R.id.tDescripcion1);
            descripcion.setText((datos.get(position)).getInfo());

            TextView precio = (TextView)item.findViewById(R.id.tPrecio_list);
            precio.setText(String.valueOf((datos.get(position)).getPrecio()));//convertir el entero a String

            ImageView imagen = (ImageView) item.findViewById(R.id.imagen);
            imagen.setImageResource((datos.get(position)).getIdImagen());

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tDescripcion2);
            tDescripcion2.setText(datos.get(position).getInfo2());

         return (item);
        }
    }
}
