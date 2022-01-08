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
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    private Button butonLogin, butonParolaUitata, butonInregistrare; //inițializarea variabilelor de tip Button
    private EditText emailAutentificare, parolaAutentificare; //inițializarea variabilelor de tip EditText
    private FirebaseAuth autentificatorFirebase; //inițializarea serviciului Authentication
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butonLogin = (Button) findViewById(R.id.butonAutentificare); //sincronizarea variabilei butonLogin de tip Button cu obiectul de pe intefața grafică
        butonParolaUitata = (Button) findViewById(R.id.butonParolaUitata); //sincronizarea variabilei butonParolaUitata de tip Button cu obiectul de pe intefața grafică
        butonInregistrare = (Button) findViewById(R.id.butonInregistrare); //sincronizarea variabilei butonInregistrare de tip Button cu obiectul de pe intefața grafică
        emailAutentificare = (EditText)findViewById(R.id.emailAutentificare); //sincronizarea variabilei emailAutentificare de tip EditText cu obiectul de pe intefața grafică
        parolaAutentificare = (EditText)findViewById(R.id.parolaAutentificare); //sincronizarea variabilei parolaAutentificare de tip EditText cu obiectul de pe intefața grafică
        autentificatorFirebase = FirebaseAuth.getInstance(); //preluarea instanței serviciului Authentication
        butonLogin.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonLogin execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) {
                if(emailAutentificare.getText().toString().isEmpty() || parolaAutentificare.getText().toString().isEmpty()){  //condiția pentru câmpurile de tip EditText să fie goale
                    Toast.makeText(MainActivity.this,"Introduceți toate datele de autentificare!",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Introduceți toate datele de autentificare!"
                }
                else {
                    Validare(emailAutentificare.getText().toString(), parolaAutentificare.getText().toString()); //folosirea funcției Validare pentru a se face autentificarea
                }
                }
        });
        butonParolaUitata.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonParolaUitata execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) { deschiderePaginaParolaUitata(); //folosirea funcției deschiderePaginaParolaUitata pentru a deschide Pagina pentru resetare a parolei
            }
        });
        butonInregistrare.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonInregistrare execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) { deschiderePaginaInregistrare(); //folosirea funcției deschiderePaginaParolaInregistrare pentru a deschide Pagina pentru inregistrare
            }
        });
    }
    //funcția de deschidere a Paginii de resetare a parolei
    public void deschiderePaginaParolaUitata(){ //crearea funcției deschiderePaginaParolaUitata
        Intent intent = new Intent(this, parolaUitata.class); //crearea unei intenții de a deschide Pagina pentru resetare a parolei
        startActivity(intent); //deschiderea Paginii pentru resetare a parolei
    }
    //funcția de deschidere a Paginii de înregistrare
    public void deschiderePaginaInregistrare(){ //crearea funcției deschiderePaginaInregistrare
        Intent intent = new Intent(this, paginaInregistrare.class); //crearea unei intenții de a deschide Pagina pentru inregistrare
        startActivity(intent); //deschiderea Paginii pentru inregistrare
    }
    //funcția de verificare a validării adresei de email
    private void verificareEmail(){ //crearea funției verificareEmail
        FirebaseUser utilizatorFirebase = autentificatorFirebase.getCurrentUser(); //preluarea utilizatorului autentifica
        Boolean emailVerificat = utilizatorFirebase.isEmailVerified(); //verificare dacă adresa de e-mail este validată
        if(emailVerificat){ //condiția ca adresa să fie validată
            startActivity(new Intent(MainActivity.this,paginaPrincipala.class)); //deschiderea Paginii principale
            finish(); //oprirea activității Paginii de autentificare
        }
        else{
            Toast.makeText(MainActivity.this,"Vă rugăm verificați-vă email-ul!",Toast.LENGTH_SHORT).show(); //afișarea mesajului "Vă rugăm verificați-vă email-ul"
            autentificatorFirebase.signOut(); //ieșirea din cont a utilizatorului autentificat
            //startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
    }
    //funcția de autentificare
    private void Validare(final String numeUtilizator,final String parolaUtilizator){ //crearea funției Validare
        autentificatorFirebase.signInWithEmailAndPassword(numeUtilizator,parolaUtilizator).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //intrarea în cont a utilizatorului
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //verificare dacă adresa și parola corespund
                    verificareEmail(); //folosirea funcției verificareEmail pentru a se verifica dacă adresa este validată și pentru deschiderea Pagini principale
                    finish(); //oprirea activității Paginii de autentificare
                }
                else{ //adresa și parola nu corespund
                    Toast.makeText(MainActivity.this,"Login failed! Wrong e-mail address or password.",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Autentificare eșuată! Email-ul sau parola este greșită"
                }
            }
        });
    }
}