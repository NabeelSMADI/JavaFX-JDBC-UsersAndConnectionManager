package DB.Users;


import DB.Connection.ConManger;
import DB.Main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {

    private ObservableList<User> data;
    public int cUser = 0;


    public void open() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Users.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException ex) {
            // TODO: handle error
            return;
        }

        Stage UsersStage = new Stage();

        UsersStage.initOwner(Main.pStage);
      //  UsersStage.initModality(Modality.APPLICATION_MODAL);
        UsersStage.setScene(scene);
        UsersStage.initStyle(StageStyle.UTILITY);
        UsersStage.setResizable(false);
        UsersStage.centerOnScreen();
        UsersStage.setTitle("Users");
        UsersStage.setScene(scene);
        UsersStage.show();
    }





    public void buildData(){
        data = FXCollections.observableArrayList();
        data.clear();
        try{
            String SQL = "Select * from users Order By user_nr";
            ResultSet rs = ConManger.connection.createStatement().executeQuery(SQL);
            while(rs.next()){
                User cm = new User();
                cm.userId.set(rs.getInt("user_nr"));
                cm.userName.set(rs.getString("name"));
                cm.userPassword.set(rs.getString("pass"));
                 data.add(cm);
            }
            tableview.setItems(data);
            if(data.size() > 0){
                cUser = data.size()-1;
                showByIndex(cUser);
                addBt.setDisable(true);
            }else{
                newR();
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }




    void showByIndex(int index){
        numText.setText(String.valueOf(index+1));
        nameText.setText(data.get(index).getUserName());
        passText.setText(data.get(index).getUserPassword());
        tableview.getSelectionModel().select(cUser);
        addBt.setDisable(true);
        newBt.setDisable(false);
        editBt.setDisable(false);

    }




    @FXML
    void initialize(){
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'UserMaster.fxml'.";
        user_nr.setCellValueFactory(
                new PropertyValueFactory<User,Integer>("userId"));
        name.setCellValueFactory(
                new PropertyValueFactory<User,String>("userName"));
        pass.setCellValueFactory(
                new PropertyValueFactory<User,String>("userPassword"));

        buildData();
    }


    @FXML
    private TableView<User> tableview;

    @FXML
    private TableColumn<User, Integer> user_nr;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, String> pass;

    @FXML
    void selectRow(MouseEvent event) {
        cUser = tableview.getSelectionModel().getSelectedIndex();
        showByIndex(cUser);
     }

    @FXML
    private Button close;

    @FXML
    private ImageView firstBt;

    @FXML
    private ImageView pBt;

    @FXML
    private ImageView nBt;

    @FXML
    private ImageView lastBt;



    @FXML
    private TextField nameText;

    @FXML
    private PasswordField passText;

    @FXML
    private TextField numText;

    @FXML
    private Button addBt;

    @FXML
    private Button editBt;

    @FXML
    private Button newBt;

    @FXML
    private Button delBt;

    @FXML
    void exit(ActionEvent event) {
        close.getScene().getWindow().hide();
    }

    @FXML
    void add(ActionEvent event) throws SQLException {
        if(!nameText.getText().trim().isEmpty() && !passText.getText().trim().isEmpty()){
            try {
                Statement st = ConManger.connection.createStatement();
                String insertString = "INSERT INTO Users (name, pass) VALUES ('" + nameText.getText().trim() + "',' "+passText.getText().trim()+"')";
                System.out.println(insertString);
                st.execute(insertString);
            }catch(SQLException ex){
                System.out.println(ex);
            }
             buildData();
            cUser = data.size()-1;
            showByIndex(cUser);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        if(!nameText.getText().trim().isEmpty() && !passText.getText().trim().isEmpty()){
            try {
                Statement st = ConManger.connection.createStatement();
                String updateString =  "UPDATE Users  SET name = '" + nameText.getText().trim() + "', pass = '"+passText.getText().trim()+"' WHERE user_nr = "+data.get(cUser).getUserId();
                System.out.println(updateString);
                st.execute(updateString);
            }catch(SQLException ex){
                System.out.println(ex);
            }
            buildData();
            cUser = data.size()-1;
            showByIndex(cUser);
        }
    }

    @FXML
    void del(ActionEvent event) {
        if(numText.getText().trim() != "*" && data.size() > 0){
            try {
                Statement st = ConManger.connection.createStatement();
                String delString = "DELETE FROM Users WHERE user_nr = "+data.get(cUser).getUserId();
                System.out.println(delString);
                st.execute(delString);
            }catch(SQLException ex){
                System.out.println(ex);
            }
            if(data.size() > 0){
                buildData();
                cUser = data.size()-1;
                showByIndex(cUser);
            }else{
                newR();
            }

        }
    }


    @FXML
    void newUser(ActionEvent event) {
      newR();
    }

    void newR (){
        numText.setText("*");
        nameText.setText("");
        passText.setText("");
        addBt.setDisable(false);
        newBt.setDisable(true);
        editBt.setDisable(true);
    }

    @FXML
    void first(MouseEvent event) {
        cUser = 0;
        showByIndex(cUser);
    }

    @FXML
    void last(MouseEvent event) {
        cUser = data.size()-1;
        showByIndex(cUser);
    }



    @FXML
    void next(MouseEvent event) {
        if(cUser != data.size()-1){
            cUser ++;
            showByIndex(cUser);
        }
    }

    @FXML
    void pre(MouseEvent event) {
        if(cUser != 0) {
            cUser--;
            showByIndex(cUser);
        }
    }
}
