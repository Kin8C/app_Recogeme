package com.safict.recogeme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Elkin on 7/8/2015.
 */
public class Servicio_Notificacion extends Service {

    private Timer timer;
    NotificationManager notification_Manager;
    private static final int ID_Notif_Pendiente = 2;
    public static boolean estado_Recogida = false;

    public JSONArray jsonArray;
    HttpHandler handler = new HttpHandler("http://recogeme.safict.com/API/post_Service.php");

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("Service", "Servicio creado...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Servicio iniciado...");
        JSONArray jsonArray = null;

        // Inicio Timer
        timer = new Timer();
        SubTimer subTimer = new SubTimer();
        timer.scheduleAtFixedRate(subTimer, 5000, 10000);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("Service", "Servicio destruido...");
    }

    // Notificacion
    public void enviar_Notificacion_pendiente() {
        notification_Manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notificacion_Pendiente_NT = new Notification(R.drawable.recogeme_main2, "Recogida Asignada", System.currentTimeMillis());
        PendingIntent intencionPendiente = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), listado.class), 0);

        notificacion_Pendiente_NT.setLatestEventInfo(getApplicationContext(), "Recogida Asignada", "Tu Recogidad se ha Transmitido", intencionPendiente);

        notification_Manager.notify(ID_Notif_Pendiente, notificacion_Pendiente_NT);
    }

    class SubTimer extends TimerTask {

        HttpHandler handler = new HttpHandler("http://recogeme.safict.com/API/post_Service.php");


        @Override
        public void run() {
            Log.v("Service", String.valueOf(proceso()));
            if (estado_Recogida) {
                timer.cancel();
            }
        }

        public boolean proceso() {

            Log.v("timer", "Me Estoy Ejecutando en Segundo Plano");
            try {
                String datos = handler.verifica();
                jsonArray = new JSONArray(datos);
                Log.v("timer", "Me Estoy Ejecutando en Segundo Plano");
                if (jsonArray.getJSONObject(0).getString("estado").equals("Transmitida")) {
                    Log.v("timer", "el Estado del Envio Cambio");
                    enviar_Notificacion_pendiente();
                    estado_Recogida = true;

                } else {
                    Log.v("timer", jsonArray.getJSONObject(0).getString("estado"));
                    estado_Recogida = false;
                }

            } catch (Exception e) {
                estado_Recogida = false;
            }
            boolean asignado = estado_Recogida;
            return asignado;
        }
    }
}


