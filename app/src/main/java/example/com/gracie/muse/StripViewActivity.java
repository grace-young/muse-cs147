package example.com.gracie.muse;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import example.com.gracie.muse.Strip;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StripViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Strip stripToView; // the strip being displayed
    private StripDataHolder holder;

    private static final int SELECT_PICTURE  = 149;
    private static final int REQUEST_PERMISSION = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip_view);

        Log.d("datas", "IN ON CREATE of StripViewActivity");

        holder = StripDataHolder.getInstance();

        // Get the Strip from the previous activity

        if(getIntent().getExtras() == null){
            Log.d("datas", "null intent thing");
        }


        String stripObjAsString = getIntent().getExtras().getString("stripstring");
        stripToView = new Gson().fromJson(stripObjAsString, Strip.class);
        setTitle(stripToView.getStripTitle());

        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this); //maybe should be "context" ???
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new StripPanelsAdapter(stripToView);
        mRecyclerView.setAdapter(mAdapter);


        // EDIT THE VISIBILITY
        if(!stripToView.isCreatedByNewUser()){
            Log.d("datas", "IN IF stripview");
            // want to disappear the other options
            LinearLayout linlay_finish = (LinearLayout) findViewById(R.id.finish_strip_button);
            linlay_finish.setVisibility(View.GONE);

            LinearLayout linlay_del = (LinearLayout) findViewById(R.id.button_delete_strip);
            linlay_del.setVisibility(View.GONE);

            LinearLayout linlay_invite = (LinearLayout) findViewById(R.id.button_invite_panel);
            linlay_invite.setVisibility(View.GONE);

            LinearLayout linlay_add = (LinearLayout) findViewById(R.id.button_add_panel);
            linlay_add.setVisibility(View.VISIBLE);
        }else{
            Log.d("datas", "IN ELSE");
        }

    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d("datas", "on resume called in StripViewActivity");

        Log.d("datas", "stripToView before: " + stripToView.toString());
        stripToView = holder.getStrip(  stripToView.getOwnerUsername(), stripToView.getStripTitle());
        Log.d("datas", "stripToView after: " + stripToView.toString());


        mAdapter = new StripPanelsAdapter(stripToView);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }


    public void finishStrip(View view){
        Log.d("datas", "FINISH THIS STRIP");
        stripToView.markStripCompleted();
        LinearLayout linlay_finish = (LinearLayout) findViewById(R.id.finish_strip_button);
        linlay_finish.setVisibility(View.INVISIBLE);

        LinearLayout linlay_invite = (LinearLayout) findViewById(R.id.button_invite_panel);
        linlay_invite.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Strip marked as finished; no more panels will be added");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }


    public void deleteStrip(View view){
        int sizeBefore = holder.getData().size();
        holder.deleteStrip(stripToView.getOwnerUsername(), stripToView.getStripTitle());
        if (sizeBefore == holder.getData().size()){
            Log.d("datas", "PROBLEM THIS DID NOT ACTUALLY DELETE");
        }
        Log.d("datas", "DELETE THIS STRIP");
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Your strip has been deleted");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        finish();
    }


    public void toInvite(View view) {
        Intent intent = new Intent(this, InviteToExistingStripActivity.class);

        // Send the Strip as a string
        String stripAsString = new Gson().toJson(stripToView);
        intent.putExtra("stripstring", stripAsString);
        startActivity(intent);
    }

    public void addPanelToStrip(View view){
        // ADD A PANEL TO THE STRIP

        // GET THE PHOTO, SEND TO AddPanelActivity.class
        // start the camera thing
        Intent cam_intent = new Intent();
        cam_intent.setType("image/*");
        cam_intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(cam_intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_PICTURE) {
            if(resultCode == Activity.RESULT_OK) {

                // CAMERA IS BACK

                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    // Get the path from the Uri
                    String selectedImagePath;
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


                    Intent newStripIntent = new Intent(this, AddPanelActivity.class);

                    //puts the string in
                    newStripIntent.putExtra("imgpath", selectedImagePath);

                    // now put the strip in
                    // Send the Strip as a string
                    String stripAsString = new Gson().toJson(stripToView);
                    newStripIntent.putExtra("stripstring", stripAsString);

                    startActivity(newStripIntent);

                } else {
                    // did not select image
                    Log.d("datas", "DID NOT SELECT IMG");
                }
            }else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
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


}
