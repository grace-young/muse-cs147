package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewStripActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private ImageButton imgButton;

    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI

    private ArrayList<Strip> stripArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strip);

        setTitle("Create New Strip");

        String arrayAsString = getIntent().getExtras().getString("striparray");
        List<Strip> list = Arrays.asList(new Gson().fromJson(arrayAsString, Strip[].class));
        stripArray = new ArrayList<Strip>(list); //hopefully converts ??????

        Log.d("datas", "Got data back from Gson");
        for(int i = 0; i < stripArray.size(); i++){
            Log.d("datas", stripArray.get(i).toString());
        }
        Log.d("datas", "finished printing");

        imgButton = (ImageButton)findViewById(R.id.image_selected);
    }

    public void finishNewStrip(View view) {
        // Called when "OK" button is tapped
        Log.d("datas", "inside finishNewStrip method");
        EditText editTitle = (EditText) findViewById(R.id.edit_blurb);

        // Need to create a new Strip
        Strip newStrip = new Strip(editTitle.getText().toString(), "owner");
        // add a panel to that Strip
                    // -1 signifies that it is NOT in the res folder.
        Panel newPanel = new Panel("owner", selectedImgUriPath, -1);

        newStrip.addPanel(newPanel);

        // add it to the ArrayList<Strip>
        // and send that back.
        stripArray.add(newStrip);

        // now return back to the main activity
        Intent returnIntent = new Intent();

        //       String arrayAsString = new Gson().toJson(holder.getData());
        String arrayAsString = new Gson().toJson(stripArray);

        returnIntent.putExtra("striparray", arrayAsString);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void cancelNewStrip(View view) {
        // Called when "CANCEL" button is tapped
        Intent returnIntent = new Intent();
        // there is no data to pass back
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void uploadPhoto(View view) {
        // Called when upload button is tapped

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("datas", "Image Path : " + path);
                    Log.d("datas", "URI: " + selectedImageUri);
                    // Set the image in ImageView
                    selectedImgUriPath = selectedImageUri.toString();
                    imgButton.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


}
