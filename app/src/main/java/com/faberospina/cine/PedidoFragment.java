package com.faberospina.cine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoFragment extends Fragment {

    //Declaracion de la lista del catalogo
    ArrayAdapter<String> adaptador;
    ArrayList<String> Lista_arreglos;
    Button btn;
    int[] C={0,0,0,0,0,0};
    ArrayList<Lista_entrada> data;
    private Lista_entrada[] datos= new Lista_entrada[]{
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.drawable.textura_fondo,"",0,"",""),
            new Lista_entrada(R.color.red,"",0,"","")
    };

    Adapter adapterData;
    ListView lista;

    public PedidoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedido, container, false);
        adapterData = new Adapter(this.getContext(),datos);

        btn = (Button) view.findViewById(R.id.btn_Despachar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(),pedidosAdminActivity.class);
                startActivity(intent1);
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        lista = (ListView) view.findViewById(R.id.listafra);
        lista.setAdapter(adapterData);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getContext(), AdminActivity.class);// crea un nuevo intent
                //intent.putExtra("kPos",C[i]);
                startActivity(intent);

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

    class Adapter extends ArrayAdapter<Lista_entrada> {
        public Adapter(Context context, Lista_entrada[] datos) {
            super(context,R.layout.layout_item1,datos);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String flag="";
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_item1,null);
            TextView nombre = (TextView) item.findViewById(R.id.tCombos);
            nombre.setText(datos[position].getNombre());
            TextView price= (TextView) item.findViewById(R.id.tPrecio);
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
            imagen.setImageResource(datos[position].getIdImagen());

            return (item);
        }
    }

}
