package taskmanager.model;

import org.slf4j.LoggerFactory;
import taskmanager.controller.Main;
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

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Notifier.class);
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
                                    if (linkedTaskList.size()>0) {
                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                        alert.setTitle("Incoming Tasks");
                                        alert.setHeaderText("Those tasks should be completed soon!");
                                        alert.setContentText(linkedTaskList.toString());
                                        alert.showAndWait();

                                    }
                                } catch (Exception e) {
                                    LOG.error("Some unpredicted exception occurred ",e);
                                }
                            }
                        });
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            LOG.error("Stream is interrupted", e);
                        }
                    }
                    return null;
                }
            };
        }
    };

}
