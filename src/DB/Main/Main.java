package DB.Main;

import DB.Connection.ConManger;
import DB.Users.Login.Login;
import DB.Users.Users;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application{
    public static Stage pStage ;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.initStyle(StageStyle.DECORATED);
      //  primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.centerOnScreen();
         primaryStage.setTitle("Retail Software");
        primaryStage.setScene(scene);
        primaryStage.show();
        if(!ConManger.getInfo()){
            ConManger conManger = new ConManger();
            conManger.open();
        }else{
            Login login = new Login();
            login.open();
        }
    }




    @FXML
    private Menu file;

    @FXML
    private MenuItem ConnectBt;


    @FXML
    private MenuItem UsersBt;

    @FXML
    private MenuItem logOutBt;

    @FXML
    private MenuItem closeBt;






    @FXML
    void Connect(ActionEvent event) throws Exception {
        ConManger conManger = new ConManger();
        conManger.open();

    }





    @FXML
    void openUsers(ActionEvent event) throws Exception {
        Users users = new Users();
        users.open();
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
        Login.passed = false;
        Login login = new Login();
        login.open();
    }

    @FXML
    void Exit(ActionEvent event) {
        Platform.exit();
    }


}
