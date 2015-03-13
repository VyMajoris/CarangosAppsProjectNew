package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuaro;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5192 on 12/03/15.
 */
public class RegistraDeviceTask extends AsyncTask<Void, Void, String> {

    private CarangosApplication app;

    public RegistraDeviceTask(CarangosApplication app){
        this.app = app;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationID = null;
        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            registrationID = gcm.register(Constantes.GCM_SERVER_ID);
            MyLog.i("Device registrado com id: " +registrationID);
            String email = InformacoesDoUsuaro.getEmail(this.app);
            String url = "device/register/"+email+"/"+registrationID;
            WebClient client = new WebClient(url);
            client.post();

        }catch (Exception e){
            MyLog.e("Problema no registro " + e.getMessage());
        }


        return registrationID;
    }
    @Override
    protected void onPostExecute(String result){
        app.lidaComRespostaDoRegistronoServidor(result);
    }


}
