package si.uni_lj.fe.tnuv.projekttunv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    private Button button;
    private Button ambulanceBtn;
    private Button patientBtn;
    private Button textDoc;
    private Button clearBtn;
    private TextView clearTxt;
    private String phoneNo;
    private String insuranceNo;
    private String patientName;


    private static si.uni_lj.fe.tnuv.projekttunv.Activity2 ins;

    public static class Entry {
        public final String title;
        public final String link;
        public final String summary;

        private Entry(String title, String summary, String link) {
            this.title = title;
            this.summary = summary;
            this.link = link;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        ins = this;

        // Referencing and Initializing the button
        button = (Button) findViewById(R.id.clickBtn);
        ambulanceBtn = (Button) findViewById(R.id.callAmbulance);
        patientBtn = (Button) findViewById(R.id.callPatient);
        textDoc = (Button) findViewById(R.id.emailDoc);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        clearTxt = (TextView) findViewById(R.id.textV1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

        // Setting onClick behavior to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, button);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.one:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, "Navodila za ravnanje ob primeru epileptičnega napada", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, si.uni_lj.fe.tnuv.projekttunv.Instructions.class);
                                startActivity(intent1);
                                return true;

                            case R.id.two:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, "History", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, History.class);
                                startActivity(intent2);
                                return true;

                            case R.id.three:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, "O profilu", Toast.LENGTH_SHORT).show();
                                //Intent intent3 = new Intent(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, MainActivity.class);
                                //startActivity(intent3);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                return true;

                            case R.id.four:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity2.this, "Logout", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();//logout
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambulanceBtn.setVisibility(View.INVISIBLE);
                patientBtn.setVisibility(View.INVISIBLE);
                textDoc.setVisibility(View.INVISIBLE);
                clearBtn.setVisibility(View.INVISIBLE);
                clearTxt.setText(R.string.message_receiver);

            }
        });

        patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNo = ReceiveSms.getPhone();

                String s = "tel:" + phoneNo;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(s));
                startActivity(intent);
            }
        });
        ambulanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(intent);
            }
        });
        textDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientName = ReceiveSms.getName();
                insuranceNo = ReceiveSms.getNumber();

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String currentDate = sdf.format(new Date());
                String s = "Oseba " + patientName + " - " +  insuranceNo + " - epileptični napad";
                String b = "Pozdravljeni! \n\n Obveščamo vas, da je vaš pacient " + patientName + ", št. zavarovalne police: "+ insuranceNo +" doživel epileptični napad na dne " +
                        currentDate + "." + "\n\nLep pozdrav!\nEpilepsy guardian";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_SUBJECT, s);
                intent.putExtra(Intent.EXTRA_TEXT, b);
                startActivity(Intent.createChooser(intent, ""));
            }
        });


    }

    public static si.uni_lj.fe.tnuv.projekttunv.Activity2 getInstace(){
        return ins;
    }

    public void updateTheTextView(final String t) {
        si.uni_lj.fe.tnuv.projekttunv.Activity2.this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textV1 = (TextView) findViewById(R.id.textV1);
                textV1.setText(t);
                ambulanceBtn.setVisibility(View.VISIBLE);
                patientBtn.setVisibility(View.VISIBLE);
                textDoc.setVisibility(View.VISIBLE);
                clearBtn.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
    }
}

