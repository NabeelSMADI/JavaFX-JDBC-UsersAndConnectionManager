package DB.Users.Login;

import DB.Connection.ConManger;
import DB.Main.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.ResultSet;

public class Login  {
    public static  boolean passed = false;
    public void open() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            // TODO: handle error
            return;
        }

        Stage loginStage = new Stage();

        loginStage.initOwner(Main.pStage);
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setScene(scene);
        loginStage.initStyle(StageStyle.UTILITY);
        loginStage.setResizable(false);
        loginStage.centerOnScreen();
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.show();

        loginStage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                       if(!passed){
                           System.out.println("Application Closed by click to Close Button(X)");
                           Platform.exit();
                       }
                    }
                });
            }
        });
    }



    @FXML
    private TextField nameText;

    @FXML
    private PasswordField passText;

    @FXML
    private Button loginBt;

    @FXML
    private Button closeBt;

    @FXML
    void Exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void login(ActionEvent event) {
        try{
            String SQL = "Select pass from users where name='"+ nameText.getText() + "'";
            ResultSet rs = ConManger.connection.createStatement().executeQuery(SQL);
          if(rs.next()){
              String pass = rs.getString("pass").trim();
              String pass2 = passText.getText().trim();
               if(pass.compareTo(pass2) == 0){
                   passed = true;
                  nameText.getScene().getWindow().hide();
              };
          }



        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
