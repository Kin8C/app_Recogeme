package com.safict.recogeme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Recogeme extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recogeme);
        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(),"Comprueba tu conexión a Internet.", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            //Toast.makeText(getBaseContext(),"Si hay internet", Toast.LENGTH_SHORT).show();
            RelativeLayout fondo = (RelativeLayout) findViewById(R.id.layoutFondo);
            //fondo.setBackgroundColor(fondo.getContext().getResources().getColor(R.color.Azul_Fondo));
            int miliSegundos = 2000;
            temporizador(miliSegundos);
        }

    }

    public void temporizador(int miliSegundos)
    {
        new CountDownTimer(miliSegundos, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent nuevo_Activiy = new Intent(Recogeme.this, main_Recogeme.class);
                startActivity(nuevo_Activiy);
                finish();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

}
