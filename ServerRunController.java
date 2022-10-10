package controller;

import dataType.Person;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import service.WorkerServer;

/**
 * 
 * @author Admin
 */
public class ServerRunController implements Initializable {

    @FXML
    private ListView<Person> listViewConnect;

    private ObservableList<Person> list;
    BlockingQueue<Object> bq;
    @FXML
    private AnchorPane anchorPaneBaseServer;
    @FXML
    private Button buttonRun;

    private WorkerServer workerServer;
    private boolean run = false;
    private ExecutorService executorService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

        listViewConnect.setItems(list);
        buttonRun.setStyle("-fx-background-color: red;");
    }

    /**
     * Метод-слушатель запуска/остановки сервера
     * @param event 
     */
    @FXML
    private void handleButtonRunAction(ActionEvent event) {
        //если сервер остановлен, то
        if (!run) {
            //создание сервера
            workerServer = new WorkerServer(5000, this);
            //запуск сервера
            workerServer.runServer();
            //маркер запуска
            run = true;
            buttonRun.setText("Stop");
            buttonRun.setStyle("-fx-background-color: green;");

        } else {
            try {
                //остановка сервера
                workerServer.stopServer();
                //маркер остановки
                run = false;
                buttonRun.setText("Run");
                buttonRun.setStyle("-fx-background-color: red;");
                
            } catch (IOException ex) {
                Logger.getLogger(ServerRunController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Добавление пользователя в список
     * @param person пользователь
     */
    public void addToListPersonConnection(Person person) {
        list.add(person);
    }

    /**
     * Удаление пользователя из списка
     * @param person пользователь
     */
    public void removeFromListPersonConnection(Person person) {
        list.remove(person);
    }
    
    /**
     * Очистка списка
     */
    public void clearToListPersonConnection() {
        list.clear();
    }

}
