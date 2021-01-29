package ba.unsa.etf.rpr.t7;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AboutController {
    public ImageView imageView;

    public AboutController(){
        initialize();
    }
    @FXML
    public void initialize(){
        Image image = new Image("https://cdn2.f-cdn.com/contestentries/1458359/7352885/5c1146a1c851b_thumb900.jpg");
        imageView = new ImageView();
        imageView.setImage(image);
    }
}
