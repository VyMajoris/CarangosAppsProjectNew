package br.com.caelum.fj59.carangos.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by erich on 7/16/13.
 */
public class BlogPostAdapter extends BaseAdapter {
    private Context context;
    private final List<BlogPost> posts;

    public BlogPostAdapter(Context mContext, List<BlogPost> posts) {
        this.context = mContext;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(position % 2 == 0) {
            if (convertView != null && convertView.findViewById(R.id.root_linha_par) !=null){
                holder = (ViewHolder) convertView.getTag();
                MyLog.i("Aproveitou cell par");
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.post_linha_par, viewGroup,false);
                     holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                    MyLog.i("Nao aprovietou PAR");
            }
        }else{
            if (convertView != null && convertView.findViewById(R.id.root_linha_impar) != null){
                holder = (ViewHolder) convertView.getTag();
                MyLog.i("aproveitou cell impar");
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.post_linha_impar, viewGroup,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                MyLog.i("aproveitou cell impar");
            }
        }



       /*
       int layout = position % 2 == 0?
                        R.layout.post_linha_par : R.layout.post_linha_impar;
        holder = new ViewHolder(convertView);

        if (convertView == null){

            convertView = LayoutInflater.from(context).inflate(layout, viewGroup, false);
            MyLog.i("Criou uma nova linha!!");

        }else{
            MyLog.i("Aproveitou uma linha");

        }
        */

        BlogPost blogPost = (BlogPost) getItem(position);

        holder.mensagem.setText(blogPost.getMensagem());
        holder.mensagem.setText(blogPost.getAutor().getNome());

        //foto.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_car));
        UrlImageViewHelper.setUrlDrawable(holder.foto, blogPost.getFoto(), this.context.getResources().getDrawable(R.drawable.ic_car));

        int idImagem = 0;
        switch (blogPost.getEstadoDeHumor()) {
            case ANIMADO: idImagem = R.drawable.ic_muito_feliz; break;
            case INDIFERENTE: idImagem = R.drawable.ic_feliz; break;
            case TRISTE: idImagem = R.drawable.ic_indiferente; break;
        }

        holder.emoticon.setImageDrawable(this.context.getResources().getDrawable(idImagem));

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    class ViewHolder {
        ImageView foto;
        ImageView emoticon;
        TextView mensagem;
        TextView nomeAutor;

        ViewHolder(View view) {
            this.foto = (ImageView) view.findViewById(R.id.foto);
            this.emoticon = (ImageView) view.findViewById(R.id.emoticon);
            this.mensagem = (TextView) view.findViewById(R.id.mensagem);
            this.nomeAutor = (TextView) view.findViewById(R.id.nome_autor);
        }
    }
}
