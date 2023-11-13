package View.Splash_Screen.Element;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dialog {
    ImageView chat1 = new ImageView();
    ImageView chat2 = new ImageView();
    ImageView chat3 = new ImageView();
    ImageView chat4 = new ImageView();

    public Dialog(Node chat1, Node chat2, Node chat3, Node chat4) {
        this.chat1 = (ImageView) chat1;
        this.chat2 = (ImageView) chat2;
        this.chat3 = (ImageView) chat3;
        this.chat4 = (ImageView) chat4;
    }

    public void setChatImage(String pathImage1, String pathImage2, String pathImage3, String pathImage4) {

    }
}
