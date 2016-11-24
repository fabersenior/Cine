package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
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
    private String hora,idImagen,letter,posi,k1,k2,k3,k4,user;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    ArrayList<Integer> s= new ArrayList<Integer>();
    ArrayList<Silla> list_silla = new ArrayList<Silla>();
    private boolean flag=true;
    private int HLONG=0,konteo=0;
    private long mas=100000000000L;
    private long mhora=18000000L;
    private long minuto=1080000L;

    SharedPreferences prefs;

   private ArrayList<Lista_entrada> datos= new ArrayList<Lista_entrada>();

    private ArrayList<Silla> datos1= new ArrayList<Silla>();

    Adapter adapterData;
    ListView lst;

    private  String da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pedidos_admin);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences",Context.MODE_PRIVATE);

        da=prefs.getString("borrar","0");

        firebasedatos.setAndroidContext(getApplicationContext());//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        MyAsinc myAsincTask= new MyAsinc();
        myAsincTask.execute();

        adapterData = new Adapter(getApplicationContext(),list_silla);
        lst = (ListView) findViewById(R.id.lstf);
        lst.setAdapter(adapterData);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);// crea un nuevo intent
                //intent.putExtra("kPos",);
                String lit,pot;
                lit =list_silla.get(i).getLetter();
                pot= String.valueOf(list_silla.get(i).getPos());
                SavePreferences("letra",lit);
                SavePreferences("posillo",pot);
                startActivity(intent);
            }
        });

   
    }

    public void PUSH_ADAPTADOR(){


        if (da.equals("ok")){
            list_silla.remove(0);//primera ubicacion de la lista de orden
            SavePreferences("borrar","0");
            lst.invalidateViews();
            }

        adapterData = new Adapter(getApplicationContext(),list_silla);
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
                        String data = "Silla"+codigo;
                        if (dataSnapshot.child("Ubicacion").child(data).exists()){
                            hora=dataSnapshot.child("Ubicacion").child(data).child("hora").getValue().toString();
                            letter=dataSnapshot.child("Ubicacion").child(data).child("letter").getValue().toString();
                            posi=dataSnapshot.child("Ubicacion").child(data).child("pos").getValue().toString() ;
                            idImagen =dataSnapshot.child("Ubicacion").child(data).child("idImagen").getValue().toString();

                            k1= dataSnapshot.child("Ubicacion").child(data).child("k1").getValue().toString();
                            k2= dataSnapshot.child("Ubicacion").child(data).child("k2").getValue().toString();
                            k3= dataSnapshot.child("Ubicacion").child(data).child("k3").getValue().toString();
                            k4= dataSnapshot.child("Ubicacion").child(data).child("k4").getValue().toString();
                            user= dataSnapshot.child("Ubicacion").child(data).child("user").getValue().toString();
                            HLONG=Integer.parseInt(hora);

                            Silla sp1=new Silla(letter,Integer.parseInt(posi),Integer.parseInt(idImagen),
                                    Integer.parseInt(hora),user,Integer.parseInt(k1),Integer.parseInt(k2),
                                    Integer.parseInt(k3),Integer.parseInt(k4) );

                            list_silla.add(sp1);
                            s.add(HLONG);
                            codigo = Integer.toString(++id);

                        }else {
                            int c= Integer.parseInt(codigo);
                            c=c+1;
                            codigo = String.valueOf(c);
                            data = "Silla"+codigo;
                            if (dataSnapshot.child("Ubicacion").child(data).exists()){
                                codigo = Integer.toString(++id);
                            }else{
                                flag=false;
                            }

                        }
                    }

                    Collections.sort(list_silla, new Comparator<Silla>() {
                        @Override
                        public int compare(Silla p1, Silla p2) {
                            return new Long(p1.getHora()).compareTo(new Long(p2.getHora()));
                        }
                    });


                    //Collections.sort(list_silla);
/*                    Lista_entrada l1= new Lista_entrada(R.drawable.textura_fondo,"",0,"","");
                    datos.add(l1);*/

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
           // Toast.makeText(getApplicationContext(),String.valueOf(s),Toast.LENGTH_SHORT).show();

            //PUSH_ADAPTADOR();
            super.onPostExecute(aVoid);
           // Collections;
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


    class Adapter extends ArrayAdapter<Silla> {
        public Adapter(Context context, ArrayList<Silla> datos) {
            super(context,R.layout.layout_list_orden,datos);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            long hr=0L;
            String flag="";
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_list_orden,null);

            TextView nombre = (TextView)item.findViewById(R.id.tCombos);
            nombre.setText((list_silla.get(position)).getUser());

            TextView descripcion = (TextView)item.findViewById(R.id.tPrecio_list);
            descripcion.setText(String.valueOf(list_silla.get(position).getIdImagen()));

            ImageView imagen = (ImageView) item.findViewById(R.id.imagen);
            imagen.setImageResource(R.drawable.combo1);//Imagen Real

            TextView precio = (TextView)item.findViewById(R.id.tCantidad);//Total del pedido idImagen

            precio.setText(new StringBuilder().append(String.valueOf((list_silla.get(position)).getLetter())).append(String.valueOf(list_silla.get(position).getPos())).toString());//convertir el entero a String

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tHora);
            hr=list_silla.get(position).getHora()*1000;

            tDescripcion2.setText(DateFormat.format("HH:mm:ss", hr+mas-mhora+minuto));//Hora

         return (item);
        }
    }
}
