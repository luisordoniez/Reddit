package com.reddit.luisordoniez.reddit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.reddit.luisordoniez.reddit.R;
import com.reddit.luisordoniez.reddit.adapters.RecyclerViewAdapter;
import com.reddit.luisordoniez.reddit.conexion.AsyncTaskDownload;
import com.reddit.luisordoniez.reddit.conexion.AsyncTaskJson;
import com.reddit.luisordoniez.reddit.conexion.AsyncTaskRefreshRecycler;
import com.reddit.luisordoniez.reddit.models.Data;
import com.reddit.luisordoniez.reddit.models.Respuesta;
import com.reddit.luisordoniez.reddit.utils.Constants;
import com.reddit.luisordoniez.reddit.utils.GlobalClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AsyncTaskJson.HttpI, AsyncTaskRefreshRecycler.HttpI {

    /**
     * Atributos
     */

    private AsyncTaskJson asyncTaskJson;
    private AsyncTaskDownload asyncTaskDownload;

    private GlobalClass globalClass = new GlobalClass();
    private ProgressDialog circuloProgres;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;
    private TextView Info_conexion;
    private Respuesta temas;
    private boolean online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalClass = (GlobalClass) this.getApplicationContext();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        Info_conexion = (TextView) findViewById(R.id.info_conexion);

        if (globalClass.TestConection())
            callServiceJson();
        else
            callBachupJson();

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Refresh();
                    }
                }
        );
    }


    /**
     * método invocado para refrescar la informacion
     */

    private void Refresh() {
        AsyncTaskRefreshRecycler refresh = new AsyncTaskRefreshRecycler(this);
        refresh.execute();
    }

    /**
     * método encargado de obtener informacion de Bachup 'OFFLINE'.
     */

    private void callBachupJson() {
        JSONObject Aux = new JSONObject();
        Aux = globalClass.readBachup();

        if (!Aux.isNull("data")) {
            Info_conexion.setVisibility(View.VISIBLE);
            Info_conexion.setText(Constants.SIN_CONEXION + globalClass.getFechaBachup());
            try {
                construir_vista(Aux, false);
                online = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Info_conexion.setVisibility(View.GONE);

            // colocar info general de no hay datos para mostrar
        }
    }


    /**
     * método encargado de obtener informacion desde Webservice'ONLINE'.
     */

    private void callServiceJson() {
        Info_conexion.setVisibility(View.GONE);
        circuloProgres = new ProgressDialog(this);
        circuloProgres.show();
        circuloProgres.setCancelable(false);
        circuloProgres.setContentView(R.layout.progress_dialog);

        asyncTaskJson = new AsyncTaskJson(this, Constants.URL_SERVER);
        asyncTaskJson.execute();
    }


    /**
     * este método hace un llamado a una tarea asincrona
     * la cual descarga en segundo plano un recurso de la web.
     *
     * @param banner_img  contiene la url donde se encuentra el recurso a descargar
     * @param id    identificador unico, se utiliza para path donde se guardara el recurso
     */

    private String saveImagen(String banner_img, String id) {
        String fileName = banner_img.substring(banner_img.lastIndexOf('/') + 1);

        if (globalClass.TestConection()) {
            asyncTaskDownload = new AsyncTaskDownload(this, banner_img, getFilesDir().getPath() + File.separator + id, fileName);
            asyncTaskDownload.execute();
        }

        return getFilesDir().getPath() + File.separator + id + File.separator + fileName;
    }


    /**
     * método encargado de pasar informacion para la su visualizacion
     *
     * @param json  informacion que se va a mostrar
     * @param online indicador si es OFFLINE o ONLINE
     */

    private void construir_vista(JSONObject json, boolean online) throws JSONException {

        Gson gson = new Gson();

        temas = new Respuesta();
        temas = gson.fromJson(json.get("data").toString(), Respuesta.class);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(temas.getChildren(), online);
        rv.setAdapter(adapter);
    }


    /**
     * método invocado por la instacia de la clase 'AsyncTaskJson'
     * como resultado a la tarea asíncrona.
     *
     * Este metodo organiza la informacion obtenida
     *
     * @param json contiene el resultado obtenido al consumir el servicio
     */

    @Override
    public void setResult(JSONObject json) throws JSONException {

        if (json.length() != 0) {
            construir_vista(json, true);
            online = true;

            JSONArray temp = json.getJSONObject("data").getJSONArray("children");
            JSONObject AUX = new JSONObject();
            String id;

            for (int i = 0; i < temp.length(); i++) {
                AUX = temp.getJSONObject(i).getJSONObject("data");
                id = AUX.getString("id");

                if (!AUX.isNull("banner_img"))
                    if (!AUX.get("banner_img").equals(""))
                        temp.getJSONObject(i).getJSONObject("data").put("banner_img", saveImagen(AUX.get("banner_img").toString(), id));

                if (!AUX.isNull("header_img"))
                    if (!AUX.get("header_img").equals(""))
                        temp.getJSONObject(i).getJSONObject("data").put("header_img", saveImagen(AUX.get("header_img").toString(), id));

                if (!AUX.isNull("icon_img"))
                    if (!AUX.get("icon_img").equals(""))
                        temp.getJSONObject(i).getJSONObject("data").put("icon_img", saveImagen(AUX.get("icon_img").toString(), id));
            }

            json.getJSONObject("data").put("children", temp);

            globalClass.saveBachup(json);
        } else
            callBachupJson();

        circuloProgres.cancel();

    }


    /**
     * método invocado por la instacia de la clase 'AsyncRefreshRecycler'
     * como resultado a la tarea asíncrona.
     *
     * Este metodo, segun el parametro decine como refrescar el Recycler
     * ServiceJson o BachupJson
     *
     * @param respuesta contiene el resultado
     */

    @Override
    public void setResult(Boolean respuesta) {
        refreshLayout.setRefreshing(false);
        if (respuesta)
            callServiceJson();
        else
            callBachupJson();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.salir:
                finish();
                break;

            case R.id.acercaDe:
                    Intent myIntent = new Intent();
                    myIntent.setClass(this, AcercaDe.class);
                    this.startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
