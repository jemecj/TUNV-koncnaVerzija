package si.uni_lj.fe.tnuv.projekttunv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Activity3 extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // Referencing and Initializing the button
        button = (Button) findViewById(R.id.clickBtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

        // Setting onClick behavior to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, button);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.one:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, "Clicked First Menu Item", Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.two:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, "Clicked Second Menu Item", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, Activity2.class);
                                startActivity(intent1);
                                return true;

                            case R.id.three:
                                Toast.makeText(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, "Logout", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(si.uni_lj.fe.tnuv.projekttunv.Activity3.this, MainActivity.class);
                                startActivity(intent2);
                                return true;
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}