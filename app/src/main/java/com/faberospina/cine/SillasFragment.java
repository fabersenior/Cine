package com.faberospina.cine;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SillasFragment extends Fragment {

    ImageAdapter adapterData;

    private ArrayList<Silla> datos = new ArrayList<Silla>();

/*    private Silla[] datos= new Silla[]{
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_png),
            new Silla("",0,R.drawable.silla_verde)
    };*/

    public SillasFragment() {
        // Required empty public constructor
    }


    //Adapter adapterData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sillas, container, false);
        GridView gridView=(GridView)view.findViewById(R.id.gsillas);


        for(int k=0;k<18;k++){
           Silla silla=new Silla("",0,R.drawable.silla_png);
            datos.add(silla);
        }

       adapterData = new ImageAdapter(getContext(),datos);

        gridView.setAdapter(adapterData);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"hola"+position,Toast.LENGTH_SHORT).show();

            }
        });
        // Inflate the layout for this fragment
        return view;

     //  firebasedatos.setAndroidContext(getActivity().getApplicationContext());


    }
    class ImageAdapter  extends ArrayAdapter<Silla> {
        public ImageAdapter(android.content.Context context, ArrayList<Silla> datos) {
            super(context,R.layout.grid_item,datos);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String flag="";
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.grid_item,null);

/*            TextView nombre = (TextView) item.findViewById(R.id.tBizco);
            nombre.setText(datos[position].getNombre());
            TextView price= (TextView) item.findViewById(R.id.tPrecio);
            if(datos[position].getPrecio()>0) {
                price.setText(" $" + Integer.toString(datos[position].getPrecio()));
            }
            else{
                price.setText("");
            }
            // price.setText(Integer.toString(datos[position].getPrecio()));
            TextView info= (TextView) item.findViewById(R.id.tInfo);
            info.setText(datos[position].getInfo());*/

            ImageView imagen = (ImageView) item.findViewById(R.id.usilla);
            imagen.setImageResource(datos.get(position).getIdImagen());

            return (item);
        }
    }

}
