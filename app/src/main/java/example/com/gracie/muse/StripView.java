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

public class StripView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StripDataHolder holder;
    private Strip strip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip_view);

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

        Log.d("datas", "IN ON CREATE");
    }

    private StripDataHolder initializeData(){
        // strip = Strip.getInstance();
        if (holder.getData().size() > 0){
            // This has already been initalized
            return holder;
        }
        Strip newStrip = new Strip("Dunces & Dragons", "gyoung");
        newStrip.addPanel("shannwu", R.drawable.demo_panel);
        newStrip.addPanel("gyoung", R.drawable.cat_3);
        holder.addNewStrip(newStrip);
        Log.d("datas", "added pannel 1 with author shannon to strip first gracie title");

        Strip newStrip2 = new Strip("Muse-ing", "mgwills");
        newStrip2.addPanel("mgwills", R.drawable.test_panel);
        holder.addNewStrip(newStrip2);
        Log.d("datas", "added panel 2 to strip SECOND2 title with author madison");

        Log.d("datas", holder.getData().toString());

        // Now to create the panels and strip for the cats

        return holder;
    }

    public void toInvite(View view) {
        Intent intent = new Intent(this, InviteActivity.class);
        startActivity(intent);
    }

}
