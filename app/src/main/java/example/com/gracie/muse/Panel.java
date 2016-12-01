package example.com.gracie.muse;

/**
 * Created by Gracie on 11/30/2016.
 * Defines the Panel class that defines the image and creator username of a panel.
 */

public class Panel {
    private String creatorUsername;
    private boolean isInDrawables;
    private String imageNameOrPath;

    public Panel(String username, boolean inDrawables, String imgNameOrPath){
        creatorUsername = username;
        isInDrawables = inDrawables;
        imageNameOrPath = imgNameOrPath;
    }

    public String getCreatorUsername(){
        return creatorUsername;
    }

    public boolean isInDrawables(){
        return isInDrawables;
    }

    public String getImageNameOrPath(){
        return imageNameOrPath;
    }
}
