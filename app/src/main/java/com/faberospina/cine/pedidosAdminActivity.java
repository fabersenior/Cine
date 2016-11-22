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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class pedidosAdminActivity extends AppCompatActivity {

    //Declaracion de la lista del catalogo
    ListView lista_catalogo;
    ArrayAdapter<String> adaptador;
    ArrayList<String> Lista_arreglos;


    SharedPreferences prefs;

    private  String kcompra="";
    int[] C={0,0,0,0,0,0};

   private ArrayList<Lista_entrada> datos= new ArrayList<Lista_entrada>();
/*    private Lista_entrada[] datos= new Lista_entrada[]{
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"","")
    };*/

    Adapter adapterData;

    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pedidos_admin);

        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences",Context.MODE_PRIVATE);

        kcompra=prefs.getString("kCompra","01");

        //Para la lista de pedidos
     /*   lista_catalogo =(ListView)findViewById(R.id.Lista);
        Lista_arreglos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Lista_arreglos);
        //lista_catalogo.setAdapter(adaptador);*/

        //datos.remove(0);

        if(kcompra=="ok"){
            Lista_entrada l1= new Lista_entrada(R.drawable.textura_fondo,"",0,"","");
            datos.add(l1);
        }



        adapterData = new Adapter(getApplicationContext(),datos);
        lst = (ListView) findViewById(R.id.lstf);
        lst.setAdapter(adapterData);



        //aceptable

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);// crea un nuevo intent
                //intent.putExtra("kPos",C[i]);
                startActivity(intent);
            }
        });

   
    }

     public  void image_OnClick(View view){
         //Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
         //startActivity(intent);

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

/*            TextView nombre = (TextView) item.findViewById(R.id.tCombos);
            nombre.setText(datos[position].getNombre());
            TextView price= (TextView) item.findViewById(R.id.tPrecio_list);
            if(datos[position].getPrecio()>0) {
                price.setText(" $" + Integer.toString(datos[position].getPrecio()));
            }
            else{
                price.setText("");
            }
            // price.setText(Integer.toString(datos[position].getPrecio()));
            TextView info= (TextView) item.findViewById(R.id.tDescripcion1);
            info.setText(datos[position].getInfo());
            TextView info2= (TextView) item.findViewById(R.id.tDescripcion2);
            info2.setText(datos[position].getInfo());
            ImageView imagen = (ImageView) item.findViewById(R.id.imagen);
            imagen.setImageResource(datos[position].getIdImagen());*/

            return (item);
        }
    }
}
