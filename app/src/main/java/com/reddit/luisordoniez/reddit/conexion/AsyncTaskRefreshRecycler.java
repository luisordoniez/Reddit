package com.reddit.luisordoniez.reddit.conexion;

import android.app.Activity;
import android.os.AsyncTask;

import com.reddit.luisordoniez.reddit.utils.GlobalClass;


/**
 * Created by luisordoniez on 16/12/16.
 * <p/>
 * Clase que extiende de AsyncTask, actualidazar la lista de temas
 */

public class AsyncTaskRefreshRecycler extends AsyncTask<Void, Void, Boolean> {

    /**
     * Atributos
     */

    static final int DURACION = 500; // 0.5 segundos de espera
    private HttpI httpI;
    private GlobalClass globalClass;

    /**
     * Constructor parametrizado
     *
     * @param httpI contexto de la app
     */

    public AsyncTaskRefreshRecycler(HttpI httpI) {
        this.httpI = httpI;
        globalClass = (GlobalClass) ((Activity) httpI).getApplicationContext();
    }

    public interface HttpI {
        public void setResult(Boolean respuesta);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        for (int i = 0; i < 3; i++) {
            if (globalClass.TestConection())
                return true;
            else {
                try {
                    Thread.sleep(DURACION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Metodo que devuelve respuesta a activyty que lo llamo
     *
     * @param respuesta contiene respuesta
     */

    @Override
    protected void onPostExecute(Boolean respuesta) {
        super.onPostExecute(respuesta);

        httpI.setResult(respuesta);
    }
}
