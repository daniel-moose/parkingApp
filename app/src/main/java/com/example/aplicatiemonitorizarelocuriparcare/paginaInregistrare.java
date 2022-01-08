package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;  //importarea librăriilor necesare
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;
public class paginaInregistrare extends AppCompatActivity {
    private EditText emailUtilizator,parolaUtilizator,numeUtilizator,prenumeUtilizator,telefonUtilizator,nrInmatriculare; //inițializarea variabilelor de tip EditText
    private Button butonInregistrare; //inițializarea variabilei de tip Button
    private FirebaseAuth autentificatorFirebase; //inițializarea serviciului Authentication
    String email,parola,nume,prenume,telefon,nrinmatriculare,inparcare,totalplata; //inițializarea variabilelor de tip String
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inregistrare);
        butonInregistrare = findViewById(R.id.butonInregistrare); //sincronizarea variabilei butonInregistrare de tip Button cu obiectul de pe intefața grafică
        setupEditTextUtilizator(); //folosirea funcției setupEditTextUtilizator pentru a sincroniza toate variabilele de tip EditText cu obiectele din interfața grafică
        autentificatorFirebase = FirebaseAuth.getInstance(); //preluarea instanței serviciului Authentication
        butonInregistrare.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonInregistrare execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) {
                if(Validare()){ //verificare dacă utilizatorul a introdus detaliile de înregistrare
                    String email_Utilizator = emailUtilizator.getText().toString().trim(); //preluare text din câmpul de tip EditText emailUtilizator
                    String parola_Utilizator = parolaUtilizator.getText().toString().trim(); //preluare text din câmpul de tip EditText parolaUtilizator
                    autentificatorFirebase.createUserWithEmailAndPassword
                            (email_Utilizator,parola_Utilizator).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //funcție pentru crearea unui nou utlizator cu detaliile de autentificare email_Utilizator și parola_Utilizaotr
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){ //verifcare creare contului
                                trimiteVerificareEmail(); //trimiterea email-ului de validare a adresei
                            }
                            else{ //crearea contului nu a fost posibilă
                                Toast.makeText(paginaInregistrare.this,"Înregistrarea a eșuat!",Toast.LENGTH_SHORT).show();//afișare mesaj de eroare "Înregistrarea a eșuat!"
                            }
                        }
                    });
                }
            }
        });
    }
    private void setupEditTextUtilizator(){ //realizarea funcției de sincronizare a variabilelor de tip EditText cu obiectele de pe interfața grafică
        emailUtilizator = findViewById(R.id.emailUtilizator);
        parolaUtilizator  = findViewById(R.id.parolaUtilizator);
        numeUtilizator = findViewById(R.id.numeUtilizator);
        prenumeUtilizator = findViewById(R.id.prenumeUtilizator);
        telefonUtilizator = findViewById(R.id.telefonUtilizator);
        nrInmatriculare = findViewById(R.id.nrInmatriculare);
    }
        //funcția pentru verificarea introducerii datelor de înregistrare
    private Boolean Validare(){ //realizarea funției pentru verificarea introducerii datelor de înregistrare
        Boolean rezultat =false; //inițializarea unei variabile de tip Boolean cu valoarea false
        email = emailUtilizator.getText().toString(); //preluarea textului din câmpul de tip EditText emailUtilizator în variabila email
        parola = parolaUtilizator.getText().toString(); //preluarea textului din câmpul de tip EditText parolaUtilizator în variabila parola
        nume = numeUtilizator.getText().toString(); //preluarea textului din câmpul de tip EditText numeUtilizator în variabila nume
        prenume = prenumeUtilizator.getText().toString(); //preluarea textului din câmpul de tip EditText prenumeUtilizator în variabila prenume
        telefon = telefonUtilizator.getText().toString(); //preluarea textului din câmpul de tip EditText telefonUtilizator în variabila telefon
        nrinmatriculare = nrInmatriculare.getText().toString(); //preluarea textului din câmpul de tip EditText nrInmatriculare în variabila nrinmatriculare
        inparcare = "0"; //variabila inparcare ia valoarea "0"
        totalplata = "0"; //variabila totalplata ia valoarea "0"
        if(email.isEmpty()|| parola.isEmpty()||nume.isEmpty()||prenume.isEmpty()||telefon.isEmpty()||nrinmatriculare.isEmpty()){ //verificare dacă variabilele au valori nule
            Toast.makeText(paginaInregistrare.this,"Va rugăm introduceți toate detaliile",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Vă rugăm introduceți toate detaliile"
        }
        else { //variabilele au valori nenule
            rezultat = true; //variabila de tip Boolean ia valoarea true(adevărat)
        }
        return rezultat;//funcția Validare returnează valoarea variabilei de tip Boolean
    }
    //funcția de trimitere a datelor utilizatorului în baza de date
    private void trimiteDateUtilizator(){ //realizarea funției trimiteDateUtilizator
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //preluarea instanței bazei de date
        profilUtilizator profilUtilizator = new profilUtilizator(email,parola,nume,prenume,telefon,nrinmatriculare,inparcare,totalplata); //crearea unui obiect de tip profilUtilizator în funcție de datele de înregistrare
        DatabaseReference referintaUtilizatori = firebaseDatabase.getReference("Parking").child("Users").child(Objects.requireNonNull(autentificatorFirebase.getUid())); //realizarea unei referințe către baza de date în datele de tip Utilizator
        referintaUtilizatori.setValue(profilUtilizator);//setarea valorilor cu datele de înregistrare în data de tip Utilizator
    }
    //funcția de trimitere a email-ului de validare a adresei
    private void trimiteVerificareEmail(){ //realizarea funcției trimiteVerificareEmail
        FirebaseUser firebaseUser = autentificatorFirebase.getCurrentUser(); //preluarea utilizatorului curent
        if(firebaseUser !=null){ //există utilizator curent
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() { //funcție de trimitere a email-ului de validare a adresei
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){ //email-ul a fost trimis
                        trimiteDateUtilizator(); //folosirea funcției trimiteDateUtilizator pentru a trimite datele de înregistrare în baza de date
                        Toast.makeText(paginaInregistrare.this,"V-ați înregistrat cu succes! Un email a fost trimis pentru validare!",Toast.LENGTH_SHORT).show(); //afișarea mesajului "V-ați înregistrat cu succes! Un email a fost trimis pentru validare!"
                        autentificatorFirebase.signOut();//ieșirea din contul utilizatorului
                        finish(); //oprirea activității Paginii de Înregistrare
                        startActivity(new Intent(paginaInregistrare.this,MainActivity.class)); //pornirea Paginii de autentificare
                    }
                    else{//email-ul nu a fost trimis
                        Toast.makeText(paginaInregistrare.this,"Email-ul de verificare nu a fost trmis către dumneavoatră",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Email-ul de verificare nu a fost trimis către dumneavoastră"
                    }
                }
            });
        }
    }
}
