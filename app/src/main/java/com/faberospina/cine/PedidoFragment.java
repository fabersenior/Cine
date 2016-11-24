package com.faberospina.cine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoFragment extends Fragment {

    //Declaracion de la lista del catalogo
    ArrayAdapter<String> adaptador;
    ArrayList<String> Lista_arreglos;
    String KNombre;
    String KPrecio;
    String KDesc1;
    String KDesc2;
    int p;
    private Integer id=0;
    boolean flag=true;
    int imagen=0;
    ListView lista_resumen;
    String total1;
    String c1,c2,c3,c4;

    String text1,text2,text3,text4;
    Button btn;
    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    String codigo;
    private Firebase firebasedatos;
    int[] C={0,0,0,0,0,0};
    SharedPreferences prefs;
    private String k23,p23;
     String data;
    //ArrayList<Lista_entrada> data;
    ArrayList<Catalogo> promociones1 = new ArrayList<Catalogo>();
/*    private Lista_entrada[] datos= new Lista_entrada[]{
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.color.red,"",0,"","")
    };*/

    Adapter adapterData;
    ListView lista;

    public PedidoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_pedido, container, false);
      //  FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getContext());
        final Firebase[] firebase = new Firebase[1];


        prefs = getActivity().getSharedPreferences("com.sp.main_preferences", android.content.Context.MODE_PRIVATE);
        k23=prefs.getString("letra","0");
        p23=prefs.getString("posillo","0");

        firebasedatos.setAndroidContext(getContext());//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com

        btn = (Button) view.findViewById(R.id.btn_Despachar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(),pedidosAdminActivity.class);
                SavePreferences("letra","0");
                SavePreferences("posillo","0");

                firebase[0] = firebasedatos.child("Ubicacion").child(data);
                firebase[0].removeValue();
                SavePreferences("borrar","ok");

                startActivity(intent1);
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                while(flag) {
                    codigo = Integer.toString(id);
                     data = "Silla" + codigo;
                    if (dataSnapshot.child("Ubicacion").child(data).exists()){

                        if(dataSnapshot.child("Ubicacion").child(data).child("letter").getValue().toString()==k23
                                && dataSnapshot.child("Ubicacion").child(data).child("pos").getValue().toString()==p23
                                ){

                            c1=dataSnapshot.child("Ubicacion").child(data).child("k1").getValue().toString();
                            c2=dataSnapshot.child("Ubicacion").child(data).child("k2").getValue().toString();
                            c3=dataSnapshot.child("Ubicacion").child(data).child("k3").getValue().toString();
                            c4=dataSnapshot.child("Ubicacion").child(data).child("k4").getValue().toString();
                            flag=false;
                        }
                        codigo = Integer.toString(id++);

                    }else{
                        flag=false;
                    }

                }

                if(c1!="0"){
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
                        // total1 = prefs.getString("kCantidad1","07");
                        //p=Integer.parseInt(total1);
                        //total+=p;
                        imagen=R.drawable.combo1;

                        Catalogo cat1=new Catalogo(imagen,Integer.parseInt(KPrecio) ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }


                } if(c2!="0"){
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
/*                            total1 = prefs.getString("kCantidad2","07");
                            p=Integer.parseInt(total1);
                            total+=p;*/
                        imagen=R.drawable.combo2;
                        Catalogo cat1=new Catalogo(imagen,Integer.parseInt(KPrecio) ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }

                }if (c3!="0"){
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
/*                            total1 = prefs.getString("kCantidad3","07");
                            p=Integer.parseInt(total1);
                            total+=p;*/
                        imagen=R.drawable.combo3;


                        Catalogo cat1=new Catalogo(imagen,Integer.parseInt(KPrecio) ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);
                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }


                } if (c4!="0"){
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
/*                            total+=p;

                            p=Integer.parseInt(total1);*/
                        imagen=R.drawable.combo_grande_1;


                        Catalogo cat1=new Catalogo(imagen,Integer.parseInt(KPrecio) ,KNombre,KDesc1,KDesc2);
                        promociones1.add(cat1);

                    }else{
                        Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                    }

                }
                // textView.setText(String.valueOf(total));
                // SavePreferences("keyTotal",Integer.toString(total));
                // PUSH_ADAPTADOR();

                Adapter adaptador=new Adapter(getApplicationContext(),promociones1);
                lista_resumen=(ListView) view.findViewById(R.id.listafra);

                lista_resumen.invalidateViews();
                lista_resumen.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });


        //adapterData = new Adapter(this.getContext(),datos);
        lista = (ListView) view.findViewById(R.id.listafra);
        lista.setAdapter(adapterData);



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
/*                Intent intent = new Intent(getContext(), AdminActivity.class);// crea un nuevo intent
                //intent.putExtra("kPos",C[i]);
                startActivity(intent);*/

                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
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

            return (item);
        }


    }

    private void SavePreferences(String key, String value){

        SharedPreferences prefs = getActivity().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

}
