package example.com.gracie.muse;

/**
 * Created by Gracie on 11/30/2016.
 */

import android.util.Log;

import java.util.ArrayList;

import example.com.gracie.muse.Panel;

public class Strip {
    private String stripTitle;
    private String ownerUsername;
    private ArrayList<Panel> panelsInStrip;
    private boolean stripCompleted;

    public Strip(String title, String owner){
        stripTitle = title;
        ownerUsername = owner;
        panelsInStrip = new ArrayList<Panel>();
        stripCompleted = false;
    }

    public void addPanel(String username, String imgPath){
        Panel addedPanel = new Panel(username, imgPath, -1);
        panelsInStrip.add(addedPanel);
        Log.d("datas", "added panel [img path] to " + stripTitle + " with username " + username);
    }

    public void addPanel(String username, int imgID){
        Panel addedPanel = new Panel(username, "in_res", imgID);
        panelsInStrip.add(addedPanel);
        Log.d("datas", "added panel to " + stripTitle + " with username " + username);
    }

    public void addPanel(Panel p){
        panelsInStrip.add(p);
    }

    public void markStripCompleted(){
        stripCompleted = true;
    }

    public boolean isStripCompleted(){
        return stripCompleted;
    }

    public ArrayList<Panel> getPanels(){
        return panelsInStrip;
    }

    public ArrayList<Integer> getPanelIds(){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for(int i = 0; i < panelsInStrip.size(); i++){
            Panel panel = panelsInStrip.get(i);
            toReturn.add(panel.getImageID());
        }
        return toReturn;
    }

    public ArrayList<String> getPanelPaths(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < panelsInStrip.size(); i++){
            Panel panel = panelsInStrip.get(i);
            toReturn.add(panel.getImagePath());
        }
        return toReturn;
    }

    public String getOwnerUsername(){
        return ownerUsername;
    }

    public String getStripTitle(){
        return stripTitle;
    }

    public Panel getFirstPanel(){
        // only called if fake data
        if (panelsInStrip.size() == 0){
            Log.d("datas", "THERE ARE NO PANELS WHAT");
            Log.d("datas", "Strip title is: " + stripTitle);
            return null;
        }
        return panelsInStrip.get(0);
    }


    public String toString(){
        /*String stripTitle;
    private String ownerUsername;
    private ArrayList<Panel> panelsInStrip;
    private boolean stripCompleted;*/
        String stripBegin = "Strip title: " + stripTitle + " created by: " + ownerUsername + ". It has " + panelsInStrip.size() + " panels";
        String panelsPrinted = "";
        for(int i = 0; i < panelsInStrip.size(); i++){
            panelsPrinted += "USERNAME: ";
            panelsPrinted += panelsInStrip.get(i).getCreatorUsername();
            panelsPrinted += "\n";
        }
        return stripBegin + "\n" + panelsPrinted;
    }
}
