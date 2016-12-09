package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddPanelActivity extends AppCompatActivity {

    private String selectedImgUriPath;
    private Strip stripAddingTo;
    private StripDataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_panel);

        // Get the img added
        selectedImgUriPath = getIntent().getExtras().getString("imgpath");

        // Get the Strip from the previous activity
        String stripObjAsString = getIntent().getExtras().getString("stripstring");
        stripAddingTo = new Gson().fromJson(stripObjAsString, Strip.class);

        ImageView iv_uploaded = (ImageView) findViewById(R.id.image_selected);
        iv_uploaded.setImageURI(Uri.parse(selectedImgUriPath));

        // Title thing
        CustomTextView cv = (CustomTextView) findViewById(R.id.adding_to);
        String toSetTextTo = "Adding to " + stripAddingTo.getStripTitle();
        cv.setText(toSetTextTo);

        // Holder data
        holder = StripDataHolder.getInstance();
    }

    public void finishAddingPanel(View view){
        // Pull Blurb
        EditText blurbEdit = (EditText) findViewById(R.id.edit_blurb);


            // Actually add the panel
            // String username, String blurb, String imgPath, int imgID
            Panel p = new Panel(holder.getNewUsername(), blurbEdit.getText().toString(), selectedImgUriPath, -1);
            holder.addPanelToStrip(stripAddingTo.getOwnerUsername(), stripAddingTo.getStripTitle(), p);

            Strip newStripMade = holder.getStrip(stripAddingTo.getOwnerUsername(), stripAddingTo.getStripTitle());

            //Intent returnIntent = new Intent(this, StripViewActivity.class);
            // Send the Strip as a string
            //String stripAsString = new Gson().toJson(newStripMade);
            //returnIntent.putExtra("stripstring", stripAsString);
            //startActivity(returnIntent);
            finish();
        }

    public void cancelNewStrip(View view) {
        // Called when "CANCEL" button is tapped
        Intent returnIntent = new Intent();
        // there is no data to pass back
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

        // Pull tags ??????
    }

