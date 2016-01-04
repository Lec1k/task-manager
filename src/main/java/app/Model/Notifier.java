package app.Model;

import app.Controller.Main;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LeC1K on 02.01.2016.
 */
public class Notifier {
    LinkedTaskList linkedTaskList;
    SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm");
    public Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for(int i=0;i<Integer.MAX_VALUE;i++) {
                        linkedTaskList = (LinkedTaskList) Main.arrayTaskList.incoming(format.format(new Date()).toString(),
                                format.format(new Date().getTime() + 604800000).toString());
                        Platform.runLater(new Runnable() {

                            public void run() {
                                try {
                                    if (linkedTaskList != null) {
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Incoming Tasks");
                                        alert.setHeaderText("Those tasks should be completed soon!");
                                        alert.setContentText(linkedTaskList.toString());
                                        alert.showAndWait();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };
        }
    };

}
