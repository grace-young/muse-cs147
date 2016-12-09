package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void finishNewStripInvite(View view) {
        // now return back to the main activity
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.find_users);
        String invitee = textView.getText().toString();
        if (invitee.matches("")) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("No users invited");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            return;
        } else if (!USERS.contains(invitee)){
            Log.d("hello", "blarch");
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("User does not exist");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            return;
        }
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Your invitation has been sent!");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }


    private static final ArrayList<String> USERS = new ArrayList<>(Arrays.asList(
            "gracebyung", "shannwooo", "madawg", "janestanford", "lelandstanford"
    ));
}
