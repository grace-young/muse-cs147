package example.com.gracie.muse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

public class InviteToExistingStripActivity extends AppCompatActivity {

    private Strip stripToInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_existing_strip);

        // Get the Strip from the previous activity
        String stripObjAsString = getIntent().getExtras().getString("stripstring");
        stripToInvite = new Gson().fromJson(stripObjAsString, Strip.class);


        ImageView iv = (ImageView) findViewById(R.id.first_panel_img);

        if(stripToInvite.getFirstPanel().getImageID() < 0){
            // Created from Uri
            iv.setImageURI(Uri.parse(stripToInvite.getFirstPanel().getImagePath()));
        }else{
            // In drawable folder
            iv.setImageResource(stripToInvite.getFirstPanel().getImageID());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.find_users);
        textView.setAdapter(adapter);


    }

    public void inviteFromExisting(View view){
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.find_users);
        if(actv.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please invite someone!", Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    public void cancelFromExisiting(View view){
        finish();
    }

    private static final String[] USERS = new String[] {
            "gyoung", "shannwu", "mgwills", "janestanford", "lelandstanford"
    };

}
