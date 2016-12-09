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
            //holder.titleImage.setImageResource(stripAtPos.getFirstPanel().getImageID());
            animateIDs(holder.titleImage, panelImageIDs, 0, true);
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


    // This one decodes the image paths as Uri's
    private void animatePaths(final ImageView imageView, final ArrayList<String> imagePaths, final int imageIndex, final boolean forever) {

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        //imageView.setImageResource(images[imageIndex]);
        imageView.setImageURI(Uri.parse(imagePaths.get(imageIndex)));

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (imagePaths.size() - 1 > imageIndex) {
                    animatePaths(imageView, imagePaths, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever){
                        animatePaths(imageView, imagePaths, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void animateIDs(final ImageView imageView, final ArrayList<Integer> images, final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images.get(imageIndex));

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.size() - 1 > imageIndex) {
                    animateIDs(imageView, images, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever){
                        animateIDs(imageView, images, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
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
