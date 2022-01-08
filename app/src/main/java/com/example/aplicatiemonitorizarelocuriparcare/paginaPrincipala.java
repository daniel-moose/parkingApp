package com.example.aplicatiemonitorizarelocuriparcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;  //importarea librăriilor necesare
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class paginaPrincipala extends AppCompatActivity {
    private Button butonDetalii,butonSolicitare,butonSolicitaIntrare,butonSolicitaIesire,butonDetaliiCont; //inițializarea variabilelor de tip Button
    private String textloc1,textloc2,textInParcare,textTotalPlata,loc1Rezervat,loc2Rezervat,loc1,loc2,dataloc11,dataloc12,dataloc21,dataloc22,utilizator,textUtilizator; //inițializarea variabilelor de tip String
    private FirebaseAuth firebaseAuth; //inițializarea serviciului Authentication
    private int dataintloc11, dataintloc12,dataplata1,dataplata2,dataintloc21,dataintloc22,textTotalPlataint; //inițializarea variabilelor de tip int
    private Integer nrLocuri,nrLiber,nrOcupat,nrRezervat,loc1Liber=0,loc2Liber=0,loc1Ocupat=0,loc2Ocupat=0,loc1rezervat=0,loc2rezervat=0; //inițializarea variabilelor de tip int
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);
        butonDetalii = (Button) findViewById(R.id.butonDetalii);//sincronizarea variabilei de tip Button butonDetalii cu obiectul de pe interfața grafică
        butonSolicitare = (Button) findViewById(R.id.butonSolicitare);//sincronizarea variabilei de tip Button butonSolicitare cu obiectul de pe interfața grafică
        butonSolicitaIntrare = (Button) findViewById(R.id.butonSolicitaIntrare);//sincronizarea variabilei de tip Button butonSolicitaIntrare cu obiectul de pe interfața grafică
        butonSolicitaIesire = (Button) findViewById(R.id.butonSolicitaIesire);//sincronizarea variabilei de tip Button butonSolicitaIesire cu obiectul de pe interfața grafică
        butonDetaliiCont = (Button) findViewById(R.id.butonDetaliiCont);//sincronizarea variabilei de tip Button butonDetaliiCont cu obiectul de pe interfața grafică
        DatabaseReference reftextLoc1 = FirebaseDatabase.getInstance().getReference("Parking");//realizarea unei referințe către baza de date
        firebaseAuth = FirebaseAuth.getInstance();//preluarea instanței serviciului Authentication
        utilizator = firebaseAuth.getUid();//preluarea ID-ului utilizatorului în variabila de tip String utilizator
        reftextLoc1.addValueEventListener(new ValueEventListener() { //la actualizarea datelor în baza de date
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //preluarea datelor din baza de date
                textloc1 = snapshot.child("Parking lots").child("Parking lot 1").child("Status").getValue().toString();//preluarea valorii datei "Status" a locului 1 în variabila textloc1
                textloc2 = snapshot.child("Parking lots").child("Parking lot 2").child("Status").getValue().toString();//preluarea valorii datei "Status" a locului 2 în variabila textloc2
                loc1Rezervat = snapshot.child("Parking lots").child("Parking lot 1").child("Booked").getValue().toString();//preluarea valorii datei "Rezervat" a locului 1 în variabila loc1Rezervat
                loc2Rezervat = snapshot.child("Parking lots").child("Parking lot 2").child("Booked").getValue().toString();//preluarea valorii datei "Rezervat" a locului 2 în variabila loc2Rezervat
                textInParcare = snapshot.child("Users").child(utilizator).child("inParking").getValue().toString();//preluarea valorii datei "inParcare" a utilizatorului în variabila textInParcare
                textTotalPlata = snapshot.child("Users").child(utilizator).child("totalPayment").getValue().toString();//preluarea valorii datei "inParcare" a utilizatorului în variabila textTotalPlata
                textUtilizator = snapshot.child("Users").child(utilizator).child("licensePlateNumber").getValue().toString();//preluarea valorii datei "nrInmatriculare" a utilizatorului în variabila textUtilizator
                loc1 = snapshot.child("Parking lots").child("Parking lot 1").child("BookedBy").getValue().toString();//preluarea valorii datei "RezervatDe" a locului 1 în variabila loc1
                loc2 = snapshot.child("Parking lots").child("Parking lot 2").child("BookedBy").getValue().toString();//preluarea valorii datei "RezervatDe" a locului 2 în variabila loc2
                textTotalPlataint = Integer.parseInt(textTotalPlata);
                //realizarea referințelor către baza de date
                DatabaseReference reftextDetaliiLibere = FirebaseDatabase.getInstance().getReference("Parking").child("Details").child("Available parking lots");
                DatabaseReference reftextDetaliiOcupate = FirebaseDatabase.getInstance().getReference("Parking").child("Details").child("Occupied parking lots");
                DatabaseReference reftextDetaliiRezervate = FirebaseDatabase.getInstance().getReference("Parking").child("Details").child("Booked parking lots");
                DatabaseReference reftextDetaliiTotalLocuri = FirebaseDatabase.getInstance().getReference("Parking").child("Details").child("Total parking lots");
                //calcularea numărului de locuri în funcție de statusul lor
                if(textloc1.equals("Available")){//dacă data "Status" a locului 1 este Liber
                    loc1Liber=1;//variabila de tip int loc1Liber ia valoarea 1
                    loc1Ocupat=0;//variabila de tip int loc1Ocupat ia valoarea 0
                    loc1rezervat=0;//variabila de tip int loc1rezervat ia valoarea 0
                }
                else if(textloc1.equals("Occupied")){//dacă data "Status" a locului 1 este Ocupat
                    loc1Liber=0;//variabila de tip int loc1Liber ia valoarea 0
                    loc1Ocupat=1;//variabila de tip int loc1Ocupat ia valoarea 1
                    loc1rezervat=0;//variabila de tip int loc1rezervat ia valoarea 0
                }
                else if(textloc1.equals("Booked")){//dacă data "Status" a locului 1 este Rezervat
                    loc1Liber=0;//variabila de tip int loc1Liber ia valoarea 0
                    loc1Ocupat=0;//variabila de tip int loc1Ocupat ia valoarea 0
                    loc1rezervat=1;//variabila de tip int loc1rezervat ia valoarea 1
                }
                if(textloc2.equals("Available")){//dacă data "Status" a locului 2 este Liber
                    loc2Liber=1;//variabila de tip int loc2Liber ia valoarea 1
                    loc2Ocupat=0;//variabila de tip int loc2Ocupat ia valoarea 0
                    loc2rezervat=0;//variabila de tip int loc2rezervat ia valoarea 0
                }
                else if(textloc2.equals("Occupied")){//dacă data "Status" a locului 2 este Ocupat
                    loc2Liber=0;//variabila de tip int loc2Liber ia valoarea 0
                    loc2Ocupat=1;//variabila de tip int loc2Ocupat ia valoarea 1
                    loc2rezervat=0;//variabila de tip int loc2rezervat ia valoarea 0
                }
                else if(textloc2.equals("Booked")){//dacă data "Status" a locului 2 este Rezervat
                    loc2rezervat=1;//variabila de tip int loc2rezervat ia valoarea 1
                    loc2Liber=0;//variabila de tip int loc2Liber ia valoarea 0
                    loc2Ocupat=0;//variabila de tip int loc2Ocupat ia valoarea 0
                }
                nrLiber=loc1Liber+loc2Liber;//variabila de tip int nrLiber ia valoarea sumei dintre variabilele loc1Liber și loc2Liber
                nrOcupat=loc1Ocupat+loc2Ocupat;//variabila de tip int nrOcupat ia valoarea sumei dintre variabilele loc1Ocupat și loc2Ocupat
                nrRezervat=loc1rezervat+loc2rezervat;//variabila de tip int nrRezervat ia valoarea sumei dintre variabilele loc1rezervat și loc2rezervat
                nrLocuri=nrLiber+nrOcupat+nrRezervat;//variabila de tip int nrLocuri ia valoarea sumei dintre variabilele nrLiber, nrOcupat, nrRezervat
                //trimiterea valorilor în baza de date
                reftextDetaliiTotalLocuri.setValue(nrLocuri);//setarea valorii datei "Locuri Totale" din baza de date cu valoarea variabilei de tip int nrLocuri
                reftextDetaliiLibere.setValue(nrLiber);//setarea valorii datei "Locuri Libere" din baza de date cu valoarea variabilei de tip int nrLiber
                reftextDetaliiOcupate.setValue(nrOcupat);//setarea valorii datei "Locuri Ocupate" din baza de date cu valoarea variabilei de tip int nrOcupat
                reftextDetaliiRezervate.setValue(nrRezervat);//setarea valorii datei "Locuri Rezervate" din baza de date cu valoarea variabilei de tip int nrRezervat
                //afișarea celor două butoane de solicitare a intrării/ieșirii din parcare
                if(textInParcare.equals("1")){//dacă valoarea datei "inParcare" a utilizatorului este 1
                    butonSolicitaIntrare.setVisibility(View.GONE);//setează butonul butonSolicitaIntrare ca fiind inactiv
                    butonSolicitaIesire.setVisibility(View.VISIBLE);//setează butonul butonSolicitaIesire ca fiind activ
                }
                else if(textInParcare.equals("0")){//dacă valoarea datei "inParcare" a utilizatorului este 0
                    butonSolicitaIntrare.setVisibility(View.VISIBLE);//setează butonul butonSolicitaIntrare ca fiind activ
                    butonSolicitaIesire.setVisibility(View.GONE);//setează butonul butonSolicitaIesire ca fiind inactiv
                }
                butonSolicitaIntrare.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonSolicitaIntrare execută instrucțiunile de mai jos
                    @Override
                    public void onClick(View v) {
                        if(loc1.equals(textUtilizator)||loc2.equals(textUtilizator)){ //dacă data "RezervatDe" a locului 1 sau a locului 2 este egală cu numărul de înmatriculare al utilizatorului
                        deschidereCodQR();//folosirea funcției deschidereCodQr pentru a deschide Pagina de solicitare a intrării sau a ieșirii
                        reftextLoc1.child("Users").child(utilizator).child("inParking").setValue("1");//setează valoarea datei "inParcare" a utilizatorului cu valoarea 1
                        butonSolicitaIntrare.setVisibility(View.GONE);//setează butonul butonSolicitaIntrare ca fiind inactiv
                        butonSolicitaIesire.setVisibility(View.VISIBLE);//setează butonul butonSolicitaIesire ca fiind activ
                        }
                        else{//dacă data "RezervatDe" a locului 1 sau a locului 2 nu este egală cu numărul de înmatriculare al utilizatorului
                            Toast.makeText(paginaPrincipala.this,"Please book a parking lot!",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Vă rugăm rezervați un loc de parcare!"
                        }
                    }
                });
                butonSolicitaIesire.setOnClickListener(new View.OnClickListener() {//la apăsarea butonului butonSolicitaIesire execută instrucțiunile de mai jos
                    @Override
                    public void onClick(View v) {
                        if(textTotalPlata.equals("0")){//dacă data "totalPlata" este egală cu 0
                            Toast.makeText(paginaPrincipala.this,"Contact the administrator!",Toast.LENGTH_SHORT).show(); //afișarea mesajului de eroare "Contactați administratorul!"
                        }
                        else{//dacă data "totalPlata" este diferită de 0
                        deschidereCodQR(); ////folosirea funcției deschidereCodQr pentru a deschide Pagina de solicitare a intrării sau a ieșirii
                        reftextLoc1.child("Users").child(utilizator).child("inParking").setValue("0");//setează valoarea datei "inParcare" a utilizatorului cu 0
                        reftextLoc1.child("Users").child(utilizator).child("totalPayment").setValue(0);//setează valoarea datei "totalPlata" a utilizatorului cu 0
                        butonSolicitaIntrare.setVisibility(View.VISIBLE);//setează butonul butonSolicitaIntrare ca fiind activ
                        butonSolicitaIesire.setVisibility(View.GONE);//setează butonul butonSolicitaIesire ca fiind inactiv
                        }
                    }
                });
                //metoda de monitorizare a plății
                Calendar calendar = Calendar.getInstance();//preluarea instanței funcției Calendar
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");//setarea formatului de preluare a secundelor
                if(textloc1.equals("Available") && loc1Rezervat.equals("2")){//dacă data "Status" a locului 1 este egală cu Liber și valoarea datei "Rezervat" este egală cu 2
                    dataloc11 = simpleDateFormat.format(calendar.getTime());//preia timpul în secunde la momentul respectiv în variabila de tip String dataloc11
                    dataintloc11 = Integer.parseInt(dataloc11);//transformă valoarea de tip String a variabilei dataloc11 în valoare de tip int în variabila datainloc11
                }
                if(textloc1.equals("Occupied") && loc1Rezervat.equals("1")){//dacă data "Status" a locului 1 este egală cu Ocupat și valoarea datei "Rezervat" este egală cu 1
                    dataloc12 = simpleDateFormat.format(calendar.getTime());//preia timpul în secunde la momentul respectiv în variabila de tip String dataloc12
                    dataintloc12= Integer.parseInt(dataloc12);//transformă valoarea de tip String a variabilei dataloc12 în valoare de tip int în variabila datainloc12
                }
                if(textloc2.equals("Available") && loc2Rezervat.equals("2")){//dacă data "Status" a locului 2 este egală cu Liber și valoarea datei "Rezervat" este egală cu 2
                    dataloc21 = simpleDateFormat.format(calendar.getTime());//preia timpul în secunde la momentul respectiv în variabila de tip String dataloc21
                    dataintloc21 = Integer.parseInt(dataloc21);//transformă valoarea de tip String a variabilei dataloc11 în valoare de tip int în variabila datainloc21
                }
                if(textloc2.equals("Occupied") && loc2Rezervat.equals("1")){//dacă data "Status" a locului 2 este egală cu Ocupat și valoarea datei "Rezervat" este egală cu 1
                    dataloc22 = simpleDateFormat.format(calendar.getTime());//preia timpul în secunde la momentul respectiv în variabila de tip String dataloc22
                    dataintloc22= Integer.parseInt(dataloc22);//transformă valoarea de tip String a variabilei dataloc22 în valoare de tip int în variabila datainloc22
                }
                if(dataintloc11>dataintloc12){//dacă valorea variabilei dataintloc11 este mai mare decât valoarea variabilei dataintloc12
                    dataplata1 = 0;//variabila dataplata1 ia valoarea 0
                    dataplata1 = dataintloc11-dataintloc12;//după care variabila dataplata1 ia valoarea diferenței dintre variabilele dataintloc11 și dataintloc12
                }
                else{ //dacă valorea variabilei dataintloc11 nu este mai mare decât valoarea variabilei dataintloc12
                    dataplata1 = 0;//variabila dataplata1 ia valoarea 0
                }
                if(dataplata1>0 && dataplata1 <= 10 ) {//dacă variabila dataplata1 are valori între 0 și 10
                    if (textUtilizator.equals(loc1)) { //dacă valoarea datei "nrInmatriculare" a utilizatorului este egală cu valoarea datei "RezervatDe" a locului 1 de parcare
                        Toast.makeText(paginaPrincipala.this, "You have to pay: "+dataplata1+" lei", Toast.LENGTH_SHORT).show();//afișarea mesajului "Aveți de plata" și valoarea datei dataplata1
                        reftextLoc1.child("Parking lots").child("Parking lot 1").child("BookedBy").setValue("");//setarea valorii datei "RezervatDe" a locului 1 cu " "
                        reftextLoc1.child("Parking lots").child("Parking lot 1").child("Booked").setValue(0);//setarea valorii datei "Rezervat" a locului 1 cu 0
                        reftextLoc1.child("Users").child(utilizator).child("totalPayment").setValue(dataplata1);//setarea valorii datei "totalPlata" a utilizatorului cu valoare variabilei dataplata1
                        dataintloc11 = 0;//variabila dataintloc11 ia valoarea 0
                        dataintloc12= 0;//variabila dataintloc12 ia valoarea 0
                        dataplata1=0;//variabila dataplata1 ia valoarea 0
                    }
                    else{//dacă valoarea datei "nrInmatriculare" a utilizatorului nu este egală cu valoarea datei "RezervatDe" a locului 1 de parcare
                        reftextLoc1.child("Parking lots").child("Parking lot 1").child("BookedBy").setValue("");//setarea valorii datei "RezervatDe" a locului 1 cu " "
                        reftextLoc1.child("Parking lots").child("Parking lot 1").child("Booked").setValue(0);//setarea valorii datei "Rezervat" a locului 1 cu 0
                        dataintloc11 = 0;//variabila dataintloc11 ia valoarea 0
                        dataintloc12= 0;//variabila dataintloc12 ia valoarea 0
                        dataplata1=0;//variabila dataplata1 ia valoarea 0
                    }
                }
                if(dataintloc21>dataintloc22){//dacă valorea variabilei dataintloc21 este mai mare decât valoarea variabilei dataintloc22
                    dataplata2 = 0;//variabila dataplata2 ia valoarea 0
                    dataplata2=dataintloc21-dataintloc22;//după care variabila dataplata2 ia valoarea diferenței dintre variabilele dataintloc21 și dataintloc22
                }
                else{//dacă valorea variabilei dataintloc21 nu este mai mare decât valoarea variabilei dataintloc22
                    dataplata2 = 0;//variabila dataplata2 ia valoarea 0
                }
                if(dataplata2>0 && dataplata2 <= 10 ) {//dacă variabila dataplata2 are valori între 0 și 10
                    if (textUtilizator.equals(loc2)) {//dacă valoarea datei "nrInmatriculare" a utilizatorului este egală cu valoarea datei "RezervatDe" a locului 2 de parcare
                        Toast.makeText(paginaPrincipala.this, "You have to pay: "+dataplata2+" lei", Toast.LENGTH_SHORT).show();//afișarea mesajului "Aveți de plata" și valoarea datei dataplata2
                        reftextLoc1.child("Parking lots").child("Parking lot 2").child("BookedBy").setValue("");//setarea valorii datei "RezervatDe" a locului 2 cu " "
                        reftextLoc1.child("Parking lots").child("Parking lot 2").child("Booked").setValue(0);//setarea valorii datei "Rezervat" a locului 2 cu 0
                        reftextLoc1.child("Users").child(utilizator).child("totalPayment").setValue(dataplata2);//setarea valorii datei "totalPlata" a utilizatorului cu valoare variabilei dataplata2
                        dataintloc21 = 0;//variabila dataintloc21 ia valoarea 0
                        dataintloc22= 0;//variabila dataintloc22 ia valoarea 0
                        dataplata2 = 0;//variabila dataplata2 ia valoarea 0
                    }
                    else{//dacă valoarea datei "nrInmatriculare" a utilizatorului nu este egală cu valoarea datei "RezervatDe" a locului 2 de parcare
                        reftextLoc1.child("Parking lots").child("Parking lot 2").child("BookedBy").setValue("");//setarea valorii datei "RezervatDe" a locului 2 cu " "
                        reftextLoc1.child("Parking lots").child("Parking lot 2").child("Booked").setValue(0);//setarea valorii datei "Rezervat" a locului 2 cu 0
                        dataintloc21 = 0;//variabila dataintloc21 ia valoarea 0
                        dataintloc22= 0;//variabila dataintloc22 ia valoarea 0
                        dataplata2=0;//variabila dataplata2 ia valoarea 0
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        butonDetalii.setOnClickListener(new View.OnClickListener() { //la apăsarea butonului butonDetalii execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) { deschidereDetalii();//folosirea funcției deschidereDetalii pentru a deschide Pagina detalii parcare
            }
        });
        butonSolicitare.setOnClickListener(new View.OnClickListener() {//la apăsarea butonului butonSolicitare execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) { deschidereSolicitare();//folosirea funcției deschidereSolicitare pentru a deschide Pagina pentru solicitare a unei rezervări
            }
        });
        butonDetaliiCont.setOnClickListener(new View.OnClickListener() {//la apăsarea butonului butonDetaliiCont execută instrucțiunile de mai jos
            @Override
            public void onClick(View v) { deschidereDetaliiCont();//folosirea funcției deschidereDetaliiCont pentru a deschide Pagina detalii cont
            }
        });
    }
    //funcția de deschidere a Paginii detalii parcare
    public void deschidereDetalii(){
        Intent intent = new Intent(this, detaliiParcare.class);//crearea unei intenții de a deschide Pagina detalii parcare
        startActivity(intent);//deschiderea Paginii detalii parcare
    }
    //funcția de deschidere a Paginii pentru solicitarea unei rezervări
    public void deschidereSolicitare(){
        Intent intent = new Intent(this, solicitaParcare.class);//crearea unei intenții de a deschide Pagina pentru solicitarea unei parcări
        startActivity(intent);//deschiderea Paginii pentru solicitarea unei rezervări
    }
    //funcția de deschidere a Paginii pentru solicitarea intrării/ieșirii din parcare
    public void deschidereCodQR(){
        Intent intent = new Intent(this, codIntrareIesire.class);//crearea unei intenții de a deschide Pagina pentru solicitarea intrării/ieșirii
        startActivity(intent);//deschiderea Paginii pentru solicitarea intrării/ieșirii din parcare
    }
    //funcția de deschidere a Paginii detalii cont
    public void deschidereDetaliiCont(){
        Intent intent = new Intent(this, detaliiCont.class);//crearea unei intenții de a deschide Pagina detalii cont
        startActivity(intent);//deschiderea Paginii detalii cont
    }
}
