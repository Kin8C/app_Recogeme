package com.safict.recogeme;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Entity;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.StrictMode;

import java.io.IOException;

/**
 * Created by Elkin on 6/22/2015.
 */
public class HttpHandler extends AsyncTask<Void, Void, String> {

    private final String URL;
    public HttpClient httpclient;
    public HttpPost httppost;
    public boolean estado;

    public HttpHandler(String URL)
    {
        this.URL = URL;
    }


    public String mostrar(){
        StrictMode.ThreadPolicy politicas = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politicas);
        String resultado = "Error";
        try{
            httpclient = new DefaultHttpClient();
            Log.v("http", "httpCliente creado");
            httppost = new HttpPost(URL);
            Log.v("http", "httppost creado");

            // Añadir Parametros
            List<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair("metodo","mostrar"));
            parametros.add(new BasicNameValuePair("correo", main_Recogeme.correo));
            Log.v("http", "parametro correo: " + main_Recogeme.correo);
            Log.v("http", "parametros agregados");
            httppost.setEntity(new UrlEncodedFormEntity(parametros));
            Log.v("http", "parametros enviados");


            HttpResponse response = httpclient.execute(httppost);
            Log.v("http", "HttpResponse creado");
            HttpEntity entity = response.getEntity();
            Log.v("http", "HttpEntity creado");

            String responseText = EntityUtils.toString(entity);
            Log.v("http", "respuesta del cliente recibida");

            resultado = responseText;
            Log.v("http", "valores asignados a resultado");
            Log.v("http", "resultado: "+resultado);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.v("http", "Error Client Protocol");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("http", "Error IO Excepcion");
        }
        return resultado;
    }

    public String agregar(String correo_cliente, String ciudad_origen, String nombre_origen, int telefono_origen, String direccion_origen, String ciudad_destino, String tipo_envio, int peso_aprox){
        String resultado = "Error";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);

            Log.v("myTag", "antes de Añadir Parametros");
            // Añadir Parametros
            List<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair("metodo", "agregar"));
            parametros.add(new BasicNameValuePair("correo_cliente", correo_cliente));
            parametros.add(new BasicNameValuePair("ciudad_origen", ciudad_origen));
            parametros.add(new BasicNameValuePair("nombre_origen", nombre_origen));
            parametros.add(new BasicNameValuePair("telefono_origen", Integer.toString(telefono_origen)));
            parametros.add(new BasicNameValuePair("direccion_origen", direccion_origen));
            parametros.add(new BasicNameValuePair("ciudad_destino", ciudad_destino));
            parametros.add(new BasicNameValuePair("tipo_envio", tipo_envio));
            parametros.add(new BasicNameValuePair("peso_aprox", Integer.toString(peso_aprox)));
            Log.v("myTag", "parametros Almacenado");
            httppost.setEntity(new UrlEncodedFormEntity(parametros));
            Log.v("myTag", "parametros Enviados");



            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            //String responseText = EntityUtils.toString(entity);
            String responseText = "agregado Correctamente";

            resultado = responseText;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.v("myTag", "exception PRotocolo");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("myTag", "IO Exception");
        }
        return resultado;
    }

    @Override
    protected String doInBackground(Void... params) {
        return verifica();
    }

    public String verifica(){
        //verificacion
        StrictMode.ThreadPolicy politicas = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politicas);
        String resultado = "Error";
        try{
            httpclient = new DefaultHttpClient();
            Log.v("http", "httpCliente creado");
            httppost = new HttpPost(URL);
            Log.v("http", "httppost creado");

            // Añadir Parametros
            List<NameValuePair> parametros = new ArrayList<NameValuePair>();
            parametros.add(new BasicNameValuePair("metodo","verificacion"));
            parametros.add(new BasicNameValuePair("correo", main_Recogeme.correo));
            httppost.setEntity(new UrlEncodedFormEntity(parametros));
            Log.v("http", "parametros enviados");

            HttpResponse response = httpclient.execute(httppost);
            Log.v("http", "HttpResponse creado");
            HttpEntity entity = response.getEntity();
            Log.v("http", "HttpEntity creado");

            String responseText = EntityUtils.toString(entity);
            Log.v("http", "respuesta del cliente recibida");

            resultado = responseText;
            Log.v("http", "valores asignados a resultado");
            Log.v("http", "resultado: "+resultado);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.v("http", "Error Client Protocol");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("http", "Error IO Excepcion");
        }
        return resultado;
    }
}
