package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;  //importarea librăriilor necesare
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class detaliiParcare extends AppCompatActivity {
    String nrLocuriLibere, nrLocuriOcupate, nrLocuriRezervate, nrLocuri,bariera; //inițializarea variabilelor de tip String
    TextView textTotal,textLiber,textOcupat, textRezervat,textBariera; //inițializarea variabilelor de tip TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_parcare);
        DatabaseReference reftextDetalii = FirebaseDatabase.getInstance().getReference("Parking"); //realizarea referinței către baza de date
        textTotal = (TextView) findViewById(R.id.textTotal); //sincronizarea variabilei textTotal de tip TextView cu obiectul de pe intefața grafică
        textLiber = (TextView) findViewById(R.id.textLiber); //sincronizarea variabilei textLiber de tip TextView cu obiectul de pe intefața grafică
        textOcupat = (TextView) findViewById(R.id.textOcupat); //sincronizarea variabilei textOcupat de tip TextView cu obiectul de pe intefața grafică
        textRezervat = (TextView) findViewById(R.id.textRezervat); //sincronizarea variabilei textRezervat de tip TextView cu obiectul de pe intefața grafică
        textBariera = (TextView) findViewById(R.id.textBariera); //sincronizarea variabilei textBariera de tip TextView cu obiectul de pe intefața grafică
        reftextDetalii.addValueEventListener(new ValueEventListener() { //la fiecare actualizare a informațiilor execută instrucțiunile de mai jos
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //preluarea datelor din baza de date
                nrLocuri = snapshot.child("Details").child("Total parking lots").getValue().toString(); //preluarea valorii datei "Locuri Totale" în variabila de tip String nrLocuri
                nrLocuriLibere = snapshot.child("Details").child("Available parking lots").getValue().toString(); //preluarea valorii datei "Locuri Libere" în variabila de tip String nrLocuriLibere
                nrLocuriOcupate = snapshot.child("Details").child("Occupied parking lots").getValue().toString(); //preluarea valorii datei "Locuri Ocupate" în variabila de tip String nrLocuriOcupate
                nrLocuriRezervate = snapshot.child("Details").child("Booked parking lots").getValue().toString(); //preluarea valorii datei "Locuri Rezervate" în variabila de tip String nrLocuriRezervate
                bariera = snapshot.child("Entrance").getValue().toString(); //preluarea valorii datei "Intrare" în variabila de tip String bariera
                //setarea textului câmpurilor din interfața grafică
                textTotal.setText("Total locuri: "+nrLocuri); //setarea textului câmpului de tip TextView textTotal de pe interfața grafică cu valoarea varibilei de tip String nrLocuri
                textLiber.setText("Locuri libere: "+nrLocuriLibere); //setarea textului câmpului de tip TextView textLiber de pe interfața grafică cu valoarea varibilei de tip String nrLocuriLibere
                textOcupat.setText("Locuri ocupate: "+nrLocuriOcupate); //setarea textului câmpului de tip TextView textOcupat de pe interfața grafică cu valoarea varibilei de tip String nrLocuriOcupate
                textRezervat.setText("Locuri rezervate: "+nrLocuriRezervate); //setarea textului câmpului de tip TextView textRezervat de pe interfața grafică cu valoarea varibilei de tip String nrLocuriRezervate
                textBariera.setText("Barieră intrare: "+bariera); //setarea textului câmpului de tip TextView textBariera de pe interfața grafică cu valoarea varibilei de tip String bariera
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
