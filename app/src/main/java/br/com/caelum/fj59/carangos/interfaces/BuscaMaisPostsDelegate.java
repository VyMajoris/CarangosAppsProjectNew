package br.com.caelum.fj59.carangos.interfaces;

import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by VyMajoris on 3/10/2015.
 */
public interface BuscaMaisPostsDelegate {
    void lidaComRetorno(List<BlogPost> retorno);
    void listaComErro (Exception e);

    CarangosApplication getCarangosApplication();
}
