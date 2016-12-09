package example.com.gracie.muse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        iv.setImageResource(holder.getProfilePhotoId(username));

    }





}
