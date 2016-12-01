package example.com.gracie.muse;

/**
 * Created by Gracie on 11/30/2016.
 * Defines the Panel class that defines the image and creator username of a panel.
 */

public class Panel {
    private String creatorUsername;
    private String imageNameOrPath;
    private int imageID; // The ID for the image, -1 if not in res

    public Panel(String username, String imgPath, int imgID){
        creatorUsername = username;
        imageNameOrPath = imgPath;
        imageID = imgID;
    }

    public String getCreatorUsername(){
        return creatorUsername;
    }

    public boolean isInDrawables(){
        return imageID > 0;
    }

    public String getImagePath(){
        return imageNameOrPath;
    }

    public int getImageID(){
        return imageID;
    }
}
