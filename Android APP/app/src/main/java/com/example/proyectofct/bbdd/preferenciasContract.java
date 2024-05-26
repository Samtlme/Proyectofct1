package com.example.proyectofct.bbdd;

import android.provider.BaseColumns;

public class preferenciasContract {

    preferenciasContract(){};

    public static class PreferenciasTabla implements BaseColumns{
        public static final String TABLE_NAME= "Preferencias";
        public static final String COLUMN_NAME_IP="ip";
        public static final String COLUMN_NAME_PORT="port";
        public static final String COLUMN_NAME_LANGUAGE="idioma";
        public static final String COLUMN_NAME_SPriority="prioridadS";
        public static final String COLUMN_NAME_APriority="prioridadA";
        public static final String COLUMN_NAME_BPriority="prioridadB";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PreferenciasTabla.TABLE_NAME + "(" +
                        PreferenciasTabla._ID + " INTEGER PRIMARY KEY, " +
                        PreferenciasTabla.COLUMN_NAME_IP + " TEXT, " +
                        PreferenciasTabla.COLUMN_NAME_PORT + " TEXT, " +
                        PreferenciasTabla.COLUMN_NAME_LANGUAGE + " TEXT, " +
                        PreferenciasTabla.COLUMN_NAME_SPriority + " TEXT, " +
                        PreferenciasTabla.COLUMN_NAME_APriority + " TEXT, " +
                        PreferenciasTabla.COLUMN_NAME_BPriority + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP DATABASE IF EXISTS " + PreferenciasTabla.TABLE_NAME;
    }

}

