package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;   //importarea librăriilor necesare
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
public class parolaUitata extends AppCompatActivity {
    private EditText resetareParola; //inițializarea variabilei de tip EditText
    private Button butonResetareParola; //inițializarea variabilei de tip Button
    private FirebaseAuth autentificatorFirebase; //inițializarea serviciului Authentication
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parola_uitata);
        resetareParola = findViewById(R.id.resetareParola); //sincronizarea variabilei resetareParola de tip EditText cu obiectul de pe intefața grafică
        butonResetareParola = findViewById(R.id.butonTrimitereEmailResetare); //sincronizarea variabilei butonResetareParola de tip Button cu obiectul de pe intefața grafică
        autentificatorFirebase = FirebaseAuth.getInstance(); //preluarea instanței serviciului Authentication
        butonResetareParola.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonResetareParola execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) {
                String emailUtilizator = resetareParola.getText().toString().trim(); //preluarea textului scris în câmpul resetareParola de tip EditText
                if(emailUtilizator.equals("")){ //condiția ca în câmpul resetareParola să nu fie scris nimic
                    Toast.makeText(parolaUitata.this,"Vă rugăm introduceți adresa de email!",Toast.LENGTH_SHORT).show(); //afișarea mesajului eroare "Vă rugăm introduceți adresa de email!"
                }
                else{
                    autentificatorFirebase.sendPasswordResetEmail(emailUtilizator).addOnCompleteListener(new OnCompleteListener<Void>() { //funția de trimitere a unui email de resetare a parolei
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){ //condiția ca emailul să fie trimis
                                Toast.makeText(parolaUitata.this,"Un email de resetare a parolei a fost trimis către dumneavoastră!",Toast.LENGTH_SHORT).show(); //afișarea mesajului "Un email de resetare a parolei a fost trimis către dumneavoastră!"
                                finish(); //oprirea activității Paginii de resetare a parolei
                                startActivity(new Intent(parolaUitata.this,MainActivity.class)); //pornirea Paginii de autentificare
                            }
                            else{
                                Toast.makeText(parolaUitata.this,"Vă rugăm introduceți email-ul corect!",Toast.LENGTH_SHORT).show(); //afișarea mesajului "Vă rugăm introduceți email-ul corect!"
                            }
                        }
                    });
                }
            }
        });
    }
}