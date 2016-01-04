package app.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import app.Model.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeC1K on 30.12.2015.
 */
public class addDialogueController {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField startDateField;

    @FXML
    private Label hoursLabel;

    @FXML
    private TextField endDateField;
    @FXML
    private TextField intervalField;
    @FXML
    private CheckBox isRepeated;

    public void enableFields(){
        if(isRepeated.isSelected()) {
            endDateField.setDisable(false);
            intervalField.setDisable(false);
            hoursLabel.setVisible(true);
            startDateField.setPromptText("Start Date");
        }
        else{
            startDateField.setPromptText("Time");
            hoursLabel.setVisible(false);
            endDateField.setDisable(true);
            intervalField.setDisable(true);
        }
    }

    public void addButtonHandler() throws IOException,ParseException{
        try {
            if (!isRepeated.isSelected()) {
                Main.arrayTaskList.add(new Task(titleField.getText(), startDateField.getText()));
            } else {
                Main.arrayTaskList.add(new Task(titleField.getText(), startDateField.getText(), endDateField.getText(),
                        (int) TimeUnit.HOURS.toMillis(Integer.valueOf(intervalField.getText()))));
            }
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Check all the fields!");
            alert.setContentText("Fields should not be empty, date format should be according to the tooltip, interval can't be less or equal 0!");
            alert.showAndWait();
        }
    }

    public void cancelButtonHandler(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
}
