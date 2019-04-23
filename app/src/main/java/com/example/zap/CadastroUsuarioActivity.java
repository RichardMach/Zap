package com.example.zap;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zap.config.ConfigFirebase;
import com.example.zap.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText eTxtNome;
    private EditText eTxtEmail;
    private EditText eTxtPass;
    private Button btnCadastrar;
    private FirebaseAuth firebaseAuth;

    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        eTxtNome  = findViewById(R.id.eTxtName);
        eTxtEmail = findViewById(R.id.eTxtEmailCadastro);
        eTxtPass  = findViewById(R.id.eTxtPassCadastro);

        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user = new User();

                user.setNome(eTxtNome.getText().toString());
                user.setEmail(eTxtEmail.getText().toString());
                user.setPass(eTxtPass.getText().toString());

                cadastrarUsuario();
            }
        });




    }

    private void cadastrarUsuario(){

        firebaseAuth = ConfigFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPass()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastro Realizado com Sucesso",Toast.LENGTH_LONG).show();
                    /*FirebaseUser firebaseUser = task.getResult().getUser();
                    user.setId(firebaseUser.getUid());*/
                    user.setId(task.getResult().getUser().getUid());
                    user.salvarUsuario();
                }else {
                    Toast.makeText(CadastroUsuarioActivity.this,"Erro ao Cadastrar",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
