package com.faberospina.cine;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences prefs;
    private String k,p;
    private int position,i;
    private ArrayList<Silla> datos = new ArrayList<Silla>();


    public SillasFragment() {
        // Required empty public constructor
    }


    //Adapter adapterData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sillas, container, false);
        GridView gridView=(GridView)view.findViewById(R.id.gsillas);

        prefs = getActivity().getSharedPreferences("com.sp.main_preferences", android.content.Context.MODE_PRIVATE);

        k=prefs.getString("letra","0");
        p=prefs.getString("posillo","0");

        position=Integer.parseInt(p)-1;

        for(int k=0;k<=17;k++) {
            Silla silla = new Silla("", 0, R.drawable.silla_png);
            datos.add(silla);
        }

        switch (k){
            case "A" :
                for(int k=0;k<=5;k++){
                   // Silla silla=new Silla("",0,R.drawable.silla_png);
                    if (k==position){
                        Silla sillav=new Silla("",0,R.drawable.silla_verde);
                        datos.add(position,sillav);
                        datos.remove(datos.size()-1);
                    }
                    //datos.add(silla);
                }

            break;
            case "B" :
                for(int k=6;k<=11;k++){
                    //Silla silla=new Silla("",0,R.drawable.silla_png);
                    if (k==position+6){
                        Silla sillav=new Silla("",0,R.drawable.silla_verde);
                        datos.add(position+6,sillav);
                        datos.remove(datos.size()-1);
                    }
                    //datos.add(silla);
                }

                break;
            case "C" :
                for(int k=12;k<=17;k++){
                    //Silla silla=new Silla("",0,R.drawable.silla_png);
                    if (k==position+12){
                        Silla sillav=new Silla("",0,R.drawable.silla_verde);
                        datos.add(position+12,sillav);
                        datos.remove(datos.size()-1);
                    }
                    //datos.add(silla);
                }
                break;
        }


       adapterData = new ImageAdapter(getContext(),datos);

        gridView.setAdapter(adapterData);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();

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
