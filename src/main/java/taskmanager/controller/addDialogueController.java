package taskmanager.controller;

import taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeC1K on 30.12.2015.
 */
public class addDialogueController {

    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(addDialogueController.class);

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
        Log.debug("Fields are enabled according to task");
    }

    public void addButtonHandler() throws IOException,ParseException{
        try {
            if (!isRepeated.isSelected()) {
                Main.arrayTaskList.add(new Task(titleField.getText(), startDateField.getText()));
                Log.debug("Non-repeated task added");
            } else {
                Main.arrayTaskList.add(new Task(titleField.getText(), startDateField.getText(), endDateField.getText(),
                        (int) TimeUnit.HOURS.toMillis(Integer.valueOf(intervalField.getText()))));
                Log.debug("Repeated task added");
            }
            Stage stage = (Stage) addButton.getScene().getWindow();
            Log.debug("Add dialogue window closed");
            stage.close();
        }
        catch (Exception e){
            Log.debug("Error called if input is wrong");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Check all the fields!");
            alert.setContentText("Fields should not be empty, date format should be according to the tooltip, interval can't be less or equal 0!");
            alert.showAndWait();
        }
    }

    public void cancelButtonHandler(){
        Log.debug("Add window closed by cancel button ");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
}
