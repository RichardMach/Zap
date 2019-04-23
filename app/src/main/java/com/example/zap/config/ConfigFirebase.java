package com.example.zap.config;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfigFirebase{
     private static DatabaseReference referenciaFirebase;

     public static DatabaseReference getFirebase(){
         if(referenciaFirebase == null){
             referenciaFirebase = FirebaseDatabase.getInstance().getReference();
         }



         return referenciaFirebase;

     }






    // DatabaseRerences  referenciaDatabase;
}
