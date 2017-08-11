package DB.Users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Smady91 on 20.05.2017.
 */
public class User {



        public SimpleIntegerProperty userId = new SimpleIntegerProperty();
        public SimpleStringProperty userName = new SimpleStringProperty();
        public SimpleStringProperty userPassword = new SimpleStringProperty();

        public Integer getUserId() {
            return userId.get();
        }


        public String getUserName() {
            return userName.get();
        }

        public String getUserPassword() {
            return userPassword.get();
        }





}
