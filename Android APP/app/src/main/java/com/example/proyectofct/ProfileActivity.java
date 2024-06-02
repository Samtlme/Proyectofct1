package com.example.proyectofct;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofct.bbdd.entities.Preferencias;
import com.example.proyectofct.bbdd.entities.Tareas;
import com.example.proyectofct.bbdd.entities.Usuarios;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.bbdd.prefsDAO;
import com.example.proyectofct.util.UtilController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Usuarios usuario = new Usuarios();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_nombre_perfil = findViewById(R.id.tvPerfilNombre);
        TextView tv_puntos_perfil = findViewById(R.id.tvPerfilPuntos);
        TextView tv_rol_perfil = findViewById(R.id.tvPerfilRol);
        TextView tv_profile_img = findViewById(R.id.tv_profile_image);

        /////
        try{

            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(UtilController.getBaseConnectionString(getApplicationContext(),db, "usuarios/"+UtilController.getUserIdFromPreferentes(getApplicationContext())))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("ERROR", "No se pudo conectar." + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData = "";

                    try {
                        responseData = response.body().string();
                        JSONObject usuariosDato = new JSONObject(responseData);

                        usuario = new Usuarios(
                                usuariosDato.getInt("id_usuario"),
                                usuariosDato.getString("nombre_usuario"),
                                null,
                                usuariosDato.getInt("puntos_totales"),
                                usuariosDato.getInt("rol"),
                                null
                        );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_profile_img.setText(String.valueOf(usuario.getNombre().charAt(0)));
                                tv_nombre_perfil.setText(usuario.getNombre());
                                tv_puntos_perfil.setText(usuario.getPuntos_totales().toString());

                                switch (usuario.getRol()){
                                    case 0:
                                        tv_rol_perfil.setText("Normal");
                                        break;
                                    case 1:
                                        tv_rol_perfil.setText("Administrador");
                                        break;
                                    default:
                                        tv_rol_perfil.setText("No especificado");   //DEBUG
                                }
                            }
                        });

                    } catch (JSONException jsonex) {
                        Log.e("KO", "Error de JSON en perfil.");
                    } finally {
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

        }catch(Exception e){
            Toast.makeText(ProfileActivity.this, "Error de login perfil", Toast.LENGTH_SHORT).show();
        }finally{
            if (db != null && db.isOpen()) {
                try {
                    db.close();
                } catch (Exception e) {
                    Log.e("KO", "error cerrando la BD");
                }
            }
        }

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