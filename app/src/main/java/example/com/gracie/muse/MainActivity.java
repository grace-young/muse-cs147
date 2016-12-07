package example.com.gracie.muse;

import android.app.Activity;
import android.content.Intent;
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

        Log.d("datas", "IN ON CREATE");
    }

    public void createNewStrip(View view) {
        Intent intent = new Intent(this, PanelViewSlideActivity.class);
        startActivity(intent);
        // holder.getData() returns an arraylist of all the strip objects
       /* String arrayAsString = new Gson().toJson(holder.getData());
        intent.putExtra("striparray", arrayAsString);
        startActivityForResult(intent, NEW_STRIP_RESULT);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_STRIP_RESULT) {
            if(resultCode == Activity.RESULT_OK){
                String arrayAsString =data.getStringExtra("striparray");

                List<Strip> list = Arrays.asList(new Gson().fromJson(arrayAsString, Strip[].class));
                ArrayList<Strip> arrStrip = new ArrayList<Strip>(list); //hopefully converts ??????

                holder.resetStripArray(arrStrip);
                Log.d("datas", "RESET THE STRIP ARR");
                mAdapter = new AllStripAdapter(holder.getData());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
                printData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
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

    private StripDataHolder initializeData(){
        holder = StripDataHolder.getInstance();
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

}
