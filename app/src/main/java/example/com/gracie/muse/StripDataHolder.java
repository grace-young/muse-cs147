package example.com.gracie.muse;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gracie on 12/1/2016.
 */

public class StripDataHolder {
    private ArrayList<Strip> allStrips = new ArrayList<Strip>();
    private String newUsername;
    private HashMap<String, Integer> usernameToPhotoId = new HashMap<>();

    public ArrayList<Strip> getData(){
        return allStrips;
    }

    public void addUsernamePhotoIDPair(String username, Integer photoId){
        usernameToPhotoId.put(username, photoId);
    }

    public int getProfilePhotoId(String username){
        return Integer.valueOf(usernameToPhotoId.get(username));
    }

    public void setData(ArrayList<Strip> allStripsNew){
        allStrips = allStripsNew;
    }

    /* Used to add a brand new strip to the data */
    public void addNewStrip(Strip newStrip){
        allStrips.add(0, newStrip);
    }

    public void updateNewUsername(String username){ newUsername=username; }

    public String getNewUsername(){return newUsername;}

    public boolean addPanelToStrip(String creatorUsername, String stripTitle, Panel newPanel){
        for (Strip s : allStrips){
            if(s.getOwnerUsername().equals(creatorUsername) &&
                    s.getStripTitle().equals(stripTitle) && !s.isStripCompleted()){
                // This is the same strip! And it is not completed.
                // Might be easier to pass in info for panel?????
                s.addPanel(newPanel);
                return true;
            }
        }
        return false;
    }

    public void resetStripArray(ArrayList<Strip> stripArray){
        allStrips = stripArray;
        Log.d("datas", "reset the stored strip arraylist");
    }

    public Strip getStrip(String creatorUsername, String stripTitle){
        for (Strip s : allStrips){
            if(s.getOwnerUsername().equals(creatorUsername) &&
                    s.getStripTitle().equals(stripTitle)){
                // This is the same strip! And it is not completed.
                // Might be easier to pass in info for panel?????
                Log.d("datas", "inside getStrip & found strip");
                return s;
            }
        }
        return null;
    }

    public ArrayList<Strip> getStripsFromUser(String username){
        ArrayList<Strip> toReturn = new ArrayList<Strip>();
        for (Strip s : allStrips){
            if(s.getOwnerUsername().equals(username)){
                // This is the same strip! And it is not completed.
                // Might be easier to pass in info for panel?????
                Log.d("datas", "found strip for user");
                toReturn.add(s);
            }
        }
        return toReturn;
    }


    public void deleteStrip(String creatorUsername, String stripTitle){
        for(int i = 0; i < allStrips.size(); i++){
            if(allStrips.get(i).getOwnerUsername().equals(creatorUsername) &&
                    allStrips.get(i).getStripTitle().equals(stripTitle)){
                // This is the same strip! And it is not completed.
                // Might be easier to pass in info for panel?????
                allStrips.remove(i);
                return;
            }
        }
    }


    private static final StripDataHolder holder = new StripDataHolder();
    public static StripDataHolder getInstance() {return holder;}

    // For launched activity:
    // ArrayList<Strip> strips = StripDataHolder.getInstance().getData();
}
