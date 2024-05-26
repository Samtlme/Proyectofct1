package com.example.proyectofct.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.example.proyectofct.R;
import com.example.proyectofct.bbdd.entities.Preferencias;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class prefsDAO {

    //Leer preferencias
    public Preferencias getPreferences(SQLiteDatabase db, Context context){
        Cursor cursor = db.rawQuery("SELECT * FROM " + preferenciasContract.PreferenciasTabla.TABLE_NAME, null);
        Preferencias preferencias;
        if (cursor != null) {
            cursor.moveToFirst();
            preferencias = new Preferencias();
            preferencias.setId(cursor.getInt(0));
            preferencias.setIp(cursor.getString(1));
            preferencias.setPort(cursor.getString(2));
            preferencias.setLanguage(cursor.getString(3));
            preferencias.setS_icon(cursor.getString(4));
            preferencias.setA_icon(cursor.getString(5));
            preferencias.setB_icon(cursor.getString(6));

            cursor.close();
            return preferencias;
        }
        return null;
    }

    //guardar preferencias

    public void setPreferences(SQLiteDatabase db, Context context, Preferencias preferencias){

        ContentValues values = new ContentValues();
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_IP,preferencias.getIp());
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_PORT,preferencias.getPort());
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_LANGUAGE,preferencias.getLanguage());
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_SPriority,preferencias.getS_icon());
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_APriority,preferencias.getA_icon());
        values.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_BPriority,preferencias.getB_icon());

        String selection = preferenciasContract.PreferenciasTabla._ID + " = ?";
        String[] selectionArgs = { String.valueOf(preferencias.getId()) };

        int count = db.update(
                preferenciasContract.PreferenciasTabla.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if(count == 0){
            Toast.makeText(context, "Error actualizando las preferencias.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Prefencias actualizadas.", Toast.LENGTH_SHORT).show();
        }

    }

    public void resetPreferences(SQLiteDatabase db, Context context){

        //obtener string de imagenes
        String imageDataBlueflag="";
        String imageDataYellowflag="";
        String imageDataRedflag="";

        try{
            db.delete(preferenciasContract.PreferenciasTabla.TABLE_NAME,null,null);

            imageDataBlueflag = encodeImageToBase64(context, R.raw.blueflag);
            imageDataYellowflag = encodeImageToBase64(context, R.raw.yellowflag);
            imageDataRedflag = encodeImageToBase64(context, R.raw.redflag);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //crear los content values
        ContentValues valores  = new ContentValues();

        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_IP, "127.0.0.1");
        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_PORT, "8080");
        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_LANGUAGE,"ES");
        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_SPriority, imageDataRedflag);
        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_APriority, imageDataYellowflag);
        valores.put(preferenciasContract.PreferenciasTabla.COLUMN_NAME_BPriority, imageDataBlueflag);

        long newRowId = db.insert(preferenciasContract.PreferenciasTabla.TABLE_NAME,
                null, valores);

    }

    public String encodeImageToBase64(Context context, int imageId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(imageId);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetPreferencesIfEmpty(SQLiteDatabase db, Context context) {
        boolean isTableEmpty = true;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + preferenciasContract.PreferenciasTabla.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count > 0) {
                isTableEmpty = false;
            }
            cursor.close();
        }

        if (isTableEmpty) {
            resetPreferences(db, context);
        }
    }

}
