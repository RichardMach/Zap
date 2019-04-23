package com.example.zap.model;

import com.example.zap.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class User {
    private String id;
    private String nome;
    private String email;
    private String pass;

    public User() {


    }
    public void salvarUsuario(){
        DatabaseReference databaseReference = ConfigFirebase.getFirebase();
        databaseReference.child("usuarios").child(getId()).setValue(this);//posso pasar um objeto ou usuario
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
