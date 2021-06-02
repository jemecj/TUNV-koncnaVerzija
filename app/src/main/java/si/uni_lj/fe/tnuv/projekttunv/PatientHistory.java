package si.uni_lj.fe.tnuv.projekttunv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientHistory extends AppCompatActivity {
    private ArrayList names;
    private ArrayList locations;
    private ArrayList numbers;
    private ArrayList types;
    private ArrayList phones;
    private Button patientBtn;
    private Button textDoc;

    private String shownText = "";
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);
        names = ReceiveSms.getNames();
        locations = ReceiveSms.getLocations();
        numbers = ReceiveSms.getNumbers();
        types = ReceiveSms.getTypes();
        phones = ReceiveSms.getPhones();
        position = History.getPosition();
        patientBtn = (Button) findViewById(R.id.callPatient);
        textDoc = (Button) findViewById(R.id.emailDoc);


        shownText = shownText + "Oseba: "+names.get(position) + "\nŠt. zavarovanja: " +numbers.get(position) + "\nVrsta napada: " +
                types.get(position) +"\nLokacija: " +locations.get(position) + "\nTel. številka: " + phones.get(position);

        TextView textV1 = (TextView) findViewById(R.id.data);
        textV1.setText(shownText);

        patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "tel:" + phones.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(s));
                startActivity(intent);
            }
        });

        textDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String currentDate = sdf.format(new Date());
                String s = "Oseba " + names.get(position) + " - " +  numbers.get(position) + " - epileptični napad";
                String b = "Pozdravljeni! \n\n Obveščamo vas, da je vaš pacient " + names.get(position) + ", št. zavarovalne police: "+ numbers.get(position) +" doživel epileptični napad na dne " +
                        currentDate + "." + "\n\nLep pozdrav!\nEpilepsy guardian";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_SUBJECT, s);
                intent.putExtra(Intent.EXTRA_TEXT, b);
                startActivity(Intent.createChooser(intent, ""));
            }
        });



    }
}