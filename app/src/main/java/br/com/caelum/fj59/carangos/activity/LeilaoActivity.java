package br.com.caelum.fj59.carangos.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.tasks.BuscaLeilaoTask;
import br.com.caelum.fj59.carangos.tasks.CustomHandler;

public class LeilaoActivity extends Activity {
    private List<Lance> lanceAteMomento = new ArrayList<Lance>();
    private Calendar horarioUltimaBusca = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);


        final ListView lancesLists = (ListView) findViewById(R.id.lances_list);
        final ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(LeilaoActivity.this,android.R.layout.simple_list_item_1);
        lancesLists.setAdapter(adapter);

        CustomHandler handler = new CustomHandler(adapter, lanceAteMomento);

        new BuscaLeilaoTask(handler, horarioUltimaBusca).executa();
    }



}
