package example.com.gracie.muse;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

public class Invite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        AutoCompleteTextView text = (AutoCompleteTextView) findViewById(R.id.find_users);
        Typeface font = Typeface.createFromAsset(getAssets(), "Montserrat-Light.otf");
        text.setTypeface(font);
    }
}
