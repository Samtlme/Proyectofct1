package com.example.proyectofct;

import android.content.Intent;
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
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistroActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText et_reg_user = findViewById(R.id.et_user_signup);
        EditText et_reg_pass = findViewById(R.id.et_pass_signup);
        EditText et_red_rpass = findViewById(R.id.et_rpass_signup);
        Button btn_cancel = findViewById(R.id.btn_registro_cancelar);
        Button btn_reg_reg = findViewById(R.id.btn_registro_confirmar);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_reg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_reg_pass.getText().toString().length() < 3 ||
                        et_red_rpass.getText().toString().length() < 3 ||
                        et_reg_user.getText().toString().length() < 3){
                    Toast.makeText(RegistroActivity.this, "Ningun campo debe tener menos de 3 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_reg_pass.getText().toString().equals(et_red_rpass.getText().toString())){
                    try{

                        preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                        db = helper.getWritableDatabase();
                        prefsDAO prefsDAO = new prefsDAO();
                        Preferencias prefs = prefsDAO.getPreferences(db, getApplicationContext());
                        OkHttpClient client = new OkHttpClient();

                        MediaType JSON = MediaType.get("application/json; charset=utf-8");
                        String json = "{"
                                + "\"user\":\"" + et_reg_user.getText().toString() + "\","
                                + "\"pass\":\"" + et_reg_pass.getText().toString() + "\""
                                + "}";

                        RequestBody body = RequestBody.create(json, JSON);
                        Request request = new Request.Builder()
                                .url(UtilController.getBaseConnectionString(getApplicationContext(),db, "usuarios/register"))
                                .post(body)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("ERROR", "No se pudo conectar registro." + e.toString());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    RegistroActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegistroActivity.this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                    Log.d("OK", "Registrado exitosamente!");

                                }else if(response.code() == HttpURLConnection.HTTP_FORBIDDEN){
                                    RegistroActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegistroActivity.this, "Nombre de usuario no disponible.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.d("OK", "nombre no disponible!");
                                }else{
                                    RegistroActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegistroActivity.this, "Registro fallido.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.e("KO", "Conectado pero no logeado");
                                }
                            }
                        });

                    }catch(Exception e){
                        Toast.makeText(RegistroActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();
                    }finally{
                        if (db != null && db.isOpen()) {
                            try {
                                db.close();
                            } catch (Exception e) {
                                Log.e("KO", "error cerrando la BD");
                            }
                        }
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                    if (db != null && db.isOpen()) {
                        try {
                            db.close();
                        } catch (Exception e) {
                            Log.e("KO", "error cerrando la BD");
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (Exception e) {
                Log.e("KO", "error cerrando la BD");
            }
        }
        super.onDestroy();
    }
}