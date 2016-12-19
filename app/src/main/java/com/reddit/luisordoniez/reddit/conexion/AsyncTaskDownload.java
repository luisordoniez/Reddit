package com.reddit.luisordoniez.reddit.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by luisordoniez on 16/12/16.
 *
 * Clase que extiende de AsyncTask, utilizada para descargar files
 * por medio de su URL, en este caso imagenes
 */

public class AsyncTaskDownload extends AsyncTask<String, String, String> {

    /**
     * Atributos
     */

    Context context;
    String maneFile;
    String url;
    String path;

    /**
     * Constructor parametrizado
     *
     * @param context contexto de la app
     * @param url  ubicacion del recurso a descargar
     * @param path ubicacion de la descarga
     * @param nameFile nombre como se llamara el file
     */


    public AsyncTaskDownload(Context context,String url ,String path, String nameFile) {
        this.context = context;
        this.url = url;
        this.maneFile = nameFile;
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {

        int count;
        try {

            File folder = new File(path);
            folder.mkdirs();

            URL url = new URL(this.url);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            // Output stream to write file
            OutputStream output = new FileOutputStream( this.path +File.separator+ maneFile);

            byte data[] = new byte[8192];

            //int step = lenghtOfFile/8192;
            int increment = 0;

            while ((count = input.read(data)) != -1) {
                increment = increment + count;

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return "fail";
        }

        return "true";
    }

    /**
     * Update the progress dialog
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String respuesta) {

    }
}
