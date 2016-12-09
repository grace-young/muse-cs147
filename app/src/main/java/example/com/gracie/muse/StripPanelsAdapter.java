package example.com.gracie.muse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Created by Gracie on 12/8/2016.
 * This is the adapter used by the RecyclerView in StripViewActivity.java to
 * cycle through all the panels in the selected Strip.
 */

public class StripPanelsAdapter extends  RecyclerView.Adapter<StripPanelsAdapter.PanelViewHolder>{
    private ArrayList<Panel> mPanels;
    private Strip currentStrip;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PanelViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView creatorUsername;
        TextView panelBlurb;
        ImageView panelImage;
        public TextView mTextView;
        PanelViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            creatorUsername = (TextView)itemView.findViewById(R.id.creator_username);
            panelBlurb = (TextView)itemView.findViewById(R.id.blurb_text);
            panelImage = (ImageView)itemView.findViewById(R.id.strip_photo);
        }
    }

    public StripPanelsAdapter(Strip myStrip) {
        currentStrip = myStrip;
        mPanels = currentStrip.getPanels();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StripPanelsAdapter.PanelViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.panel_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        StripPanelsAdapter.PanelViewHolder pvh = new StripPanelsAdapter.PanelViewHolder(v);
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StripPanelsAdapter.PanelViewHolder holder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final int i_position = i;

        // Add animation of the image view here
        final Panel panelAtPos = mPanels.get(i);
        if(panelAtPos.getImageID() < 0){
            // FROM URI
            holder.panelImage.setImageURI(Uri.parse(panelAtPos.getImagePath()));
        } else{
            holder.panelImage.setImageResource(panelAtPos.getImageID());
        }

        holder.panelBlurb.setText(panelAtPos.getPanelBlurb());
        holder.creatorUsername.setText(panelAtPos.getCreatorUsername());

        holder.cv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.d("datas", "WOAH THIS WAS CLICKED "+ Integer.toString(i_position));
                // Here it should go to the view panel activity
                Intent intent = new Intent(view.getContext(), PanelViewSlideActivity.class);
                // Send the Strip as a string
                String stripAsString = new Gson().toJson(currentStrip);
                intent.putExtra("stripstring", stripAsString);
                view.getContext().startActivity(intent);
                       /* String arrayAsString = new Gson().toJson(holder.getData());
                intent.putExtra("striparray", arrayAsString);
                startActivityForResult(intent, NEW_STRIP_RESULT);*/
            }

        });



    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPanels.size();
    }
}


