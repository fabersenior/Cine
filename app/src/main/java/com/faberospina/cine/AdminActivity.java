package com.faberospina.cine;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    Integer id=0;
    String rep1,rep2;

    private String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    String codigo;
    private Firebase firebasedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        codigo=String.valueOf(id);

        //this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);

        firebasedatos.setAndroidContext(this);//contexto con que vamos a trabjar el firebase
        firebasedatos=new Firebase(FIREBASE_URL);//constructor de firebase nos pide el com


        final String data = "Silla"+codigo;//codigo tiene al id que se quiere buscar de ubicacion

        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Ubicacion").child(data).exists()){
                    //Log.d("datos",dataSnapshot.child(data).getValue().toString());
                    if(dataSnapshot.child("Ubicacion").child(data).child("letter").exists()){

                        rep1=dataSnapshot.child("Ubicacion").child(data).child("letter").getValue().toString();
                        rep2=dataSnapshot.child("Ubicacion").child(data).child("pos").getValue().toString();
                    }else{
                        rep1="";
                        rep2="";
                    }
                    SavePreferences("KLetter",rep1);
                    Toast.makeText(getApplicationContext(),rep1+rep2,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"no datos",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {        }
        });

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //rep();

        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        ActionBar.Tab tab= actionBar.newTab().setText("Ubicacion").setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Pedidos").setTabListener(tabListener);
        actionBar.addTab(tab);


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        }  );
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new SillasFragment();
                case 1: return new PedidoFragment();
                default: return null;

            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void SavePreferences(String key, String value){

        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
}
