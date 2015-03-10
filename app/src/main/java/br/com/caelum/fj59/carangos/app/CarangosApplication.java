package br.com.caelum.fj59.carangos.app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VyMajoris on 3/10/2015.
 */
public class CarangosApplication extends Application {
    List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

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
