package register;

import javafx.application.Application;
import java.sql.*;

import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;
// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
//import com.mysql.cj.xdevapi.Statement;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

import java.awt.*;

/**
 * import javafx.scene.Group;
 * import javafx.scene.Node;
 * register
 */
class Register {
    private String user;
    private String password;

    // Getter
    public String get_user() {
        return user;
    }

    public String get_password() {
        return password;
    }

    // Setter
    public void set_user(String user) {
        this.user = user;
    }

    public void set_password(String password) {
        this.password = password;
    }
}

public class App extends Application {
    public void sql_register() {
        // Create a connection to the database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/celengan", "root", "");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    // String sql = "INSERT INTO users (username, password, last_edited,
                    // remember_me) VALUES ('Revo', '2002071047', CUREENT_TIMESTAMP, 'True')";
                    // statement.executeQuery(sql);

                    String sql = "Select * from users";
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(sql);
                    System.out.println(result);
                    while (result.next()) {
                        System.out.println(result.getString("id"));
                        System.out.println(result.getString("username"));
                        System.out.println(result.getString("password"));
                        System.out.println(result.getString("last_edited"));
                        System.out.println(result.getString("remember_me"));
                    }

                    // System.out.println("Data inserted.");
                    // statement.close();
                    System.out.println("Connection closed.");
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Buat scene dengan ukuran fleksibel
        Scene scene = new Scene(new VBox(), 400, 400);

        // Tambahkan button ke dalam scene
        Button button1 = new Button("Register");
        VBox vBox = new VBox();

        vBox.getChildren().addAll(button1);
        vBox.setMargin(button1, new Insets(20, 0, 0, scene.getWidth() / 2));
        scene.setRoot(vBox);

        // Tampilkan scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        sql_register();
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {
        launch(args);

        /*
         * Tree di java terbagi menjadi beberapa
         * 1. Stage -> Window
         * 2. Scene -> Content
         * 3. Layout -> Layout
         */
    }

}
