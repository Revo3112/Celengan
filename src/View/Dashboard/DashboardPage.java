package View.Dashboard;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardPage {

    private Stage stage;

    public DashboardPage(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Text title = new Text("Welcome to Dasboard page.");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);

        StackPane root = new StackPane(title);
        Scene scene = new Scene(root, 600, 600);

        this.stage.setTitle("Register");
        this.stage.setScene(scene);
        this.stage.show();
    }
}
