package taskmanager.view;

import org.slf4j.LoggerFactory;
import taskmanager.controller.EditDialogueController;
import taskmanager.controller.Main;
import taskmanager.model.Task;
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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StartDialogue.class);

    public static void startEditDialogue(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/editDialogue.fxml"));
        Stage addDialogStage = new Stage();
        LOG.debug("Stage initialized");
        addDialogStage.setTitle("Edit/Delete task");
        addDialogStage.setScene(new Scene((Parent) loader.load()));

        EditDialogueController controller = loader.getController();
        controller.temp = task;
        controller.setTitleField(task.getTitle());
        controller.setStartDateField(Task.FORMAT.format((task.getTime())).toString());
        controller.setEndDateField(Task.FORMAT.format((task.getEndTime())).toString());
        controller.setIntervalField ((int) TimeUnit.MILLISECONDS.toHours((long)task.getRepeatInterval()));
        controller.setIsRepeated(task.isRepeatable());
        LOG.debug("Fields set to the task values");
        addDialogStage.showAndWait();

    }

    public static boolean startAddDialog () throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/fxml/addDialog.fxml"));
        Stage addDialogStage = new Stage();
        LOG.debug("Stage initialized");
        addDialogStage.setTitle("Add new task");
        addDialogStage.setScene(new Scene((Parent) loader.load()));

        addDialogStage.showAndWait();



        return true;

    }
}
