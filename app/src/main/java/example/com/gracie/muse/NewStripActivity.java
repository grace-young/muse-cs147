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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewStripActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_PERMISSION = 101;
    private ImageView imgButton;

    private String selectedImgUriPath = null; // use Uri.parse() to get back to URI

//    private ArrayList<Strip> stripArray;
    private static final int INVITE_CODE = 102;

    private StripDataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_strip);

        EditText tx = (EditText) findViewById(R.id.edit_title);
        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Regular.ttf");
        tx.setTypeface(font);

        setTitle("Create New Strip");

        // unclear??
        holder = StripDataHolder.getInstance();

        selectedImgUriPath = getIntent().getExtras().getString("imgpath");

        imgButton = (ImageView) findViewById(R.id.image_selected);

        imgButton.setImageURI(Uri.parse(selectedImgUriPath));
    }

    // returns true if it added it successfully
    // returns false if it had an error
    public boolean finishNewStrip(View view) {
        // Called when "OK" button is tapped
        Log.d("datas", "inside finishNewStrip method");
        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        EditText editBlurb = (EditText) findViewById(R.id.edit_blurb);

        String title = editTitle.getText().toString();
        Log.d("hello", title);
        if (title.matches("")) {
            Log.d("hello", "what is going on");
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Your strip needs a title!");
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            return false;
        }
        // Need to create a new Strip
        Strip newStrip = new Strip(editTitle.getText().toString(), holder.getNewUsername(), true); // marking true that created by new user
        Log.d("datas", "The title set is: " + editTitle.getText().toString());
        // add a panel to that Strip
        // -1 signifies that it is NOT in the res folder.
        Panel newPanel = new Panel("owner", editBlurb.getText().toString(), selectedImgUriPath, -1);

        newStrip.addPanel(newPanel);

        holder.addNewStrip(newStrip);

        // add it to the ArrayList<Strip>
        // and send that back.
//        stripArray.add(0, newStrip);
//        return stripArray;
        return true;
    }

    public void cancelNewStrip(View view) {
        // Called when "CANCEL" button is tapped
        Intent returnIntent = new Intent();
        // there is no data to pass back
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void toInvite(View view) {
        if(finishNewStrip(view)) {
           Intent intent = new Intent(this, InviteActivity.class);
            String stripAsString = new Gson().toJson(holder.getData().get(0)); // ????
            intent.putExtra("stripstring", stripAsString);
            startActivity(intent);

        }
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions, @NonNull final int[] grantResults) {
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
