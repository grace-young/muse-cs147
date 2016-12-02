package example.com.gracie.muse;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewStripActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private ImageButton imgButton;

    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strip);

        setTitle("Create New Strip");

        String arrayAsString = getIntent().getExtras().getString("striparray");
        List<Strip> list = Arrays.asList(new Gson().fromJson(arrayAsString, Strip[].class));
        ArrayList<Strip> stripArray = new ArrayList<Strip>(list); //hopefully converts ??????

        Log.d("datas", "Got data back from Gson");
        for(int i = 0; i < stripArray.size(); i++){
            Log.d("datas", stripArray.get(i).toString());
        }
        Log.d("datas", "finished printing");

        imgButton = (ImageButton)findViewById(R.id.image_selected);
    }

    public void finishNewStrip(View view) {
        // Called when "OK" button is tapped
    }

    public void cancelNewStrip(View view) {
        // Called when "CANCEL" button is tapped
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
