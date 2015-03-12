package br.com.caelum.fj59.carangos.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.fragments.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.interfaces.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.listeners.EventoBlogPostRecebidos;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsTask;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {
    private ListView postsList;

    private BlogPostAdapter adapter;
    private EstadoMainActivity estado;

    private EventoBlogPostRecebidos evento;
    private PullToRefreshAttacher attacher;
    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.estado = EstadoMainActivity.INICIO;
        this.evento = EventoBlogPostRecebidos.registraObservador(this);

        this.attacher = PullToRefreshAttacher.get(this);



    }
    public PullToRefreshAttacher getAttacher(){
        return  attacher;
    }

    @Override
    protected void onResume(){
        super.onResume();
        MyLog.i("Executando Estado!" + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        MyLog.i("Salvando Estado!");

        outState.putSerializable(ESTADO_ATUAL, this.estado);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("RESSTAURANDO ESTADO!");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }



//    public void atualizaListaCom(List<BlogPost> posts) {
//
//        this.posts.clear();
//        this.posts.addAll(posts);
//        this.adapter.notifyDataSetChanged();
//    }

    public  void buscaPrimeirosPosts(){
        new BuscaMaisPostsTask(getCarangosApplication()).execute();

    }




    @Override
    public void lidaComRetorno(List<BlogPost> resultado) {
        CarangosApplication application = (CarangosApplication) getApplication();
        application.getPosts().clear();
        application.getPosts().addAll(resultado);
        this.estado = EstadoMainActivity.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);
        this.attacher.setRefreshComplete();
    }

    @Override
    public void listaComErro(Exception e) {
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_LONG).show();
        Log.e("+++ListaComErro +++",e.toString());
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }
}
