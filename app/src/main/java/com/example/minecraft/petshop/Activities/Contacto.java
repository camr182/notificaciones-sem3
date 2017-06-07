package com.example.minecraft.petshop.Activities;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minecraft.petshop.Mail.Mail;
import com.example.minecraft.petshop.Mail.SendEmailAsyncTask;
import com.example.minecraft.petshop.R;

import java.util.regex.Pattern;

public class Contacto extends AppCompatActivity {

    TextInputEditText mensaje;
    TextInputEditText asunto;
    TextInputEditText correo;
    Button EnviarComentario;
    TextView cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        mensaje = (TextInputEditText) findViewById(R.id.inMensaje);
        asunto = (TextInputEditText) findViewById(R.id.inAsunto);
        correo = (TextInputEditText) findViewById(R.id.inCorreo);
        EnviarComentario = (Button) findViewById(R.id.btnEnviarCorreo);

        EnviarComentario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final TextInputLayout tilMensaje = (TextInputLayout) findViewById(R.id.tilMensaje);
                final TextInputLayout tilAsunto = (TextInputLayout) findViewById(R.id.tilAsunto);
                final TextInputLayout tilCorreo = (TextInputLayout) findViewById(R.id.tilCorreo);

                boolean mensajeValidado =  esMensajeValido(mensaje.getText().toString());
                boolean asuntoValidado = esAsuntoValido(asunto.getText().toString());
                boolean correoValidado = esCorreoValido(correo.getText().toString());

                //listener campo mensaje
                mensaje.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {
                    tilMensaje.setError(null);
                   }

                   @Override
                  public void afterTextChanged(Editable s) {
                  }
                    });

                    //listener campo asunto
                  asunto.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            tilAsunto.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    //listener campo correo
                   correo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            tilCorreo.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });


                    if (mensajeValidado==true || asuntoValidado==true || correoValidado==true){
                    }else {

                        sendMessage();
                    }

            }
        });

    }

    //Validar para que solo permita ingresar letras
    private boolean esAsuntoValido(String asunto) {

        TextInputLayout tilAsunto = (TextInputLayout) findViewById(R.id.tilAsunto);
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        boolean Error = false;

        if (asunto.isEmpty()) {
            tilAsunto.setError(getString(R.string.mensaje_error_asunto));
            Error = true;
        }
        else if (!patron.matcher(asunto).matches()) {
            tilAsunto.setError("No se permite ingresar números");
            Error = true;
        }



        if (Error == true) {
            tilAsunto.setErrorEnabled(true);
        } else {
            tilAsunto.setErrorEnabled(false);
        }

        return Error;

    }

    private boolean esMensajeValido(String mensaje) {

        TextInputLayout tilMensaje = (TextInputLayout) findViewById(R.id.tilMensaje);
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        boolean Error = false;

        if (mensaje.isEmpty()) {
            tilMensaje.setError(getString(R.string.mensaje_error_mensaje));
            Error = true;
        }
        else if (!patron.matcher(mensaje).matches()) {
            tilMensaje.setError("El mensaje no admite números");
            Error = true;
        }
        else if (mensaje.length() > 100) {
            tilMensaje.setError("El mensaje no debe tener mas de 100 caracteres");
            Error = true;
        }

        if (Error == true) {
            tilMensaje.setErrorEnabled(true);
        } else {
            tilMensaje.setErrorEnabled(false);
        }

        return Error;

    }

    private boolean esCorreoValido(String correo) {

        TextInputLayout tilCorreo = (TextInputLayout) findViewById(R.id.tilCorreo);
        boolean Error = false;



        if (correo.isEmpty()) {
            tilCorreo.setError(getString(R.string.mensaje_error_correo));
            Error = true;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError("Formato de correo electrónico inválido");
            Error = true;
        }
        else if (correo.length() > 30) {
            tilCorreo.setError("El correo debe tener menos de 30 caracteres");
            Error = true;
        }

        if (Error == true) {
            tilCorreo.setErrorEnabled(true);
        } else {
            tilCorreo.setErrorEnabled(false);
        }

        return Error;
    }


    public void displayMessage(String message) {
        Snackbar.make(findViewById(R.id.activity_contacto), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    //ENVIAR CORREO
    public void sendMessage() {
        TextInputEditText mensaje = (TextInputEditText) findViewById(R.id.inMensaje);
        TextInputEditText asunto = (TextInputEditText) findViewById(R.id.inAsunto);

        String[] recipients = {"larileando@gmail.com"};//para
        SendEmailAsyncTask email = new SendEmailAsyncTask(this);
        email.activity = this;
        email.m = new Mail("camr182@gmail.com", "2008026337");//cuenta y contraseña que manda correo
        email.m.set_from("adminastrador@gmail.com");
        email.m.setBody((mensaje).getText().toString());
        email.m.set_to(recipients);
        email.m.set_subject((asunto).getText().toString());
        email.execute();
    }


}
