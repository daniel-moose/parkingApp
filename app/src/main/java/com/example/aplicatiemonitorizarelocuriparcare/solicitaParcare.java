package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;  //importarea librăriilor necesare
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class solicitaParcare extends AppCompatActivity {
    private TextView textLoc1,textLoc2;  //inițializarea variabilelor de tip TextView
    private Button butonLoc1,butonLoc2;  //inițializarea variabilelor de tip Button
    private String textloc1,loc1Rezervat,textloc2,loc2Rezervat,loc1,loc2,utilizator,textUtilizator; //inițializarea variabilelor de tip String
    private FirebaseAuth firebaseAuth;  //inițializarea serviciului Authentication
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita_parcare);
        textLoc1 = (TextView) findViewById(R.id.textLoc1); //sincronizarea variabilei de tip TextView textloc1 cu obiectul de pe interfața grafică
        textLoc2 = (TextView)findViewById(R.id.textLoc2); //sincronizarea variabilei de tip TextView textloc2 cu obiectul de pe interfața grafică
        butonLoc1 = (Button) findViewById(R.id.butonLoc1); //sincronizarea variabilei de tip Button butonLoc1 cu obiectul de pe interfața grafică
        butonLoc2 = (Button) findViewById(R.id.butonLoc2); //sincronizarea variabilei de tip Button butonLoc2 cu obiectul de pe interfața grafică
        final DatabaseReference reftextLoc1 = FirebaseDatabase.getInstance().getReference("Parking"); //realizarea unei referințe către baza de date
        firebaseAuth = FirebaseAuth.getInstance(); //preluarea instanței serviciului Authentication
        utilizator = firebaseAuth.getUid(); //preluarea ID-ului utilizatorului în variabila de tip String utilizator
        reftextLoc1.addValueEventListener(new ValueEventListener(){ //la actualizarea datelor din baza de date execută instrucțiunile de mai jos
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //preluarea datelor din baza de date
                textloc1 = snapshot.child("Parking lots").child("Parking lot 1").child("Status").getValue().toString();//preluarea valorii datei "Status" a locului 1 în variabila textloc1
                textloc2 = snapshot.child("Parking lots").child("Parking lot 2").child("Status").getValue().toString();//preluarea valorii datei "Status" a locului 2 în variabila textloc2
                loc1Rezervat = snapshot.child("Parking lots").child("Parking lot 1").child("Booked").getValue().toString();//preluarea valorii datei "Rezervat" a locului 1 în variabila loc1Rezervat
                loc2Rezervat = snapshot.child("Parking lots").child("Parking lot 2").child("Booked").getValue().toString();//preluarea valorii datei "Rezervat" a locului 2 în variabila loc2Rezervat
                textUtilizator = snapshot.child("Users").child(utilizator).child("licensePlateNumber").getValue().toString();//preluarea valorii datei "nrInmatriculare" a utilizatorului în variabila textUtilizator
                loc1 = snapshot.child("Parking lots").child("Parking lot 1").child("BookedBy").getValue().toString();//preluarea valorii datei "RezervatDe" a locului 1 în variabila loc1
                loc2 = snapshot.child("Parking lots").child("Parking lot 2").child("BookedBy").getValue().toString();//preluarea valorii datei "RezervatDe" a locului 2 în variabila loc2
                //schimbarea culorilor butoanelor în funcție de statusul locurilor de parcare
                if(textloc1.equals("Available")){//Data "Status" a locului 1 este "Liber"
                    butonLoc1.setBackgroundColor(Color.parseColor("#23f018")); //butonul butonLoc1 devine verde
                }
                else if(textloc1.equals("Occupied")){//Data "Status" a locului 1 este "Ocupat"
                    butonLoc1.setBackgroundColor(Color.parseColor("#f0181c")); //butonul butonLoc1 devine roșu
                }
                else if(textloc1.equals("Booked")){//Data "Status" a locului 1 este "Rezervat"
                    butonLoc1.setBackgroundColor(Color.parseColor("#fc792d")); //butonul butonLoc1 devine portocaliu
                }
                if(textloc2.equals("Available")){//dacă data "Status" a locului 1 este "Liber"
                    butonLoc2.setBackgroundColor(Color.parseColor("#23f018"));//butonul butonLoc2 devine verde
                }
                else if(textloc2.equals("Occupied")){ //dacă data "Status" a locului 2 este "Ocupat"
                    butonLoc2.setBackgroundColor(Color.parseColor("#f0181c"));//butonul butonLoc2 devine roșu
                }
                else if(textloc2.equals("Booked")){//dacă data "Status" a locului 2 este "Rezervat"
                    butonLoc2.setBackgroundColor(Color.parseColor("#fc792d"));//butonul butonLoc2 devine portocaliu
                }
                //setarea textului câmpurilor din interfața grafică
                if(loc1Rezervat.equals("0")){//dacă data "Rezervat" a locului 1 este 0
                textLoc1.setText(textloc1); //setarea textului câmpului textLoc1 cu textul variabilei textloc1
                }
                else { //dacă data "Rezervat" a locului 1 este 1
                    if(textloc1.equals("Booked")) { //dacă data "Status" a locului 1 este Rezervat
                        textLoc1.setText("Booked by: " + loc1); //setarea textului câmpului textLoc1 cu textul variabilei loc1
                    }
                    else{//data "Status" a locului 1 este diferită de Rezervat
                        textLoc1.setText(textloc1); //setarea textului câmpului textLoc1 cu textul variabilei textloc1
                    }
                }
                if(loc2Rezervat.equals("0")){//dacă data "Rezervat" a locului 2 este 0
                    textLoc2.setText(textloc2);//setarea textului câmpului textLoc2 cu textul variabilei textloc2
                }
                else{//dacă data "Rezervat" a locului 2 este 1
                    if(textloc2.equals("Booked")) { //dacă data "Status" a locului 2 este Rezervat
                        textLoc2.setText("Booked by: " + loc2);//setarea textului câmpului textLoc2 cu textul variabilei loc2
                    }
                    else{//data "Status" a locului 2 este diferită de Rezervat
                        textLoc2.setText(textloc2);//setarea textului câmpului textLoc2 cu textul variabilei textloc2
                    }
                }
                    butonLoc1.setOnClickListener(new View.OnClickListener() {//la apăsarea butonului butonLoc1 execută instrucțiunile de mai jos
                        @Override
                        public void onClick(View v) {
                            if(textloc1.equals("Available")) { //dacă data "Status" a locului 1 este Liber
                                Toast.makeText(solicitaParcare.this, "You have booked parking lot 1", Toast.LENGTH_SHORT).show();//afișarea mesajului "Ați realizat o rezervare pentru Locul 1!"
                                reftextLoc1.child("Parking lots").child("Parking lot 1").child("Booked").setValue(1);//setarea valorii datei "Rezervat" a locului 1 cu 1
                                reftextLoc1.child("Parking lots").child("Parking lot 1").child("BookedBy").setValue(textUtilizator);//setarea valorii datei "RezervatDe" a locului 1 cu numărul de înmatriculare al utilizatorului
                                reftextLoc1.child("Parking lots").child("Parking lot 1").child("Status").setValue("Booked");//setarea valorii datei "Status" a locului 1 cu Rezervat
                                startActivity(new Intent(solicitaParcare.this,paginaPrincipala.class));// deschiderea Paginii principale
                                finish();//încheierea activității Paginii de solicitare a unei rezervări
                            }
                            else{//dacă data "Status" a locului 1 este diferită de Liber
                                Toast.makeText(solicitaParcare.this, "Locul de parcare este "+textloc1+"!", Toast.LENGTH_SHORT).show();//afișarea mesajului de eroare "Locul de parcare este" Rezervat sau Ocupat în funcție de valoarea variabilei textloc1
                            }
                        }
                    });
                    butonLoc2.setOnClickListener(new View.OnClickListener() {//la apăsarea butonului butonLoc2 execută instrucțiunile de mai jos
                        @Override
                        public void onClick(View v) {
                            if(textloc2.equals("Available")) { //dacă data "Status" a locului 2 este Liber
                                Toast.makeText(solicitaParcare.this, "You have booked parking lot 2", Toast.LENGTH_SHORT).show(); //afișarea mesajului "Ați realizat o rezervare pentru Locul 2!"
                                reftextLoc1.child("Parking lots").child("Parking lot 2").child("Booked").setValue(1); //setarea valorii datei "Rezervat" a locului 2 cu 1
                                reftextLoc1.child("Parking lots").child("Parking lot 2").child("BookedBy").setValue(textUtilizator); //setarea valorii datei "RezervatDe" a locului 2 cu numărul de înmatriculare al utilizatorului
                                reftextLoc1.child("Parking lots").child("Parking lot 2").child("Status").setValue("Booked");//setarea valorii datei "Status" a locului 2 cu Rezervat
                                startActivity(new Intent(solicitaParcare.this,paginaPrincipala.class));// deschiderea Paginii principale
                                finish();//încheierea activității Paginii de solicitare a unei rezervări
                            }
                            else{//dacă data "Status" a locului 2 este diferită de Liber
                                Toast.makeText(solicitaParcare.this, "Locul de parcare este "+textloc2+"!", Toast.LENGTH_SHORT).show();//afișarea mesajului de eroare "Locul de parcare este" Rezervat sau Ocupat în funcție de valoarea variabilei textloc2
                            }
                        }
                    });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
