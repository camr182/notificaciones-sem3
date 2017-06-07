package com.example.minecraft.petshop;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by minecraft on 21/05/2017.
 */

public class NotificationIDTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "FIREBASE_TOKEN";
    @Override
    public void onTokenRefresh() {

        Log.d(TAG,"Solicitando token");
        String token = FirebaseInstanceId.getInstance().getToken();
        enviarTokenRegistro(token);
        //super.onTokenRefresh();
    }

    private void  enviarTokenRegistro(String token){
        Log.d(TAG,token);

    }


}
