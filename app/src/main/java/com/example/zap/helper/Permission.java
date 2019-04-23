package com.example.zap.helper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    public static boolean validaPermissoes( int resquestCode,Activity activity,String[] permission){
        if(Build.VERSION.SDK_INT>=23){
            /*percorrer as permissões listadas e verificar se já foram autorizadas

             */
            List<String> listPermissao = new ArrayList<String>();

            for (String permissao:permission){
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity,permissao)== PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao)
                    listPermissao.add(permissao);
            }
            //Caso a lista esteja vazia,ão é necessário pedir permissão

            if(listPermissao.isEmpty())
                return true;

            //Solicita novas permissões
            String[] novasPermissoes = new String[listPermissao.size()];
            listPermissao.toArray(novasPermissoes);
            ActivityCompat.requestPermissions(activity,novasPermissoes,resquestCode);


        }

        return true;
    }
}
