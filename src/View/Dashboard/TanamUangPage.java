package View.Dashboard;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TanamUangPage {
    private Stage stage;

    public TanamUangPage(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Text title = new Text("This is Tanam Uang Page");
        title.setFont(Font.font("Verdana", 20));
        title.setFill(Color.BLACK);

        String pil[] = {
            "pertama",
            "kedua",
            "ketiga",
            "keempat"
        };
        
        ComboBox combobox = new ComboBox(FXCollections.observableArrayList(pil));

        StackPane root = new StackPane(title, combobox);
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
