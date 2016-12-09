package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

        // EDIT THE VISIBILITY
        Log.d("datas", "STRIP TO VIEW COLMPLETE...." + stripToView.isCreatedByNewUser());
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

        // TESTING SOME HOLDER THINGS
        holder = StripDataHolder.getInstance();
        Strip s = new Strip("titleherehahaha", "fakeowner hahaha", false);
        holder.addNewStrip(s);

        Log.d("holder", "length of holder data: " + holder.getData().size());



        Log.d("datas", "IN ON CREATE of StripViewActivity");
    }


    public void finishStrip(View view){
        Log.d("datas", "FINISH THIS STRIP");
        stripToView.markStripCompleted();
        LinearLayout linlay_finish = (LinearLayout) findViewById(R.id.finish_strip_button);
        linlay_finish.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Your strip is finished: congrats!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        // want to find way to tell main activity that the strip has been updated????
        super.onBackPressed();
    }

    public void deleteStrip(View view){
        int sizeBefore = holder.getData().size();
        holder.deleteStrip(stripToView.getOwnerUsername(), stripToView.getStripTitle());
        if (sizeBefore == holder.getData().size()){
            Log.d("datas", "PROBLEM THIS DID NOT ACTUALLY DELETE");
        }
        Log.d("datas", "DELETE THIS STRIP");
        finish();
    }


    public void toInvite(View view) {
        Intent intent = new Intent(this, InviteActivity.class);
        startActivity(intent);
    }

}
