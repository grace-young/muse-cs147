package example.com.gracie.muse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StripDataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Log.d("datas", "IN ON CREATE");
    }

    public void createNewStrip(View view) {
        Intent intent = new Intent(this, NewStripActivity.class);
        // holder.getData() returns an arraylist of all the strip objects
        String arrayAsString = new Gson().toJson(holder.getData());
        intent.putExtra("striparray", arrayAsString);
        startActivity(intent);
    }

    private StripDataHolder initializeData(){
        holder = StripDataHolder.getInstance();
//        ArrayList<Strip> strips = StripDataHolder.getInstance().getData();
        Strip newStrip = new Strip("first gracie title", "gracie");
        newStrip.addPanel("shannon", R.drawable.cat_0);
        holder.addNewStrip(newStrip);
        Log.d("datas", "added pannel 1 with author shannon to strip first gracie title");

        Strip newStrip2 = new Strip("SECOND2 title", "madison");
        newStrip2.addPanel("madison", R.drawable.cat_1);
        holder.addNewStrip(newStrip2);
        Log.d("datas", "added panel 2 to strip SECOND2 title with author madison");

        Log.d("datas", holder.getData().toString());

        // Now to create the panels and strip for the cats

        return holder;
    }

}
