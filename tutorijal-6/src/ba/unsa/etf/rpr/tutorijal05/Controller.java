package ba.unsa.etf.rpr.tutorijal05;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    enum Operation {
        NONE,
        ADD,
        SUBTRACT
    }

    public Label display;

    private SimpleStringProperty firstNumber = new SimpleStringProperty("0");
    private SimpleStringProperty secondNumber = new SimpleStringProperty("0");
    private Operation operation = Operation.NONE;

    @FXML
    public void initialize() {
        display.textProperty().bind(firstNumber);
    }

    public void actionEquals(ActionEvent actionEvent) {
        switch (operation) {
            case ADD:
                firstNumber.set(String.valueOf(Double.parseDouble(firstNumber.get()) + Double.parseDouble(secondNumber.get())));
                break;
            case SUBTRACT:
                firstNumber.set(String.valueOf(Double.parseDouble(firstNumber.get()) - Double.parseDouble(secondNumber.get())));
                break;
            default:
                return;
        }
        display.textProperty().bind(firstNumber);
        operation = Operation.NONE;
    }

    public void actionPlus(ActionEvent actionEvent) {
        display.textProperty().bind(secondNumber);
        operation = Operation.ADD;
    }

    public void actionDot(ActionEvent actionEvent) {
        if (operation == Operation.NONE && !firstNumber.get().contains("."))
            firstNumber.set(firstNumber.get() + ".");
        else if (operation != Operation.NONE && !secondNumber.get().contains("."))
            secondNumber.set(secondNumber.get() + ".");
    }

    public void action0(ActionEvent actionEvent) {
        actionNumber(0);
    }

    public void action1(ActionEvent actionEvent) {
        actionNumber(1);
    }

    public void action2(ActionEvent actionEvent) {
        actionNumber(2);
    }

    public void action3(ActionEvent actionEvent) {
        actionNumber(3);
    }

    public void action4(ActionEvent actionEvent) {
        actionNumber(4);
    }

    public void action5(ActionEvent actionEvent) {
        actionNumber(5);
    }

    public void action6(ActionEvent actionEvent) {
        actionNumber(6);
    }

    public void action7(ActionEvent actionEvent) {
        actionNumber(7);
    }

    public void action8(ActionEvent actionEvent) {
        actionNumber(8);
    }

    public void action9(ActionEvent actionEvent) {
        actionNumber(9);
    }

    private void actionNumber(int number) {
        if (operation == Operation.NONE && firstNumber.get().equals("0") ||
                operation != Operation.NONE && secondNumber.get().equals("0"))
            if (number == 0)
                return;
            else if (operation == Operation.NONE)
                firstNumber.set("");
            else
                secondNumber.set("");

        if (operation == Operation.NONE)
            firstNumber.set(firstNumber.get() + number);
        else
            secondNumber.set(secondNumber.get() + number);
    }
}









