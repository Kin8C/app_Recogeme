package com.safict.recogeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elkin on 6/16/2015.
 */
public class listado extends Activity implements View.OnClickListener {

    public JSONArray jsonArray = null;
    public static ListView listRecogidas;
    HttpHandler handler = new HttpHandler("http://recogeme.safict.com/API/post_Service.php");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        Button btRegresar = (Button) findViewById(R.id.btnRegresarR);
        btRegresar.setOnClickListener(this);

        listRecogidas = (ListView) findViewById(R.id.lista_recogidas);

        try
        {
            listView();
        }
        catch(Exception e)
        {
            popUp();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegresarR:
                Intent regresar_Activity = new Intent(listado.this, main_Recogeme.class);
                startActivity(regresar_Activity);
                finish();
                break;
        }
    }

    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Has Solicitado tu Primera Recogida")
                .setTitle("No Hay Datos")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getBaseContext(), "Proceso de Recogida Cancelado", Toast.LENGTH_SHORT).show();
                                Intent regresar_Activity = new Intent(listado.this, main_Recogeme.class);
                                startActivity(regresar_Activity);
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void listView(){

        JSONArray jsonArray = null;
        Log.v("json", "entre JSON");
        try {
            String datos = handler.mostrar();
            Log.v("json", "metodo mostrar() invocado");
            Log.v("json", datos);
            jsonArray = new JSONArray(datos);
            //Log.v("json", jsonArray.getJSONObject(0).getString("ciudad_destino"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("json", "Error Json");
        }

        NameValuePair[] datosLista=new NameValuePair[jsonArray.length()];
        Log.v("json", "Cantidad Datos = " + jsonArray.length());
        for(int posicion = 0; posicion < jsonArray.length(); posicion++)
        {
            try
            {
                datosLista[posicion]=new BasicNameValuePair(jsonArray.getJSONObject(posicion).getString("ciudad_destino")+"?"+jsonArray.getJSONObject(posicion).getString("estado"), jsonArray.getJSONObject(posicion).getString("fecha"));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("json", "Error Json#2");
            }

        }


    /* Creamos los datos a presentar en la lista, elegimos un arreglo de pares.
    * Este tipo de datos (NameValuePair) permite almacenar dos valores por lo que
    * combinándolo con el arreglo podemos en cada posición del mismo almacenar dos
    * cadenas de texto.
    */
        adaptadorLista miAdaptador=new adaptadorLista(this, datosLista);

    /* Creamos un objeto de el adaptador creado por nosotros anteriormente
    *  pasándole el contexto y los datos a mostrar
    */

        ListView lstOpciones = (ListView) findViewById(R.id.lista_recogidas);
        lstOpciones.setAdapter(miAdaptador);

        /* Capturamos el ListView y le agregamos el adaptador */

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Toast.makeText(getBaseContext(),"Proceso de Recogida Cancelado", Toast.LENGTH_SHORT).show();
            Intent regresar_Activity = new Intent(listado.this, main_Recogeme.class);
            startActivity(regresar_Activity);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
