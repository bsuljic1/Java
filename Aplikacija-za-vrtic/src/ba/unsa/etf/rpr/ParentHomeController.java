package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.util.Optional;

public class ParentHomeController {
    private KindergartenDAO dao = KindergartenDAO.getInstance();
    public ChoiceBox<Child> childChoice = new ChoiceBox<>();
    private int parentId;


    @FXML
    public void initialize() {
       childChoice.setItems(FXCollections.observableList(dao.getChildrenOfParent(parentId)));
    }

    public void actionGetReport(ActionEvent actionEvent){
        ParentReport report = new ParentReport();
        Child selectedChild = (Child) childChoice.getSelectionModel().getSelectedItem();
        try {
            report.showReport(dao.getConn(), selectedChild.getId());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void setParentId(int id){
        this.parentId = id;
    }

    public void actionLogout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) childChoice.getScene().getWindow();
            stage.close();
        }
    }
}
