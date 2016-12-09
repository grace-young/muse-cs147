package example.com.gracie.muse;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;


public class PanelViewSlideActivity extends AppCompatActivity{ //FragmentActivity{
        //implements PanelView.OnFragmentInteractionListener{

    private int NUM_PAGES = 2; // number of panels in strip object

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    private Strip stripToView;
    private ArrayList<Panel> panelsInStrip;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_view_slide);

        // set the number of pages first
        String stripObjAsString = getIntent().getExtras().getString("stripstring");
        stripToView = new Gson().fromJson(stripObjAsString, Strip.class);
        panelsInStrip = stripToView.getPanels();
        NUM_PAGES = stripToView.getPanels().size();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        setTitle(stripToView.getStripTitle());

        Log.d("strips", "in PanelViewSlideActivity");
        Log.d("strips", stripToView.toString());

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Panel panelToDisplay = panelsInStrip.get(position);
            //String creatorUsername, String blurb, int imgID, String imgPath
            return PanelViewFragment.newInstance(panelToDisplay.getCreatorUsername(), panelToDisplay.getPanelBlurb(),
                                        panelToDisplay.getImageID(), panelToDisplay.getImagePath()); // add all the argument things here
            //return new PanelViewFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void onFragmentInteraction(Uri uri){
        Log.d("datas", "HELLO FROM FRAGMENT");
    }
}

