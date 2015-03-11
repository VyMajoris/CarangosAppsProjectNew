package br.com.caelum.fj59.carangos.app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by VyMajoris on 3/10/2015.
 */
public class CarangosApplication extends Application {
    List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();



    List<BlogPost> posts = new ArrayList<BlogPost>();


    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        for (AsyncTask task : this.tasks){
            task.cancel(true);
        }
    }


    public void registra(AsyncTask<?,?,?> task){
        tasks.add(task);
    }
    public void deregistra(AsyncTask<?,?,?> task){
        tasks.remove(task);
    }
}
