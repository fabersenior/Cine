package com.faberospina.cine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ResumenActivity extends NavegacionActivity {


    private Integer id=0;
    private String codigo;
    private int total=0;
    private boolean flag=true;
    private TextView textView;
    ProgressDialog pdialog;
    int p;

    String text1,text2,text3,text4;
    int imagen=0;

    private String letter;
    private  int pos, imag;
    private  int KPOS=0;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;


    SharedPreferences prefs;
    //Declaracion de la lista del catalogo
    ListView lista_resumen;

    //Para el combo escogido
    String posicion_lista;
    ArrayList<Catalogo> promociones1 = new ArrayList<Catalogo>();
/*    Catalogo[] productos =
            new Catalogo[]{
                    new Catalogo(R.drawable.combo1, 17500, "COMBO 1", "1 Crispetas 170 Oz", "2 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo2,14000,"COMBO 2","1 Crispetas 170 Oz","1 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo3,15300,"COMBO 3","1 Crispetas 130 Oz","1 Gaseosas 32 Oz y 1 Perro o Sandwich"),
                    new Catalogo(R.drawable.combo_grande_1,25500,"COMBO 4","1 Crispetas 170 Oz","2 Gaseosas 32 Oz"),
            };*/

    //Declaracion Spinner
    Spinner spLetras,spNumeros;
    String Letra,Numero;
    String KNombre;
    String KPrecio;
    String KDesc1;
    String KDesc2;
    String c1,c2,c3,c4;
    int conteo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_resumen);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_resumen, contentFrameLayout);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences",Context.MODE_PRIVATE);

        KPOS = Integer.parseInt(prefs.getString("kPos","07"));
        conteo= Integer.parseInt(prefs.getString("kConteo","07"));

        c1= prefs.getString("combo1","07");
        c2=prefs.getString("combo2","07");
        c3=prefs.getString("combo3","07");
        c4=prefs.getString("combo4","07");

        textView = (TextView ) findViewById(R.id.tTotal);

        firebasedatos.setAndroidContext(getApplicationContext());//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        MyAsinc myAsincTask= new MyAsinc();
        myAsincTask.execute();


        //Para el combo que escogió
        //posicion_lista=getIntent().getExtras().getString("posicion");

        //Objeto de Letraes para Spinner
        spLetras = (Spinner) findViewById(R.id.Letras);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Arreglo_letras, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLetras.setAdapter(adapter);
        spLetras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
 /*               String p="";
                p= String.valueOf(spLetras.getContentDescription().charAt(position));
                Toast.makeText(getApplicationContext(),p,Toast.LENGTH_SHORT).show();*/
                switch(position) {
                    case 1:
                        Letra="A";
                        break;
                    case 2:
                        Letra="B";
                        break;
                    case 3:
                        Letra="C";
                        break;
                    case 4:
                        Letra="D";
                        break;
                    case 5:
                        Letra="E";
                        break;
                    case 6:
                        Letra="F";
                        break;
                    case 7:
                        Letra="G";
                        break;
                    case 8:
                        Letra="H";
                        break;
                    case 9:
                        Letra="I";
                        break;
                }
                letter=Letra;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Objeto de Letraes para Spinner
        spNumeros = (Spinner) findViewById(R.id.Numeros);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Arreglo_numeros, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spNumeros.setAdapter(adapter1);
        spNumeros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Numero=Integer.toString(position);
                pos=position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com


    }

    public void PUSH_ADAPTADOR(){

        Adapter adaptador=new Adapter(getApplicationContext(),promociones1);
        lista_resumen=(ListView)findViewById(R.id.lista_resumen);
        lista_resumen.setAdapter(adaptador);

    }

    public void btn_pagar(View view){

        letter=Letra;
        pos=Integer.parseInt(Numero);//+1

        SavePreferences("kLetra",letter);
        SavePreferences("kPosicion",Numero);

        flag=true;
        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com
        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                while(flag) {

                    codigo = Integer.toString(id);
                    final String data = "Silla" + codigo;

                    if (dataSnapshot.child("Ubicacion").child(data).exists()) {
                        // Log.d("datos",dataSnapshot.child(data).getValue().toString());
                        codigo = Integer.toString(id++);
                        flag = true;
                    } else {
                        final Firebase firebase;
                        final Firebase firebase1;
                        flag = false;
                        firebase = firebasedatos.child("Ubicacion" ); //creamos un hijo de firebasedatos
                        firebase1=firebase.child("Silla"+ id);

                        Silla silla = new Silla(letter,pos,imag);
                        firebase1.setValue(silla);//agregamos a la base de datos

                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });


        Intent intent = new Intent(getApplicationContext(),FormaPagoActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),Numero, Toast.LENGTH_SHORT).show();


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

            prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
            posicion_lista=prefs.getString("kPos","07");
            pos=Integer.parseInt(posicion_lista);

            final String data = "producto"+0;

            firebasedatos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                if(c1=="ok"){
                    final String data = "producto"+0;
                    if (dataSnapshot.child("catalogo").child(data).exists()){
                        //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                        text1= dataSnapshot.child("catalogo").child(data).child("descripcion1").getValue().toString();
                        text2=dataSnapshot.child("catalogo").child(data).child("descripcion2").getValue().toString();
                        text3 =dataSnapshot.child("catalogo").child(data).child("nombre").getValue().toString();
                        text4 = dataSnapshot.child("catalogo").child(data).child("precio").getValue().toString();

                        KNombre = text3;
                        KPrecio = text4;
                        KDesc1 = text1;
                        KDesc2 = text2;
                        p=Integer.parseInt(KPrecio);
                        total+=p;
                        imagen=R.drawable.combo1;

                        Catalogo cat1=new Catalogo(imagen,p ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }


                } if(c2=="ok"){
                    final String data = "producto"+1;

                    if (dataSnapshot.child("catalogo").child(data).exists()){
                        //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                        text1= dataSnapshot.child("catalogo").child(data).child("descripcion1").getValue().toString();
                        text2=dataSnapshot.child("catalogo").child(data).child("descripcion2").getValue().toString();
                        text3 =dataSnapshot.child("catalogo").child(data).child("nombre").getValue().toString();
                        text4 = dataSnapshot.child("catalogo").child(data).child("precio").getValue().toString();

                        KNombre = text3;
                        KPrecio = text4;
                        KDesc1 = text1;
                        KDesc2 = text2;
                        p=Integer.parseInt(KPrecio);
                        total+=p;
                        imagen=R.drawable.combo2;
                        Catalogo cat1=new Catalogo(imagen,p ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }

                }if (c3=="ok"){
                    final String data = "producto"+2;

                    if (dataSnapshot.child("catalogo").child(data).exists()){
                        //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                        text1= dataSnapshot.child("catalogo").child(data).child("descripcion1").getValue().toString();
                        text2=dataSnapshot.child("catalogo").child(data).child("descripcion2").getValue().toString();
                        text3 =dataSnapshot.child("catalogo").child(data).child("nombre").getValue().toString();
                        text4 = dataSnapshot.child("catalogo").child(data).child("precio").getValue().toString();

                        KNombre = text3;
                        KPrecio = text4;
                        KDesc1 = text1;
                        KDesc2 = text2;
                        p=Integer.parseInt(KPrecio);
                        imagen=R.drawable.combo3;
                        total+=p;

                        Catalogo cat1=new Catalogo(imagen,p ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }


                } if (c4=="ok"){
                    final String data = "producto"+3;

                    if (dataSnapshot.child("catalogo").child(data).exists()){
                        //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                        text1= dataSnapshot.child("catalogo").child(data).child("descripcion1").getValue().toString();
                        text2=dataSnapshot.child("catalogo").child(data).child("descripcion2").getValue().toString();
                        text3 =dataSnapshot.child("catalogo").child(data).child("nombre").getValue().toString();
                        text4 = dataSnapshot.child("catalogo").child(data).child("precio").getValue().toString();

                        KNombre = text3;
                        KPrecio = text4;
                        KDesc1 = text1;
                        KDesc2 = text2;
                        p=Integer.parseInt(KPrecio);
                        imagen=R.drawable.combo_grande_1;
                        total+=p;

                        Catalogo cat1=new Catalogo(imagen,p ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);

                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }

                }
                textView.setText(String.valueOf(total));
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
            PUSH_ADAPTADOR();
            super.onPostExecute(aVoid);
        }
    }

    private void SavePreferences(String key, String value){
        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    class Adapter extends ArrayAdapter<Catalogo>{
        public Adapter(Context context, ArrayList<Catalogo> productos){
            super(context,R.layout.layout_item1,productos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.layout_item1, null);

            TextView nombre = (TextView)item.findViewById(R.id.tCombos);
            nombre.setText((promociones1.get(position)).getNombre());

            TextView descripcion = (TextView)item.findViewById(R.id.tDescripcion1);
            descripcion.setText((promociones1.get(position)).getDescripcion1());

            TextView precio = (TextView)item.findViewById(R.id.tPrecio_list);
            precio.setText(String.valueOf((promociones1.get(position)).getPrecio()));//convertir el entero a String

            ImageView imagen = (ImageView) item.findViewById(R.id.imagen);
            imagen.setImageResource((promociones1.get(position)).getIdImage());

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tDescripcion2);
            tDescripcion2.setText(promociones1.get(position).getDescripcion2());
/*            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_item1,null);
            Toast.makeText(getApplicationContext(),Numero, Toast.LENGTH_SHORT).show();

            ImageView imagen = (ImageView)item.findViewById(R.id.imagen);
            imagen.setImageResource(productos[position].getIdImage());

            TextView tPrecio=(TextView)item.findViewById(R.id.tPrecio);
            tPrecio.setText(String.valueOf(productos[position].getPrecio()));

            TextView tCombos=(TextView)item.findViewById(R.id.tCombos);
            tCombos.setText(productos[position].getNombre());

            TextView tDescripcion1=(TextView)item.findViewById(R.id.tDescripcion1);
            tDescripcion1.setText(productos[position].getDescripcion1());

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tDescripcion2);
            tDescripcion2.setText(productos[position].getDescripcion2());*/

            return (item);
        }


    }}
