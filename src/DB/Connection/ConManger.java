package DB.Connection;



import DB.Main.Main;
import DB.Users.Login.Login;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConManger  {

    public static final String DEFAULT_DRIVER_CLASS = "org.postgresql.Driver";
    public static  String DEFAULT_URL = "jdbc:postgresql://";
    public static  String DEFAULT_Server = "db.f4.htw-berlin.de";
    public static  String DEFAULT_Port = "5432";
    public static  String DEFAULT_DB = "_s0556209__rs_db";
    private static  String DEFAULT_USERNAME = "s0556209";
    private static  String DEFAULT_PASSWORD = "";
    public static Connection connection = null;

    public void open() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConManger.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            // TODO: handle error
            return;
        }

        Stage ConStage = new Stage();
        ConStage.initOwner(Main.pStage);
        ConStage.setScene(scene);
        ConStage.initStyle(StageStyle.UTILITY);
        ConStage.setResizable(false);
        ConStage.centerOnScreen();
        ConStage.centerOnScreen();
        ConStage.initModality(Modality.APPLICATION_MODAL);
        ConStage.setTitle("Connection Manger");
        ConStage.setScene(scene);
        ConStage.show();

        ConStage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                       if(connection == null){
                           Platform.exit();
                        }
                    }
                });
            }
        });
        }





    public void connect() throws Exception {
        try {
            Class.forName(DEFAULT_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
         connection = null;
         try {
            connection = DriverManager.getConnection(
                    DEFAULT_URL, DEFAULT_USERNAME,
                    DEFAULT_PASSWORD);
        }   catch (Exception e){
         }finally {
            if (connection != null) {
                save();
                statusText.setText("Connection successful");
                passText.setStyle("-fx-background-color: green;");
                dBText.getScene().getWindow().hide();
                Login login = new Login();
                login.open();

            } else {
                statusText.setText("Connection Failed!");
            }
        }
    }


    public static boolean connectHide() {
        DEFAULT_URL="jdbc:postgresql://";
        DEFAULT_URL = DEFAULT_URL +DEFAULT_Server + ":" + DEFAULT_Port + "/" + DEFAULT_DB;

        try {
            Class.forName(DEFAULT_DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
           // return;
        }
        connection = null;
        try {
            connection = DriverManager.getConnection(
                    DEFAULT_URL, DEFAULT_USERNAME,
                    DEFAULT_PASSWORD);
        }   catch (Exception e){
        }finally {
            if (connection != null) {
               System.out.println("Connection successful");
                return true;
              } else {
                System.out.println("Connection Failed");
                return false;
            }
        }
    }



    public static boolean getInfo() throws IOException {
          File file = new File("DBInfo");
         if(file != null){
            try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            DEFAULT_Server = reader.readLine();
            DEFAULT_DB = reader.readLine();
            DEFAULT_USERNAME = reader.readLine();
            DEFAULT_PASSWORD = reader.readLine();
            reader.close();
                return  connectHide();
            } catch (IOException ex) {

            }}
        return false;
    };



    public void save(){
        File file = new File("DBInfo");
        if(file != null){
            try {
                FileWriter  fileWriter = new FileWriter(file);
                String text = DEFAULT_Server +"\n" +DEFAULT_DB+"\n"+DEFAULT_USERNAME+"\n"+DEFAULT_PASSWORD;
                 fileWriter.write(text);
                fileWriter.close();
            } catch (IOException ex) {

            }}
    };




    @FXML
    private Button connectBt;

    @FXML
    private Button closeBt;

    @FXML
    private TextField serverText;

    @FXML
    private TextField userText;

    @FXML
    private TextField passText;

    @FXML
    private Label statusText;

    @FXML
    private TextField portText;

    @FXML
    private TextField dBText;

    @FXML
    void connect(ActionEvent event) throws Exception {

            DEFAULT_Server = serverText.getText();
            DEFAULT_Port = portText.getText();
            DEFAULT_DB = dBText.getText();
            DEFAULT_USERNAME = userText.getText();
            DEFAULT_PASSWORD = passText.getText();
            DEFAULT_URL="jdbc:postgresql://";
            DEFAULT_URL = DEFAULT_URL +DEFAULT_Server + ":" + DEFAULT_Port + "/" + DEFAULT_DB;
        if(!passText.getText().trim().isEmpty()){
                connect();
        }else{
            passText.setStyle("-fx-background-color: red;");
        }

    }

    @FXML
    void exit(ActionEvent event) {
        if(connection != null){
            dBText.getScene().getWindow().hide();
        }else{
            Platform.exit();
        }

    }




}
