package example.com.gracie.muse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProfileGracie extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    StripDataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_gracie);

        String username = getIntent().getExtras().getString("usernamestr");

        holder = StripDataHolder.getInstance();
        ArrayList<Strip> profileStrips = holder.getStripsFromUser(username);
        Log.d("prof", "showing profile for user: " + username);

        CustomTextView cvt = (CustomTextView) findViewById(R.id.prof_username);
        cvt.setText(username);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this); //maybe should be "context" ???
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AllStripAdapter(profileStrips);
        mRecyclerView.setAdapter(mAdapter);

        ImageView iv = (ImageView) findViewById(R.id.prof_photo);
        ArrayList<Integer> image_ids = holder.getProfilePhotoIds();
        int img_id;
        if(username.matches("gracebyung")){
            Log.d("prof", "found username matches: " + "gracebyung");
            img_id = Integer.valueOf(image_ids.get(0));
        }else if(username.matches("shannwooo")){
            Log.d("prof", "found username matches: " + "shannwooo");
            img_id = Integer.valueOf(image_ids.get(1));
        }else if(username.matches("madawg")){
            Log.d("prof", "found username matches: " + "madawg");
            img_id = Integer.valueOf(image_ids.get(2));
        }else {
            Log.d("prof", "found username matches: " + "OTHER");
            img_id = Integer.valueOf(image_ids.get(3));
        }

        iv.setImageResource(img_id);


    }





}
