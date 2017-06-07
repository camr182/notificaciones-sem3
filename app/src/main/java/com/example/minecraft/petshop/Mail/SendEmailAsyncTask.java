package com.example.minecraft.petshop.Mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.minecraft.petshop.Activities.Contacto;
import com.example.minecraft.petshop.R;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 * Created by ameza on 22/03/2017.
 */

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public Mail m;
    public Contacto activity;
    private ProgressDialog progressDialog = null;
    public Context context;

    public SendEmailAsyncTask(Context context) {
        this.context=context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressdialog);
        progressDialog.setCancelable(true);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        //Toast.makeText(context,"Mensaje enviado",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                activity.displayMessage("Email sent.");
            } else {
                activity.displayMessage("Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Detalles de la cuenta erróneos");
            e.printStackTrace();
            activity.displayMessage("Autenticación fallida.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Correo fallido");
            e.printStackTrace();
            activity.displayMessage("Falló el envio del correo.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            activity.displayMessage("Unexpected error occured.");
            return false;
        }
    }
}