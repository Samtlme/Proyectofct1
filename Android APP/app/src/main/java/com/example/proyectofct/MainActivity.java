package com.example.proyectofct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofct.bbdd.entities.Preferencias;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.bbdd.prefsDAO;
import com.example.proyectofct.util.UtilController;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //cargar imagenes y ajustes por defecto en BD
        try{

            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            prefsDAO prefsDAO = new prefsDAO();
            prefsDAO.resetPreferencesIfEmpty(db,getApplicationContext());

        }catch(Exception e){
            Toast.makeText(this, "Error reseteando preferencias.", Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }

        //test debug
//        try {
//            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
//            db = helper.getWritableDatabase();
//            prefsDAO prefsDAO = new prefsDAO();
//            Preferencias prefs = prefsDAO.getPreferences(db, getApplicationContext());
//            Toast.makeText(this,prefs.getIp() , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,prefs.getPort() , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,prefs.getLanguage() , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,"1"+prefs.getA_icon() , Toast.LENGTH_SHORT).show();
//
//            Toast.makeText(this,"3"+prefs.getS_icon() , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this,"2"+prefs.getB_icon() , Toast.LENGTH_SHORT).show();
//        }catch(Exception e){
//
//        }finally{
//            db.close();
//        }

        //navegacion a preferencias

        Button btn_opciones = findViewById(R.id.btn_options);
        btn_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpcionesActivity.class);
                startActivity(intent);
            }
        });

        //login

        Button btn_login = findViewById(R.id.btn_login);
        EditText et_usuario = findViewById(R.id.et_user);
        EditText et_contrasena = findViewById(R.id.et_pass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                    db = helper.getWritableDatabase();
                    prefsDAO prefsDAO = new prefsDAO();
                    Preferencias prefs = prefsDAO.getPreferences(db, getApplicationContext());

                    OkHttpClient client = new OkHttpClient();

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    String json = "{"
                            + "\"user\":\"" + et_usuario.getText().toString() + "\","
                            + "\"pass\":\"" + et_contrasena.getText().toString() + "\""
                            + "}";

                    RequestBody body = RequestBody.create(json, JSON);
                    Request request = new Request.Builder()
                            .url(UtilController.getBaseConnectionString(getApplicationContext(),db, "usuarios/login"))
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("ERROR", "No se pudo conectar." + e.toString());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String responseData;
                            if(response.isSuccessful()){
                                responseData = response.body().string();
                                //DEBUG
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Login correcto!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Log.d("OK", "Logeado exitosamente!");
                                Intent intent = new Intent(MainActivity.this, Menu.class);

                                // Almacenamos ID usuario
                                UtilController.setUserIdPreferences(getBaseContext(),responseData);
                                startActivity(intent);

                            }else{
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Login incorrecto.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Log.e("KO", "Conectado pero no logeado");
                            }
                        }
                    });

                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error de login", Toast.LENGTH_SHORT).show();
                }finally{
                    db.close();
                }


            }
        });

        //registro
        Button btn_registrarse = findViewById(R.id.btn_register);
        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });


    }
}