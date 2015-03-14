package br.com.caelum.fj59.carangos.tasks;

import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.Collections;
import java.util.List;

import br.com.caelum.fj59.carangos.converter.LanceConverter;
import br.com.caelum.fj59.carangos.modelo.Lance;

/**
 * Created by android5192 on 13/03/15.
 */
public class CustomHandler extends Handler {
    private ArrayAdapter<Lance> adapter;
    private List<Lance> lancesAteOMomento;

    public CustomHandler(ArrayAdapter<Lance> adapter, List<Lance> lancesAteOMomento){
        System.out.println("custom Handler Constructor");
        this.adapter = adapter;
        this.lancesAteOMomento = lancesAteOMomento;

    }

    @Override
    public void handleMessage(Message msg){
        System.out.println("handleMessage");
        String json = (String) msg.obj;
        List<Lance> novosLances = new LanceConverter().toLances(json);
        lancesAteOMomento.addAll(novosLances);
        adapter.notifyDataSetChanged();
        Collections.sort(lancesAteOMomento);

    }

}
