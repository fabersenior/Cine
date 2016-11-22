package com.faberospina.cine;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;//importar el sdk de fb
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    public String FIREBASE_URL="https://appcine-b45ca.firebaseio.com/";
    private Firebase firebasedatos;
    private static final int RC_SIGN_IN =1 ;
    private static final String TAG = "LoginActivity";
    EditText eName, ePass;

    Integer id=0;
    String nombre, codigo, password;
    String user = "", pass = "", email = "", var = " ";
    SharedPreferences prefs;
    boolean flag=true;
    private Calendar calendar;
    SharedPreferences.Editor editor;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private SignInButton mGoogleBtn;
    private GoogleApiClient mGoogleApiClient;
    private int option=0; //1.login con google , 2. login con fb
    private int year;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(getApplicationContext());
        firebasedatos = new Firebase(FIREBASE_URL);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        callbackManager = CallbackManager.Factory.create();

        mGoogleBtn = (SignInButton) findViewById(R.id.sign_in_button);
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });




       // String dateString =String.valueOf(date);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new  GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        SavePreferences("kConteo",String.valueOf(0));//reinicia el contador de # elementos en el carrito
        SavePreferences("combo1","1");
        SavePreferences("combo2","1");
        SavePreferences("combo3","1");
        SavePreferences("combo4","1");


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        // loginButton.setPublishPermissions();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {// autocompletar
            @Override
            public void onSuccess(LoginResult loginResult) {
                // goMainActivity();
                option=2;
                handleFacebookAccessToken(loginResult.getAccessToken());
            }


            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(), "Cancel Login", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(FacebookException error) {


                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();//cargar al usuario en el user

                if (user != null) {
                    goMainActivity();
                }

            }
        };
        prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);

        eName = (EditText) findViewById(R.id.editText);
        ePass = (EditText) findViewById(R.id.editText2);


        eName.setText("");
        ePass.setText("");


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());//obtener credeciales de fb
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //si la tarea no es exitosa
                    Toast.makeText(getApplicationContext(), "Error Login", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Login Ok", Toast.LENGTH_SHORT).show();

                }
            }
        });//iniciar loggin con esas credenciales
    }

    private void signIn() {
        option=1;

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {

        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        firebaseAuth.addAuthStateListener(fireAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.

        firebaseAuth.removeAuthStateListener(fireAuthStateListener);

    }

    private void goMainActivity() {
        Intent intent = new Intent(getApplicationContext(), CatalogoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

  /*  public  void  btnIngresar(View view){
goMainActivity();

    }*/
    public void btnIngresar(View view) {
        flag=true;
        int ok=0;
        id=0;
        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);

        if(eName.length()==0 ){
            Toast.makeText(getApplicationContext(),"Ingrese Usuario",Toast.LENGTH_SHORT).show();
        }else{    //
            ok++;
            nombre=eName.getText().toString();
        }
        if (ePass.length()==0){
            Toast.makeText(getApplicationContext(),"Ingrese contraseña",Toast.LENGTH_SHORT).show();
        }else {
            ok++;

            password=ePass.getText().toString();
        }
        //Log.d("Noload",String.valueOf(flag));
        if (ok>=2){
            final Intent te=intent;
            firebasedatos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Flag",String.valueOf(flag));
                    Log.d("Ok",String.valueOf(id));
                    while(flag) {
                        codigo = Integer.toString(id);
                        final String data = "Usuario" + codigo;
                        if(dataSnapshot.child("Contactos").child(data).child("nombre").exists()){
                            String nombrecito= dataSnapshot.child("Contactos").child(data).child("nombre").getValue().toString();
                            String contrasena= dataSnapshot.child("Contactos").child(data).child("pass").getValue().toString();
                            if (nombrecito.equals(nombre)){
                                if(contrasena.equals(password)) {
                                    String uu = "Bienvenido";
                                    Toast.makeText(LoginActivity.this, uu, Toast.LENGTH_SHORT).show();
                                    flag = false;
                                    startActivity(te);
                                    finish();
                                    //cancel();
                                }else{
                                    Toast.makeText(LoginActivity.this,"La contraseña ingresada es invalida", Toast.LENGTH_SHORT).show();
                                    flag=false;
                                }
                            }else {
                                codigo = Integer.toString(id++);
                            }
                        }else{
                            String uu="el usuario no existe";
                            Toast.makeText(LoginActivity.this,uu, Toast.LENGTH_SHORT).show();
                            flag=false;
                        }
                    }
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {        }
            });
/*            startActivity(intent);
            finish();*/

        }


    }

    public void btnRegistrarse(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);// crea un nuevo intent
        //Intent intent = new Intent();
        startActivityForResult(intent, 1234);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        eName.setText("");
        ePass.setText("");

        if (option==2){
            callbackManager.onActivityResult(requestCode, resultCode, data);//logeo con fb
           // goMainActivity();
        }else {


            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                } else {
                    // Google Sign In failed, update UI appropriately
                    // ...
                }
            }
        }
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            //prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
            editor = prefs.edit();
            user = data.getExtras().getString("kName");//obtengo el extra de RegistroActivity con la clave
            pass = data.getExtras().getString("kPass");
            email = data.getExtras().getString("kEmail");
            Log.d("var", prefs.getString("kName", "07:00"));
            Log.d("User", user);//lo imprime en el log(consola) o terminal
            Log.d("password", pass);
            Log.d("email", email);

        } else if (requestCode == 1234 && resultCode == RESULT_CANCELED) {
            // startActivity(getParentActivityIntent());
            Log.d("Noload", "NO hay datos");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public void cancel(){

        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
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
}
