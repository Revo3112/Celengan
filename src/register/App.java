package register;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 * register
 */
class Register {
    public String nama;
    public String nim;
}

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        Register register = new Register();
        register.nama = "Revo";
        register.nim = "2002071047";

        // VBox vbox = new VBox();

        Scene scene = new Scene(null, 500, 500);
        System.out.println(scene.getWidth());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
