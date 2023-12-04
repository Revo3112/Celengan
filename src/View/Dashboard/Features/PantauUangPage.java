package View.Dashboard.Features;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class PantauUangPage {
    private Stage stage;

    public PantauUangPage(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Text title = new Text("Pantau Uang");
        title.setFont(Font.font("Verdana", 20)); // Mengatur font dari text
        title.setFill(Color.WHITE);
        title.setTranslateX(-290);
        title.setTranslateY(-167);
        
        Rectangle item1 = createRectangle(200, 200, 10, 10, Color.valueOf("3081D0"));
        Rectangle item2 = createRectangle(200, 200, 10, 10, Color.valueOf("DF826C"));
        Rectangle item3 = createRectangle(200, 200, 10, 10, Color.valueOf("DC8686"));
        HBox hbox = new HBox();
        hbox.getChildren().addAll(item1, item2, item3);

        VBox vbox = new VBox(hbox);

        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: #231F20;");
        background.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);

        StackPane root = new StackPane();
        root.getChildren().addAll(background, vbox, title);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            background.setMaxSize(this.stage.getWidth() - 200, this.stage.getHeight() - 100);
            title.setTranslateX((-this.stage.getWidth() / 2) + 210);
            title.setTranslateY((-this.stage.getHeight() / 2) + 82);
        };
        this.stage.widthProperty().addListener(stageSizeListener);
        this.stage.heightProperty().addListener(stageSizeListener);

        Scene scene = new Scene(root);
        this.stage.setScene(scene);
    }

    private Rectangle createRectangle(double width, double height, double arcWidth, double arcHeight, Color color) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        rect.setFill(color);

        return rect;
    }

}
