package example.com.gracie.muse;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewStripActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_PERMISSION = 101;
    private ImageButton imgButton;

    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI

    private ArrayList<Strip> stripArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strip);

        EditText tx = (EditText) findViewById(R.id.edit_title);
        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Regular.ttf");
        tx.setTypeface(font);



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
        EditText editTitle = (EditText) findViewById(R.id.edit_title);

        // Need to create a new Strip
        Strip newStrip = new Strip(editTitle.getText().toString(), "owner");
        Log.d("datas", "The title set is: " + editTitle.getText().toString());
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
                    String selectedImagePath;

                    Log.i("datas", "Image Path : " + path);
                    Log.d("datas", "URI: " + selectedImageUri);

                    // CONSENT
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSION);
                        //dialog.dismiss();
                        return;
                    }

                    // Set the image in ImageView
                    //selectedImgUriPath = selectedImageUri.toString();
                    selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
                    selectedImgUriPath = selectedImagePath;
                    imgButton.setImageURI(selectedImageUri);
                    Log.i("datas", "img file path "+selectedImagePath);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                // Set the image in ImageView
                //selectedImgUriPath = selectedImageUri.toString();

                Log.i("datas", "permission granted");
            } else {
                // User refused to grant permission.
                Log.d("datas", "no permision");
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
