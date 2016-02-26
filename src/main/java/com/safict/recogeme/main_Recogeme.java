package com.safict.recogeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.safict.recogeme.R.drawable.push_btn_recogeme_main;

/**
 * Created by Elkin on 6/13/2015.
 */
public class main_Recogeme extends Activity implements View.OnClickListener, View.OnTouchListener {

    public Button btn_Recogeme;
    public Button btn_Recogidas;
    public static String correo = null;

    public static NotificationManager notificacion_Pendiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificacion_Pendiente = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        StrictMode.ThreadPolicy politicas = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politicas);
        Log.v("json", "Inicio App");
        Log.v("Ficheros", "Antes del IF");
        if(readStart().equals("NO"))
        {
            popupMenu();
            //Log.v("Ficheros", correo);
        }else{
            correo = readStart();
        }
        Log.v("Ficheros", "Sali del IF");

        btn_Recogeme = (Button) findViewById(R.id.btnRecogeme);
        btn_Recogidas = (Button)findViewById(R.id.btnRecogidas);

        //btn_Recogeme.setOnClickListener(this);
        //btn_Recogidas.setOnClickListener(this);

        btn_Recogeme.setOnTouchListener(this);
        btn_Recogidas.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private String readStart(){
        String lectura = "NO";
        try{
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("System_ColCali_14062004.kin")));
            lectura = fin.readLine();
            correo = lectura;
            Log.v("Ficheros", "Lectura Correcta"+correo);

            fin.close();

        }
        catch (Exception ex)
        {
            Log.v("Ficheros", "Error al leer fichero desde memoria interna");
        }
        return lectura;
    }


    private void popupMenu()
    {
        Log.v("Ficheros", "popMenu()");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Datos de Inicio");
        alert.setMessage("Agrega tu correo Para Continuar:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        Toast.makeText(getBaseContext(),"Esta información solo se te solicitará una vez", Toast.LENGTH_LONG).show();
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                //Toast.makeText(getBaseContext(),input.getText(), Toast.LENGTH_LONG).show();
                if (input.getText().length() > 7) {
                    try {
                        OutputStreamWriter dataText = new OutputStreamWriter(openFileOutput("System_ColCali_14062004.kin", Context.MODE_PRIVATE));
                        dataText.write(String.valueOf(input.getText()).toUpperCase());
                        correo = readStart();

                        Log.v("Ficheros", "fichero creado y escrito exitosamente en la memoria interna"+correo);
                        dataText.close();
                    } catch (Exception ex) {
                        Log.v("Ficheros", "Error al escribir fichero a memoria interna");
                    }
                } else {
                    popupMenu();
                    Log.v("Ficheros", String.valueOf(input.getText().length()) + "Vacio");
                }
            }
        });

//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // Canceled.
//
//            }
//        });

        alert.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            super.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.btnRecogeme:
                //btn_Recogeme.setBackground(btn_Recogeme.getContext(), getResources().getDrawable(push_btn_recogeme_main));
                //Toast.makeText(getBaseContext(), "Has Pulsado Recogeme", Toast.LENGTH_SHORT).show();
                Intent activity_Recogeme = new Intent(main_Recogeme.this, formulario.class);
                startActivity(activity_Recogeme);
                finish();
                break;
            case R.id.btnRecogidas:
                Log.v("json", "Click Recogidas");
                Toast.makeText(getBaseContext(), "Has Pulsado Mis Recogidas", Toast.LENGTH_SHORT).show();
                Intent activity_Recogidas = new Intent(main_Recogeme.this, listado.class);
                startActivity(activity_Recogidas);
                finish();
                break;
        }
        return false;
    }


    public void enviar_Notificacion_pendiente(String mensajero, String estado){
        Log.v("timer", "Dentro del Metodo");
        Log.v("timer", "notificacion_pendiente Creada");
        Notification notificacion_Pendiente_NT = new Notification(R.drawable.recogeme_main2,"Información Transmitida", System.currentTimeMillis());
        Log.v("timer", "notificacion_NT_ Creada");
        PendingIntent intencionPendiente=PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),listado.class),0);
        Log.v("timer", "pendiente Intent creado");
        notificacion_Pendiente_NT.setLatestEventInfo(getApplicationContext(), "Información Procesada", "M " + mensajero + " E " + estado, intencionPendiente);
        Log.v("timer", "Informacion Agregada");
        notificacion_Pendiente.notify(2, notificacion_Pendiente_NT);
        Log.v("timer", "Notificacion Mostrada");
    }




}
