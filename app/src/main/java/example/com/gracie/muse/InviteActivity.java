package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {

    private ArrayList<Strip> stripArray;
    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI
    private Strip currentStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("Invite");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.find_users);
        textView.setAdapter(adapter);


        String stripObjAsString = getIntent().getExtras().getString("stripstring");
        currentStrip = new Gson().fromJson(stripObjAsString, Strip.class);


        ImageView iv = (ImageView) findViewById(R.id.first_panel_img_invite);

        if(currentStrip.getFirstPanel().getImageID() < 0){
            // Created from Uri
            iv.setImageURI(Uri.parse(currentStrip.getFirstPanel().getImagePath()));
        }else{
            // In drawable folder
            iv.setImageResource(currentStrip.getFirstPanel().getImageID());
        }
    }

    public void finishNewStrip(View view) {
        // now return back to the main activity
        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }

    private static final String[] USERS = new String[] {
        "gyoung", "shannwu", "mgwills", "janestanford", "lelandstanford"
    };
}
