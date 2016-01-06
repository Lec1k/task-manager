package java.taskmanager.controller;

import java.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeC1K on 30.12.2015.
 */
public class EditDialogueController {
    public Task temp;
    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(EditDialogueController.class);

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextField intervalField;
    @FXML
    private CheckBox isRepeated;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private  Label hoursLabel;


    public void setTitleField(String title) {
        titleField.setText(title);
    }

    public void setStartDateField(String s) {
        startDateField.setText(s);
    }

    public void setEndDateField(String s ) {
        endDateField.setText(s);
    }

    public void setIntervalField(int i) {
        intervalField.setText(Integer.toString(i));
    }

    public void setIsRepeated(boolean b ) {
        isRepeated.setSelected(b);
    }

    public void enableFields(){
        if(isRepeated.isSelected()) {
            endDateField.setVisible(true);
            intervalField.setVisible(true);
            endDateLabel.setVisible(true);
            intervalLabel.setVisible(true);
            hoursLabel.setVisible(true);
            startDateField.setPromptText("Start Date");
        }
        else{
            startDateField.setPromptText("Time");
            endDateLabel.setVisible(false);
            intervalLabel.setVisible(false);
            endDateField.setVisible(false);
            intervalField.setVisible(false);
            hoursLabel.setVisible(false);
        }
        Log.debug("Fields enabled according to the task");
    }

    public void addButtonHandler() throws IOException,ParseException{
        if(!isRepeated.isSelected()) {
            temp.setTime(startDateField.getText());
            temp.setTitle(titleField.getText());
            Log.debug("Non-repeated task is edited");
        }
        else{
            temp.setTime(startDateField.getText());
            temp.setTitle(titleField.getText());
            temp.setNewEndTime(endDateField.getText());
            temp.setIntervalTime((int) TimeUnit.HOURS.toMillis(Integer.valueOf(intervalField.getText())));
            temp.setRepeatable(isRepeated.isSelected());
            Log.debug("Repeated task is edited");

        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        Log.debug("Edit window closed");
    }

    public void cancelButtonHandler(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Log.debug("Edit window closed by cancel button");

    }
    public void deleteButtonHandler(){
        Main.arrayTaskList.remove(temp);
        Log.debug("Task is removed from the list");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Log.debug("Edit window closed");


    }

}
