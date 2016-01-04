package app.Controller;

import app.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static File f = new File("src/main/resources/data/tasklist.dat");


    public static LinkedTaskList arrayTaskList = new LinkedTaskList();
    @Override
    public void stop(){
        try {
            TaskIO.writeBinary(Main.arrayTaskList, Main.f);
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
        primaryStage.setScene(new Scene(root, 649, 332));
        primaryStage.show();
            Notifier notifier = new Notifier();
            notifier.service.start();


    }




    public static void main(String[] args)  throws IOException,ParseException,ClassNotFoundException{
        f.createNewFile();
        Main.arrayTaskList = (LinkedTaskList) TaskIO.readBinary(Main.arrayTaskList,Main.f);
        for(int i=0;i<Main.arrayTaskList.size();i++){
            if(Main.arrayTaskList.getTask(i).getTime().before(Task.currDate)){
                Main.arrayTaskList.getTask(i).setActive(false);
            }
        }
        launch(args);
    }
}
