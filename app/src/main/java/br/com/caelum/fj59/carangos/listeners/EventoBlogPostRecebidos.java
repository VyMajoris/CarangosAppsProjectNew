package br.com.caelum.fj59.carangos.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.interfaces.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by android5192 on 11/03/15.
 */
public class EventoBlogPostRecebidos extends BroadcastReceiver {
    private static final String RESULTADO_POSTS = "resultadoPosts";
    private static final String SUCESSO = "sucesso";
    private static final String BLOGPOST_RECEBIDOS = "blogs post recebidos";

    private BuscaMaisPostsDelegate delegate;
    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("RECEBI O EVENTO!!" + intent.getBooleanExtra(SUCESSO, false));
        delegate.lidaComRetorno(
                (List<BlogPost>) intent.getSerializableExtra(RESULTADO_POSTS)) ;
    }


    public void desregistra(CarangosApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }
    public static EventoBlogPostRecebidos registraObservador(BuscaMaisPostsDelegate delegate){
        EventoBlogPostRecebidos receiver = new EventoBlogPostRecebidos();
        receiver.delegate = delegate;

        LocalBroadcastManager.getInstance(delegate.getCarangosApplication()).registerReceiver(receiver, new IntentFilter((BLOGPOST_RECEBIDOS)));

        return receiver;
    }

    public static void notificaPostsRecebidos(Context context, List<BlogPost> resultado, boolean sucesso){
        Intent intent = new Intent(BLOGPOST_RECEBIDOS);
        intent.putExtra(RESULTADO_POSTS, (Serializable) resultado);
        intent.putExtra(SUCESSO, sucesso );

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
