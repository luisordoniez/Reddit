package com.reddit.luisordoniez.reddit.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by luisordoniez on 15/12/16.
 * <p/>
 * Clase que permite ser instanciada en cualquier activity con el fin hacer
 * utilidad de sus metodos que son generales y de frecuente uso.
 */

public class GlobalClass extends Application {

    /**
     * Constructor por defecto
     */

    public GlobalClass() {
        super();
    }

    /**
     * Metrodo que indica si el dispositivo cuneta con internet WIFI o no
     *
     * @return 'false'  no hay internet WIFI
     */

    public Boolean conectWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //NetworkInfo[] redes = connectivity.getAllNetworkInfo();
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metrodo que indica si el dispositivo cuneta con internet MOBILE o no
     *
     * @return 'false'  no hay internet MOBILE
     */

    public Boolean conectRedMovil() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Metrodo que indica si el dispositivo cuneta con internet MOBILE o WIFI
     *
     * @return 'false'  no hay internet MOBILE o WIFI
     */

    public Boolean TestConection() {

        return conectWifi() || conectRedMovil();

    }

    /**
     * Crea y guarda un file llamado 'Constants.NAME_FILE' que contiene una copia
     * del JSON obtenido al consumir servicio.
     *
     * @param json objeto que se guarda en el file
     * @see 'AsyncTaskJson' servicio GET
     */

    public void saveBachup(JSONObject json) {

        File file = new File(this.getFilesDir(), Constants.NAME_APLICATION);

        try {
            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            openFileOutput(Constants.NAME_FILE, Context.MODE_PRIVATE));

            fout.write(json.toString());
            fout.close();

            Log.i("Ficheros", "Fichero creado!");
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    /**
     * Lee un file llamado 'Constants.NAME_FILE' que contiene una copia
     * del JSON obtenido al consumir servicio.
     *
     * @return "JSONObjet lleno" si el archivo existe y esta lleno
     * @see 'AsyncTaskJson' servicio GET
     */

    public JSONObject readBachup() {

        try {
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(
                            openFileInput(Constants.NAME_FILE)));

            String texto = fin.readLine();

            fin.close();

            Log.i("Ficheros", "Fichero leido!");
            Log.i("Ficheros", "Texto: " + texto);

            return new JSONObject(texto);
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
            return new JSONObject();
        }
    }

    /**
     * Obtiene la ultima modificacion hecha al file llamado 'Constants.NAME_FILE'
     *
     * @return fecha de la ultima modificacion
     */

    public String getFechaBachup() {
        File fichero = new File(this.getFilesDir().getPath() + "/" + Constants.NAME_FILE);
        long time = fichero.lastModified();
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(time));
        return dateString;
    }
}
