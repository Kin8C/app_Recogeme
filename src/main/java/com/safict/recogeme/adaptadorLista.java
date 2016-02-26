package com.safict.recogeme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;

import java.util.StringTokenizer;

/**
 * Created by Elkin on 6/30/2015.
 */
public class adaptadorLista extends ArrayAdapter{
    // Hacemos que nuestra clase herede las características de un ArrayAdapter

    Activity context;
    NameValuePair[] datos;
    /* Creamos las variables necesarias para capturar el contexto
    *  y los datos que se publicarán en la lista
    */

    public adaptadorLista(Activity context,NameValuePair[] datos) {
        super(context,R.layout.listview_base,datos);
        this.context=context;
        this.datos=datos;
        // TODO Auto-generated constructor stub
    }

    /* Constructor de la clase, donde pasamos por parámetro los datos
     * a mostrar en la lista y el contexto
    */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View item=inflater.inflate(R.layout.listview_base, null);

        StringTokenizer st = new StringTokenizer(datos[position].getName(), "?");
        String[] valores = new String[2];
        int i = 0;
        while (st.hasMoreTokens()){
            valores[i] = st.nextToken();
            //Log.v("myTag",valores[i]);
            i++;
        }
        TextView destinatario=(TextView) item.findViewById(R.id.textCiudad);
        destinatario.setText(valores[0]);
        //destinatario.setText(datos[position].getName().toString());

        TextView direccion=(TextView) item.findViewById(R.id.textTipoEnvio);
        direccion.setText(valores[1]);
        //direccion.setText(datos[position].getValue().toString());

        TextView ciudad=(TextView) item.findViewById(R.id.textFecha);
        ciudad.setText(datos[position].getValue().toString());

        return item;
    }

}
