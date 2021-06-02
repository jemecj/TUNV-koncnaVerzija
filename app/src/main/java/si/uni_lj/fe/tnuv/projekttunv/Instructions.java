package si.uni_lj.fe.tnuv.projekttunv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        TextView mTextView = (TextView) findViewById(R.id.text_view);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}