package taskmanager.controller;

import taskmanager.model.Task;
import taskmanager.model.TaskIO;
import taskmanager.view.StartDialogue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;

public class Controller {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Controller.class);

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
    Tooltip t = new Tooltip("Date FORMAT: DD MM YYYY HH:MM");




   private  void setTodayDate(){
       todayDate.setText("Today date is " + Task.FORMAT.format(Task.currDate).toString());
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
        LOG.debug("Add task dialogue window called");
        StartDialogue.startAddDialog();
    }

    public  boolean isCancelClicked(){
        return true;
    }

    public void findInfo(){
        Tooltip t1 = new Tooltip("Find task by index from 0 to " + (Main.arrayTaskList.size()-1) + " or by the title");
        Tooltip.install(findButton,t1);
        Tooltip.install(inputId,t1);
    }

    public void findButtonHandler() throws IOException, ParseException,ClassNotFoundException{
        boolean check=false;
        try {
            if (inputId.getText().matches("\\d+\\d?+\\d?")) {
                Task temp = Main.arrayTaskList.getTask(Integer.valueOf(inputId.getText()));

                LOG.debug("Task by id was found");
                StartDialogue.startEditDialogue(temp);
                LOG.debug("Edit dialogue window called");
            } else {
                for (int i = 0; i < Main.arrayTaskList.size(); i++) {
                    if (Main.arrayTaskList.getTask(i).getTitle().equals(inputId.getText())) {
                        LOG.debug("Task by name was found");
                        Task temp = Main.arrayTaskList.getTask(i);
                        check=true;
                        StartDialogue.startEditDialogue(temp);
                        LOG.debug("Edit dialogue window called");

                    }
                }
                if(!check && !(inputId.getText().length()==0)){
                    LOG.warn("Error called if there is no task with such title");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong input");
                    alert.setHeaderText("There is no task with such title");
                    alert.showAndWait();
                }
                else {
                    if(!check) {
                        LOG.warn("Error called if field is empty");
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Empty find field");
                        alert.setHeaderText("Enter title or id of the task");
                        alert.showAndWait();
                    }
                }
            }
        }
        catch (Exception e ){
            LOG.warn("Error called if  input is wrong", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Check find field!");
            alert.setContentText("Task could be only  find by id from 0 to " + (Main.arrayTaskList.size()-1) +
                                 " or by the title of the task");
            alert.showAndWait();

        }

    }

    public  void fillTextArea() throws IOException, ParseException,ClassNotFoundException {
        setTodayDate();
        try {
            if(fromTextField.getText().length()==0 || toTextField.getText().length()==0){
                incomingTextArea.setText((Main.arrayTaskList).toString());
                LOG.debug("Text area filled with tasks");
              }
            else {
                incomingTextArea.setText((Main.arrayTaskList.incoming(fromTextField.getText(),
                        toTextField.getText())).toString());
                LOG.debug("Text area filled with tasks with specific dates chosen by user");
            }
            if (incomingTextArea.getText().length()==0){
                LOG.warn("Warning called if list of tasks is empty");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("List is empty");
                alert.setHeaderText("Add new tasks to be shown");
                alert.setContentText("You should add some tasks to the list, otherwise there is nothing to show");
                alert.showAndWait();

            }
        }
        catch (ParseException e){
            LOG.warn("Error called if date input is wrong",e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Check Date FORMAT!");
            alert.showAndWait();
        }
    }

}
