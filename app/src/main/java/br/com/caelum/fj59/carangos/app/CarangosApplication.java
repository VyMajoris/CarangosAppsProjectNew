package br.com.caelum.fj59.carangos.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.interfaces.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.tasks.RegistraDeviceTask;

/**
 * Created by VyMajoris on 3/10/2015.
 */
public class CarangosApplication extends Application {
    List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();
    private static final String REGISTRED = "registeredInGCM";
    private static final String REGISTRATION_ID = "registrationId";
    private SharedPreferences preferences;




    List<BlogPost> posts = new ArrayList<BlogPost>();


    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        preferences = getSharedPreferences("configs",
                Activity.MODE_PRIVATE);
        initializeGCM();
    }

    private void initializeGCM() {
        if(!usuarioRegistrado()){
            new RegistraDeviceTask(this).execute();
        }else{
            MyLog.i("device j[a cadastrado");
            preferences.getString(REGISTRATION_ID, null);
        }
    }

    private boolean usuarioRegistrado() {

        return preferences.getBoolean(REGISTRED, false);
    }


    public void lidaComRespostadoRegistroNovoServidor(String registro){
        if(registro != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTRED,true);
            editor.putString(REGISTRATION_ID, registro);
            editor.commit();




        }
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

    public void lidaComRespostaDoRegistronoServidor(String result) {

    }
}
