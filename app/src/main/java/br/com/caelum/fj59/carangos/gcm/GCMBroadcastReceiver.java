package br.com.caelum.fj59.carangos.gcm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by android5192 on 12/03/15.
 */
public class GCMBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        String message = (String) intent.getExtras().getSerializable("message");

        MyLog.i("Tipo de mensagem: " + messageType + "com conteudo " + message);
        Intent irParaLeilao = new Intent (context, MainActivity.class);
        PendingIntent acaoPendente = PendingIntent.getActivity(context, 0 ,irParaLeilao, PendingIntent.FLAG_CANCEL_CURRENT);
        irParaLeilao.putExtra("idDaNotificacao",Constantes.ID_NOTIFICACAO);


        Notification notification = new Notification.Builder(context)
                .setContentTitle("UM Nnovo leilao comecou!")
                .setContentText("Leilao: " + intent.getExtras().getSerializable("message"))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(acaoPendente)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Constantes.ID_NOTIFICACAO, notification);
    }


}
