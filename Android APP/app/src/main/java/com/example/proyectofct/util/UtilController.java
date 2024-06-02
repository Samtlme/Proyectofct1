package com.example.proyectofct.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectofct.bbdd.entities.Preferencias;
import com.example.proyectofct.bbdd.prefsDAO;

import java.util.Locale;

public class UtilController {

    static SQLiteDatabase db;
    public static String getBaseConnectionString(Context context, SQLiteDatabase db, String endpoint){
        prefsDAO prefsDAO = new prefsDAO();
        Preferencias prefs = prefsDAO.getPreferences(db, context);
        String connectionString = "http://"+prefs.getIp()+":"+prefs.getPort()+"/"+endpoint;
        return connectionString;
    }

    public static void setUserIdPreferences(Context context, String userId){
        //context = getBaseContext
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.apply();
    }

    public static String getUserIdFromPreferentes(Context context){
        //context = getBaseContext
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "-1");
    }


}
