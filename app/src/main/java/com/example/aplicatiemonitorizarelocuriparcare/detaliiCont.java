package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;     //importarea librăriilor necesare
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class detaliiCont extends AppCompatActivity {
private String utilizator,textNume,textPrenume,textnrInmatriculare,textplata;  //inițializarea variabilelor de tip String
private FirebaseAuth firebaseAuth; //inițializarea serviciului Authentication
private TextView textNumePrenume,textNrInmatriculare,textPlata; //inițializarea variabilelor de tip TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cont);
        DatabaseReference reftextLoc1 = FirebaseDatabase.getInstance().getReference("Parking"); //realizarea referinței către baza de date
        firebaseAuth = FirebaseAuth.getInstance(); //preluarea instanței serviciului Authentication
        utilizator = firebaseAuth.getCurrentUser().getUid(); //preluarea ca String a ID-ului utilizatorului autentificat
        textNumePrenume = (TextView) findViewById(R.id.textNumePrenume); //sincronizarea variabilei textNumePrenume de tip TextView cu obiectul de pe intefața grafică
        textNrInmatriculare = (TextView) findViewById(R.id.textNrInmatriculare);//sincronizarea variabilei textNumePrenume de tip TextView cu obiectul de pe intefața grafică
        textPlata = (TextView) findViewById(R.id.textPlata);//sincronizarea variabilei textPlata de tip TextView cu obiectul de pe intefața grafică
        reftextLoc1.addValueEventListener(new ValueEventListener() {  //la fiecare actualizare a informațiilor în baza de date execută instrucțiunile de mai jos
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //preluarea datelor din baza de date
                textNume = snapshot.child("Users").child(utilizator).child("firstName").getValue().toString(); //preluarea valorii datei numeUtilizator în variabila de tip String textNume
                textPrenume = snapshot.child("Users").child(utilizator).child("secondName").getValue().toString(); //preluarea valorii datei prenumeUtilizator în variabila de tip String textPrenume
                textnrInmatriculare = snapshot.child("Users").child(utilizator).child("licensePlateNumber").getValue().toString(); //preluarea valorii datei nrInmatriculare în variabila de tip String textnrInmatriculare
                textplata = snapshot.child("Users").child(utilizator).child("totalPayment").getValue().toString(); //preluarea valorii datei totalPlata în variabila de tip String textplata
                //setarea textului câmpurilor din interfața grafică
                textNumePrenume.setText("Name: "+textNume+" "+textPrenume); //setarea textului câmpului de tip TextView textNumePrenume de pe interfața grafică cu valorile varibilelor de tip String textNume și textPrenume
                textNrInmatriculare.setText("License plate number: "+textnrInmatriculare); //setarea textului câmpului de tip TextView textNrInmatriculare de pe interfața grafică cu valoarea varibilei de tip String textnrInmatriculare
                textPlata.setText("Total payment: "+ textplata); //setarea textului câmpului de tip TextView textPlata de pe interfața grafică cu valoarea varibilei de tip String textplata
                 }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}