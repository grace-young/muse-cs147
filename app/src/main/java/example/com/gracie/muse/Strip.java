package example.com.gracie.muse;

/**
 * Created by Gracie on 11/30/2016.
 */

import java.util.ArrayList;

import example.com.gracie.muse.Panel;

public class Strip {
    private String stripTitle;
    private String ownerUsername;
    private ArrayList<Panel> panelsInStrip;

    public Strip(String title, String owner){
        stripTitle = title;
        ownerUsername = owner;
        panelsInStrip = new ArrayList<Panel>();
    }

    public void addPanel(String username, boolean inDrawables, String imgNameOrPath){
        Panel addedPanel = new Panel(username, inDrawables, imgNameOrPath);
        panelsInStrip.add(addedPanel);
    }

    public ArrayList<Panel> getPanels(){
        return panelsInStrip;
    }
}
