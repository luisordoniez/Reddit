package com.reddit.luisordoniez.reddit.conexion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luisordoniez on 14/12/16.
 *
 * Clase que extiende de AsyncTask, utilizada para conusmir servicio
 * que contiene informacion para la app
 */

public class AsyncTaskJson extends android.os.AsyncTask<String, Void, JSONObject> {

    /**
     * Atributos
     */

    private String urlServer = "";
    private int serverResponseCode = 0;
    private HttpI httpI;

    /**
     * Constructor parametrizado
     *
     * @param httpI contexto de la app
     * @param urlServer  url donde se encuentra el servicio
     */

    public AsyncTaskJson(HttpI httpI, String urlServer) {
        this.httpI = httpI;
        this.urlServer = urlServer;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        JSONObject jsonObj = null;
        HttpURLConnection conn = null;

        try {
            // open a URL connection
            URL url = new URL(urlServer);

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Read the input stream into a String
            InputStream inputStream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            String forecastJsonStr = buffer.toString();
            jsonObj = new JSONObject(forecastJsonStr);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String message = conn.getResponseMessage();


            if(serverResponseCode != 200)
                jsonObj = new JSONObject();

        } catch (Exception e) {
            jsonObj = new JSONObject();
        }

        return jsonObj;
    }

    public interface HttpI {
        public void setResult(JSONObject json) throws JSONException;
    }

    /**
     * Metodo que devuelve respuesta a activyty que lo llamo
     *
     * @param result json obtenido del servicio
     */

    protected void onPostExecute(JSONObject result) {
        try {
            httpI.setResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
