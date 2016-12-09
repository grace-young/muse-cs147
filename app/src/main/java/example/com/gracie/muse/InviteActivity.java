package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {

    private ArrayList<Strip> stripArray;
    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI
    private String stringAsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("Invite");

        Log.d("hello", "poop");

        stringAsArray = getIntent().getExtras().getString("striparray");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.find_users);
        textView.setAdapter(adapter);

    }

    public void finishNewStrip(View view) {
        // now return back to the main activity
        Intent returnIntent = new Intent();

        returnIntent.putExtra("striparray", stringAsArray);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private static final String[] USERS = new String[] {
        "gyoung", "shannwu", "mgwills", "janestanford", "lelandstanford"
    };
}
