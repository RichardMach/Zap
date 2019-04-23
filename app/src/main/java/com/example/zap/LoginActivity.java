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

    private EditText editTxttelefone;
    private EditText editTxtNome;
    private EditText editTxtDdd;
    private EditText editTxtDdi;
    protected Button btnCadastrar;
    protected SimpleMaskFormatter simpleMaskNome,simpleMaskDDD,simpleMaskDDI,simpleMaskPhoneNumber;
    protected MaskTextWatcher mtwPhoneNumber,mtwDDD,mtwDDI;
    protected String[] permissoesNecessarias = new String[] {
        Manifest.permission.SEND_SMS,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permission.validaPermissoes(1,this,permissoesNecessarias);

        editTxttelefone = findViewById(R.id.editTxtPhoneNumber);
        editTxtNome     = findViewById(R.id.editTxtNomeID);
        editTxtDdd      = findViewById(R.id.editTxtDddId);
        editTxtDdi      = findViewById(R.id.editTxtDdiId);

        btnCadastrar    = findViewById(R.id.btnCadastrarId);

        //Mascara para telefone
        simpleMaskPhoneNumber = new SimpleMaskFormatter("NNNNN - NNNN");
        simpleMaskDDD         = new SimpleMaskFormatter("NN");
        simpleMaskDDI         = new SimpleMaskFormatter("+NN");

        mtwPhoneNumber = new MaskTextWatcher(editTxttelefone, simpleMaskPhoneNumber);
        mtwDDD         = new MaskTextWatcher(editTxtDdd,simpleMaskDDD);
        mtwDDI         = new MaskTextWatcher(editTxtDdi,simpleMaskDDI);

        editTxttelefone.addTextChangedListener(mtwPhoneNumber);
        editTxtDdd.addTextChangedListener(mtwDDD);
        editTxtDdi.addTextChangedListener(mtwDDI);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome             =  editTxtNome.getText().toString();
                String telefoneCompleto = editTxtDdi.getText().toString()
                        + editTxtDdd.getText().toString()
                        + editTxttelefone.getText().toString();

                //Retirando a formatação o telefone completo
                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace(" - ","");
                Log.i("telefone",":"+telefoneSemFormatacao);

                //gerando o token
                Random random = new Random();
                int numerorandomico = random.nextInt(9999-1000)+1000;
                String token = String.valueOf(numerorandomico);
                String mensagemEnvio = "ZAPZAP código de confirmação "+ token;

                //Utilizando o arquivo preferencias| salvando dados para validação
                Preferencias preferencias = new Preferencias(getApplicationContext());
                preferencias.salvarUsuarioPreferences(nome,telefoneSemFormatacao,token);


                Boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao,mensagemEnvio);

                if(enviadoSMS){
                    startActivity( new Intent(LoginActivity.this,ValidadorActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Não foi possivel enviar o sms",Toast.LENGTH_LONG).show();
                }

                /*
                HashMap<String,String> dadosusuarioHash = preferencias.getDadosUsuario();
                Log.i("DadosUsuario","nome : " + dadosusuarioHash.get("nome")
                        + "Fone : " + dadosusuarioHash.get("telefone")
                        + "Token : " + dadosusuarioHash.get("token"));
                        */
            }
        });

    }
    //Envia sms
    public boolean enviaSMS(String telefone,String mensagem){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            return true;


        }catch (Exception e){
            e.printStackTrace();
            return false;

        }


    }
    public void onRequestPermissionsResult(int requestCode,String[] permissons,int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permissons,grantResult);

        for(int resultado:grantResult){

            if(resultado== PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    protected void alertaValidacaoPermissao(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar este app é necessário aceitar as permissões");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
