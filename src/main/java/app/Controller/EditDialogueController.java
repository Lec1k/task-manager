package app.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.Model.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeC1K on 30.12.2015.
 */
public class EditDialogueController {
    public Task temp;

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
    }

    public void addButtonHandler() throws IOException,ParseException{
        if(!isRepeated.isSelected()) {
            temp.setTime(startDateField.getText());
            temp.setTitle(titleField.getText());
        }
        else{
            temp.setTime(startDateField.getText());
            temp.setTitle(titleField.getText());
            temp.setNewEndTime(endDateField.getText());
            temp.setIntervalTime((int) TimeUnit.HOURS.toMillis(Integer.valueOf(intervalField.getText())));
            temp.setRepeatable(isRepeated.isSelected());

        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    public void cancelButtonHandler(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
    public void deleteButtonHandler(){
        Main.arrayTaskList.remove(temp);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

}
