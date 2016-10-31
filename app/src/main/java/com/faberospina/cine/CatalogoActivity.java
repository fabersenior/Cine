package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

public class CatalogoActivity extends NavegacionActivity {

    ListView list;
    private Catalogo[] productos=
            new Catalogo[]{
                    new Catalogo(R.drawable.combo1,17500,"COMBO 1","1 Crispetas 170 Oz","2 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo2,14000,"COMBO 2","1 Crispetas 170 Oz","1 Gaseosas 32 Oz"),
                    new Catalogo(R.drawable.combo3,15300,"COMBO 3","1 Crispetas 130 Oz","1 Gaseosas 32 Oz y 1 Perro o Sandwich"),
                    new Catalogo(R.drawable.combo_grande_1,25500,"COMBO 4","1 Crispetas 170 Oz","2 Gaseosas 32 Oz"),
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_catalogo);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_catalogo, contentFrameLayout);

        //Para la lista del catalogo
        Adapter adaptador=new Adapter(this,productos);
        list=(ListView)findViewById(R.id.Lista);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(),ShowActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(),ShowActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(),ShowActivity.class);
                        startActivity(intent3);
                        break;
                }

                list.setItemChecked(position,true);
            }
        });

    }


    public void btn_Carrito(View view){
        Intent intent = new Intent(getApplicationContext(),ResumenActivity.class);
        startActivity(intent);
    }
    class Adapter extends ArrayAdapter<Catalogo>{
        public Adapter(Context context, Catalogo[] productos){
            super(context,R.layout.layout_item1,productos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.layout_item1,null);

            ImageView imagen = (ImageView)item.findViewById(R.id.imagen);
            imagen.setImageResource(productos[position].getIdImage());

            TextView tPrecio=(TextView)item.findViewById(R.id.tPrecio);
            tPrecio.setText(String.valueOf(productos[position].getPrecio()));

            TextView tCombos=(TextView)item.findViewById(R.id.tCombos);
            tCombos.setText(productos[position].getNombre());

            TextView tDescripcion1=(TextView)item.findViewById(R.id.tDescripcion1);
            tDescripcion1.setText(productos[position].getDescripcion1());

            TextView tDescripcion2=(TextView)item.findViewById(R.id.tDescripcion2);
            tDescripcion2.setText(productos[position].getDescripcion2());

            return (item);
        }
    }
}
