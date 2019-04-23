package com.example.zap;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zap.helper.Permission;
import com.example.zap.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {


    protected SimpleMaskFormatter simpleMaskNome,simpleMaskDDD,simpleMaskDDI,simpleMaskPhoneNumber;
    protected MaskTextWatcher mtwPhoneNumber,mtwDDD,mtwDDI;
    protected String[] permissoesNecessarias = new String[] {
        Manifest.permission.SEND_SMS

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permission.validaPermissoes(1,this,permissoesNecessarias);

    }
    public void abrirCadastroUsuario( View view){
        startActivity( new Intent(LoginActivity.this,CadastroUsuarioActivity.class));
    }

}
