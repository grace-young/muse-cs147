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
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StripDataHolder holder;


    private static final int SELECT_PICTURE  = 149;
    private static final int REQUEST_PERMISSION = 105;
    private static final int NEW_STRIP_RESULT = 147;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Explore");

        // first put data in
        holder = initializeData();

        mRecyclerView = (RecyclerView)findViewById(R.id.rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this); //maybe should be "context" ???
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mAdapter = new AllStripAdapter(holder.getData());
        mRecyclerView.setAdapter(mAdapter);

        Log.d("datas", "IN ON CREATE of MainActivity");


    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("holder", "on resume called in main activity");
        mAdapter = new AllStripAdapter(holder.getData());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void createNewStrip(View view) {
        //Intent intent = new Intent(this, NewStripActivity.class);

        // start the camera thing
        Intent cam_intent = new Intent();
        cam_intent.setType("image/*");
        cam_intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(cam_intent, "Select Picture"), SELECT_PICTURE);

/*
        String arrayAsString = new Gson().toJson(holder.getData());
        Log.d("hello", arrayAsString);
        intent.putExtra("striparray", arrayAsString);
        startActivityForResult(intent, NEW_STRIP_RESULT);
*/
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


                    Intent newStrip = new Intent(this, NewStripActivity.class);

                    //puts the string in
                    newStrip.putExtra("imgpath", selectedImagePath);
                    startActivity(newStrip);

                } else {
                    // did not select image
                    Log.d("datas", "DID NOT SELECT IMG");
                }
            }else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }




    private void printData(){
        ArrayList<Strip> stripArray = holder.getData();
        for(int i = 0; i < stripArray.size(); i++){
            Log.d("datas", "STRIP: " + stripArray.get(i).toString());
            ArrayList<Panel> panels = stripArray.get(i).getPanels();
            for(int p=0; p < panels.size(); p++){
                Log.d("datas", "  PANELS: created by - " + panels.get(p).getCreatorUsername() + " \n path - "
                        + panels.get(p).getImagePath());
            }
        }
        Log.d("datas", "finished printing");

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


    private StripDataHolder initializeData(){
        holder = StripDataHolder.getInstance();
        if (holder.getData().size() > 0){
            // This has already been initalized
            return holder;
        }
        Strip balloonStrip = new Strip ("Lost Balloon", "madawg", false);
        balloonStrip.addPanel("madawg", "Before Dumbo grew his ears, all he had was a little balloon and big dreams.", R.drawable.b_elephant);
        balloonStrip.addPanel("gracebyung", "Then one day he lost the balloon! It floated away into the sky.", R.drawable.b_balloon);
        balloonStrip.addPanel("shannwooo", "It was meant for a different adventure.", R.drawable.b_up);
        holder.addNewStrip(balloonStrip);
        Log.d("datas", "balloonstrip iscompleted: " + balloonStrip.isCreatedByNewUser());

        Strip peopleStrip = new Strip ("Stick Figures", "gracebyung", false);
        peopleStrip.addPanel("gracebyung", "I can only draw stick figures :(", R.drawable.p_stick_figure);
        peopleStrip.addPanel("shannowooo", "Keep at it, that's where we all start!", R.drawable.p_man_drawing);
        holder.addNewStrip(peopleStrip);
        Log.d("datas", "peoplestrip iscompleted: " + peopleStrip.isCreatedByNewUser());

        Strip leavesStrip = new Strip ("Leaves", "shannwooo", false);
        leavesStrip.addPanel("shannwooo", "My backyard is COVERED in leaves!", R.drawable.l_leaves);
        leavesStrip.addPanel("gracebyung", "Did someone say leaves?", R.drawable.l_tea_leaves);
        leavesStrip.addPanel("madawg", "Watch out, Professor Trelawney might read your tea leaf fortune.", R.drawable.l_prof_trelawney);
        leavesStrip.addPanel("shannwooo", "Professor Trelawney or Tori Kelley? Curly hair, don't care :P", R.drawable.l_tori_kelley);
        leavesStrip.addPanel("madawg", "Tori Kelley's guitar skills tho", R.drawable.l_guitar);
        leavesStrip.addPanel("gracebyung", "Pretty sun design on the guitar... getting some sun at the beach right now", R.drawable.l_sun_beach);
        leavesStrip.addPanel("shannwooo", "Beach life forever! Here's my favorite type of leaves, just to bring it full circle hehe", R.drawable.l_palm_tree);
        leavesStrip.addPanel("madawg", "Palms? :D", R.drawable.l_palm);
        holder.addNewStrip(leavesStrip);
        Log.d("datas", "leavestrip iscompleted: " + leavesStrip.isCreatedByNewUser());

        Strip differentStrip = new Strip ("Do you", "shannwoo", false);
        differentStrip.addPanel("shannwoo", "Gotta love him. Always doin his own thing", R.drawable.d_stitch);
        differentStrip.addPanel("gracebyung", "Ugly ducklings unite", R.drawable.d_ugly_duckling);
        differentStrip.addPanel("madawg", "Lyric from \"Get it Together\", one of my fav songs by India Arie", R.drawable.d_lyric);
        holder.addNewStrip(differentStrip);
        Log.d("datas", "differentstrip iscompleted: " + differentStrip.isCreatedByNewUser());
        Log.d("datas", holder.getData().toString());



        // Now to create the panels and strip for the cats

        return holder;
    }

}
