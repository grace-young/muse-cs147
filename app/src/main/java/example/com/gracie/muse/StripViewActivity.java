package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip_view);

        // Get the Strip from the previous activity
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

        Log.d("datas", "IN ON CREATE of StripViewActivity");
    }

    public void toInvite(View view) {
        Intent intent = new Intent(this, InviteActivity.class);
        startActivity(intent);
    }

}
