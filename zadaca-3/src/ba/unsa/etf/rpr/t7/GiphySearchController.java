package ba.unsa.etf.rpr.t7;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;


public class GiphySearchController {
    public TilePane tilePane;
    public TextField fieldGiphy;
    private String selectedURL = "";

    public void searchAction(ActionEvent actionEvent) throws URISyntaxException {
        tilePane.getChildren().clear();
        Image loading = new Image(getClass().getClassLoader().getResource("slike/loading.gif").toString(), 128, 128, false, false);
        Button buttonLoading = new Button("");
        buttonLoading.setGraphic(new ImageView(loading));
        buttonLoading.setMaxSize(135, 135);
        buttonLoading.setMinSize(135, 135);
        tilePane.getChildren().add(buttonLoading);

        new Thread(() -> {

            URLConnection connection = null;
            try {
                connection = new URL("http://api.giphy.com/v1/gifs/search?api_key=UNRwMmEKNs7YmboP4wo6KXBBySCal7EX&q=" + fieldGiphy.getText()).openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                bufferedReader.close();
                JSONObject json = new JSONObject(content.toString());
                JSONArray gifovi = json.getJSONArray("data");
                for (int i = 0; i < gifovi.length(); i++) {
                    JSONObject gif = gifovi.getJSONObject(i);
                    JSONObject slika = gif.getJSONObject("images").getJSONObject("original_still");
                    String url = slika.getString("url");
                    int indeks = url.indexOf("/");
                    int indeks2 = url.indexOf(".");
                    url = url.replace(url.substring(indeks + 2, indeks2), "i");
                    Image image = new Image(url, 128, 128, false, false);
                    Button button1 = new Button("");
                    button1.setGraphic(new ImageView(image));
                    String finalUrl = url;
                    button1.setOnAction(e -> selectedURL = finalUrl);
                    button1.setMaxSize(135, 135);
                    button1.setMinSize(135, 135);

                    Platform.runLater(() -> tilePane.getChildren().add(tilePane.getChildren().size() - 1, button1));

                }
                Platform.runLater(() -> tilePane.getChildren().remove(tilePane.getChildren().size() - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void okAction(ActionEvent actionEvent) {
        if (selectedURL.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ResourceBundle bundle = ResourceBundle.getBundle("Language");
            alert.setTitle(bundle.getString("dialogTitle"));
            alert.setHeaderText(bundle.getString("dialogHeaderText"));
            alert.setContentText(bundle.getString("dialogContent"));
            alert.showAndWait();
        } else {
            Stage stage = (Stage) tilePane.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        selectedURL = "";
        Stage stage = (Stage) tilePane.getScene().getWindow();
        stage.close();
    }

    public String getSelectedURL() {
        return selectedURL;
    }
}
