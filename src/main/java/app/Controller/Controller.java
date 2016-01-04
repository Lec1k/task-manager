package app.Controller;

import app.View.StartDialogue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.Model.*;

import java.io.IOException;
import java.text.ParseException;

public class Controller {



    @FXML
    private Label todayDate;
    @FXML
    private Button addButton;
    @FXML
    private Button findButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button showButton;
    @FXML
    private TextArea incomingTextArea;
    @FXML
    private TextField inputId;
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField toTextField;
    Tooltip t = new Tooltip("Date format: DD MM YYYY HH:MM");


   private  void setTodayDate(){
       todayDate.setText("Today date is " + Task.format.format(Task.currDate).toString());
   }

    public void dataFormatInfo(){
        Tooltip.install(fromTextField,t);
        Tooltip.install(toTextField,t);
    }

    public void exitButtonHandler() throws IOException{
        TaskIO.writeBinary(Main.arrayTaskList,Main.f);
        System.exit(0);
    }
    public void addButtonHandler() throws IOException{
        StartDialogue.startAddDialog();
    }

    public  boolean isCancelClicked(){
        return true;
    }

    public void findInfo(){
        Tooltip t1 = new Tooltip("Find task by index from 0 to " + Main.arrayTaskList.size());
        Tooltip.install(findButton,t1);
        Tooltip.install(inputId,t1);
    }

    public void findButtonHandler() throws IOException, ParseException,ClassNotFoundException{
        Task temp = Main.arrayTaskList.getTask(Integer.valueOf(inputId.getText()));
        StartDialogue.startEditDialogue(temp);

    }

    public  void fillTextArea() throws IOException, ParseException,ClassNotFoundException {
        setTodayDate();
        try {
            if(fromTextField.getText().length()==0 || toTextField.getText().length()==0){
                incomingTextArea.setText((Main.arrayTaskList).toString());
              }
            else {
                incomingTextArea.setText((Main.arrayTaskList.incoming(fromTextField.getText(),
                        toTextField.getText())).toString());
            }
        }
        catch (ParseException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Check Date format!");
            alert.showAndWait();
        }
         //   incomingTextArea.setText(Main.arrayTaskList.toString());


    }

}
