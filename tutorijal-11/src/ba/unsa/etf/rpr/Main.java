package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource(
                "/fxml/glavna.fxml"), bundle);
        GlavnaController ctrl = new GlavnaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Gradovi svijeta");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.toFront();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static String ispisiGradove() {
        String ret = "";
        ArrayList<Grad> gradovi = GeografijaDAO.gradovi();
        for (Grad grad : gradovi)
            ret += grad.getNaziv() + " (" + grad.getDrzava().getNaziv() + ") - " + grad.getBrojStanovnika() + "\n";
        return ret;
    }

    public static void glavniGrad() {
        String ispisi = "";
        System.out.println("Unesite naziv drzave:\n ");
        Scanner unesi = new Scanner("");
        String naziv = unesi.nextLine();
        Drzava drzava = GeografijaDAO.nadjiDrzavu(naziv);
        if (drzava != null) ispisi += "Glavni grad države " + naziv + " je " + drzava.getGlavniGrad();
        else ispisi += "Nepostojeća država";
        System.out.println(ispisi);
    }


}
