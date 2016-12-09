package example.com.gracie.muse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Gracie on 12/1/2016.
 */
public class AllStripAdapter extends RecyclerView.Adapter<AllStripAdapter.StripViewHolder> {
// some kind of private var here ??????
    ArrayList<Strip> mDatasetStrip;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class StripViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView creatorUsername;
        TextView stripTitle;
        ImageView titleImage;
        public TextView mTextView;
        StripViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            creatorUsername = (TextView)itemView.findViewById(R.id.creator_username);
            stripTitle = (TextView)itemView.findViewById(R.id.strip_title);
            titleImage = (ImageView)itemView.findViewById(R.id.strip_photo);
        }
    }


    public AllStripAdapter(ArrayList<Strip> myDataset) {
        mDatasetStrip = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StripViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        StripViewHolder svh = new StripViewHolder(v);
        return svh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StripViewHolder holder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final int i_position = i;

        // Add animation of the image view here
        final Strip stripAtPos = mDatasetStrip.get(i);
        if(stripAtPos.getFirstPanel().getImageID() < 0){
            // FROM URI
            ArrayList<String> panelImagePaths =  stripAtPos.getPanelPaths();
            holder.titleImage.setImageURI(Uri.parse(stripAtPos.getFirstPanel().getImagePath()));
        } else{
            ArrayList<Integer> panelImageIDs = stripAtPos.getPanelIds();
            holder.titleImage.setImageResource(stripAtPos.getFirstPanel().getImageID());
        }

        // ^ later on we'll have to check if it is actually on the SD card
        holder.stripTitle.setText(mDatasetStrip.get(i).getStripTitle());  // + " | "
        holder.creatorUsername.setText(mDatasetStrip.get(i).getOwnerUsername());

        holder.cv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.d("datas", "WOAH THIS WAS CLICKED "+ Integer.toString(i_position));
                // Here it should go to the view panel activity
                //Intent intent = new Intent(view.getContext(), PanelViewSlideActivity.class);
                Intent intent = new Intent(view.getContext(), StripViewActivity.class);
                // Send the Strip as a string
                String stripAsString = new Gson().toJson(stripAtPos);
                intent.putExtra("stripstring", stripAsString);
                view.getContext().startActivity(intent);
            }

        });



    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetStrip.size();
    }

    /* ????? don't know if need this
    * @Override
      public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    * */

}
