package com.example.zap.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.prefs.Preferences;

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;//interface para manipular o arquivo

    private String WHATS_APP="whatsapp.preferencias";
    private int MODE=0;

    private String CHAVE_NOME     = "nome";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN    = "token";

    public Preferencias(Context contextoParametros) {

        context=contextoParametros;
        //Quando o MODE é 0 só o seu app pode acessar o arquivo
        preferences = context.getSharedPreferences(WHATS_APP,MODE);
        editor = preferences.edit();

    }

    public void salvarUsuarioPreferences(String nome,String fone,String token){
        //Adicionado ao arquivo preferences os valores acima

        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,fone);
        editor.putString(CHAVE_TOKEN,token);
        editor.commit();

    }
    public HashMap<String,String> getDadosUsuario(){

        HashMap<String,String> dadosusuario = new HashMap<>();

        dadosusuario.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
        dadosusuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE,null));
        dadosusuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN,null));

        return dadosusuario;


    }

}
