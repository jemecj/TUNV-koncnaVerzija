package si.uni_lj.fe.tnuv.projekttunv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {
    private ArrayList names;
    private Button clearHistory;
    private ListView lv;

    private static int positionOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        names = ReceiveSms.getNames();
        clearHistory = (Button) findViewById(R.id.clearHistory);

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getApplicationContext(),
                        //"Podrobna zgodovina " + names.get(position), Toast.LENGTH_LONG)
                       // .show();
                positionOut = position;
                startActivity(new Intent(getApplicationContext(),PatientHistory.class));

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names);
        lv.setAdapter(arrayAdapter);

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names.clear();
                arrayAdapter.clear();
            }
        });
    }
    public static int getPosition(){
        return positionOut;
    }

}