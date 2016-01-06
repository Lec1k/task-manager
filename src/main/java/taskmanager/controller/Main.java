package taskmanager.controller;

import taskmanager.model.LinkedTaskList;
import taskmanager.model.Notifier;
import taskmanager.model.Task;
import taskmanager.model.TaskIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Main extends Application {
    public static File f = new File("tasklist.dat");
    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(Main.class);


    public static LinkedTaskList arrayTaskList = new LinkedTaskList();
    @Override
    public void stop(){
        try {
            TaskIO.writeBinary(Main.arrayTaskList, Main.f);
            Log.debug("Saving files and closing the taskmanager.");
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }


        @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);.
        primaryStage.setTitle("Task Manager");
            Log.debug("Main window initialized.");
        primaryStage.setScene(new Scene(root, 649, 332));
        primaryStage.show();
            Notifier notifier = new Notifier();
            notifier.service.start();
            Log.debug("Notification service started");


    }




    public static void main(String[] args)  throws IOException,ParseException,ClassNotFoundException{
        Log.info("Starting task manager application");
        f.createNewFile();

        Main.arrayTaskList = (LinkedTaskList) TaskIO.readBinary(Main.arrayTaskList,Main.f);
        Log.debug("Reading storage file for tasks or creating new if it's first start of the application");
        for(int i=0;i<Main.arrayTaskList.size();i++){
            if(Main.arrayTaskList.getTask(i).getTime().before(Task.currDate)){
                Main.arrayTaskList.getTask(i).setActive(false);
            }
        }
        Log.debug("Checking for inactive tasks");

        launch(args);
    }
}
