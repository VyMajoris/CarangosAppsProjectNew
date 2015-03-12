package br.com.caelum.fj59.carangos.tasks;

import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;

import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.BlogPostConverter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.interfaces.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.listeners.EventoBlogPostRecebidos;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPostsTask extends AsyncTask<Pagina, Void, List<BlogPost>> {

    private CarangosApplication application;
    private Exception erro;

    public BuscaMaisPostsTask(CarangosApplication application) {

        this.application = application;
        application.registra(this);



    }

    @Override
    protected List<BlogPost> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            List<BlogPost> postsRecebidos = new BlogPostConverter().toPosts(jsonDeResposta);




            return postsRecebidos;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BlogPost> retorno) {
        MyLog.i("RETORNO OBTIDO!" + retorno);

        if (retorno!=null) {
            EventoBlogPostRecebidos.notificaPostsRecebidos(this.application,retorno,true);
        } else {
            EventoBlogPostRecebidos.notificaPostsRecebidos(this.application,retorno,false);
        }
        this.application.deregistra(this);
    }
}
