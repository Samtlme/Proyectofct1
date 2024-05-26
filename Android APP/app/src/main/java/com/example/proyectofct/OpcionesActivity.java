package com.example.proyectofct;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofct.bbdd.entities.Preferencias;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.bbdd.prefsDAO;

public class OpcionesActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Preferencias prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.opciones), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //recuperamos opciones
        try {
            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            prefsDAO prefsdao = new prefsDAO();
            prefs = prefsdao.getPreferences(db,getApplicationContext());

        }catch(Exception e){
            Toast.makeText(this, "Error recuperando preferencias.", Toast.LENGTH_SHORT).show();
        }finally{
            db.close();
        }

        EditText et_ip = findViewById(R.id.et_IP);
        et_ip.setText(prefs.getIp());
        EditText et_port = findViewById(R.id.et_PORT);
        et_port.setText(prefs.getPort());

        //spinner opciones y eventos

        Spinner spinnerIdioma = findViewById(R.id.spinner_idioma);
        String[] spinnerOptions = {"Castellano", "Galego"};
        ArrayAdapter<String> adapter_idioma = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                spinnerOptions);
        spinnerIdioma.setAdapter(adapter_idioma);

        if(prefs.getLanguage().toUpperCase().equals("ES")){
            spinnerIdioma.setSelection(0);
        } else if(prefs.getLanguage().toUpperCase().equals("GA")){
            spinnerIdioma.setSelection(1);
        }

        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = spinnerOptions[position];
                if(selectedOption.equals("Castellano")){
                    prefs.setLanguage("ES");
                }else if(selectedOption.equals("Galego")){
                    prefs.setLanguage("GA");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_aceptar = findViewById(R.id.btn_aceptar);
        Button btn_cancelar = findViewById(R.id.btn_cancelar);
        Button btn_resetPrefs = findViewById(R.id.btn_resetPrefs);


        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                    db = helper.getWritableDatabase();
                    prefs.setIp(et_ip.getText().toString());
                    prefs.setPort(et_port.getText().toString());
                    prefsDAO prefsdao = new prefsDAO();
                    prefsdao.setPreferences(db,getApplicationContext(),prefs);
                    finish();

                }catch(Exception e){
                    Toast.makeText(OpcionesActivity.this, "Error guardando preferencias.", Toast.LENGTH_SHORT).show();
                }finally{
                    db.close();
                    finish();
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_resetPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                    db = helper.getWritableDatabase();
                    prefsDAO prefsdao = new prefsDAO();
                    prefsdao.resetPreferences(db, getApplicationContext());                    
                }catch(Exception e){
                    Toast.makeText(OpcionesActivity.this, "Error reseteando preferencias.", Toast.LENGTH_SHORT).show();
                }finally{
                    db.close();
                    finish();
                }
            }
        });

    }
}