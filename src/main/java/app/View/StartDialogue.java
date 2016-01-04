package app.View;

import app.Controller.EditDialogueController;
import app.Controller.Main;
import app.Model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeC1K on 04.01.2016.
 */
public class StartDialogue {

    public static void startEditDialogue(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/editDialogue.fxml"));
        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Edit/Delete task");
        addDialogStage.setScene(new Scene((Parent) loader.load()));

        EditDialogueController controller = loader.getController();
        controller.temp = task;
        controller.setTitleField(task.getTitle());
        controller.setStartDateField(Task.format.format((task.getTime())).toString());
        controller.setEndDateField(Task.format.format((task.getEndTime())).toString());
        controller.setIntervalField ((int) TimeUnit.MILLISECONDS.toHours((long)task.getRepeatInterval()));
        controller.setIsRepeated(task.isRepeatable());
        addDialogStage.showAndWait();

    }

    public static boolean startAddDialog () throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/addDialog.fxml"));
        Stage addDialogStage = new Stage();
        addDialogStage.setTitle("Add new task");
        addDialogStage.setScene(new Scene((Parent) loader.load()));

        addDialogStage.showAndWait();



        return true;

    }
}
